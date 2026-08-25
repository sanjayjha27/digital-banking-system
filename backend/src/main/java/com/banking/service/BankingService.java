package com.banking.service;

import com.banking.dto.*;
import com.banking.entity.*;
import com.banking.exception.ApiException;
import com.banking.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankingService {
    private final UserRepository users;
    private final AccountRepository accounts;
    private final TransactionRepository tx;

    public BankingService (UserRepository u, AccountRepository a, TransactionRepository t) {
        users = u;
        accounts = a;
        tx = t;
    }

    private Account own (String email) {
        User u = users.findByEmail (email).orElseThrow (() -> new ApiException ("User not found", HttpStatus.NOT_FOUND));
        return accounts.findByUserId (u.getId ()).orElseThrow ();
    }

    private void check (Account a) {
        if (!a.isActive ()) throw new ApiException ("Account is inactive", HttpStatus.FORBIDDEN);
    }

    @Transactional
    public Account deposit (String email, AmountRequest r) {
        Account a = accounts.findByAccountNumberForUpdate (own (email).getAccountNumber ()).orElseThrow ();
        check (a);
        a.setBalance (a.getBalance ().add (r.amount ()));
        tx.save (new BankTransaction (a, TransactionType.DEPOSIT, r.amount (), a.getBalance (), null, r.description ()));
        return a;
    }

    @Transactional
    public Account withdraw (String email, AmountRequest r) {
        Account a = accounts.findByAccountNumberForUpdate (own (email).getAccountNumber ()).orElseThrow ();
        check (a);
        if (a.getBalance ().compareTo (r.amount ()) < 0)
            throw new ApiException ("Insufficient balance", HttpStatus.BAD_REQUEST);
        a.setBalance (a.getBalance ().subtract (r.amount ()));
        tx.save (new BankTransaction (a, TransactionType.WITHDRAWAL, r.amount (), a.getBalance (), null, r.description ()));
        return a;
    }

    @Transactional
    public void transfer (String email, TransferRequest r) {
        Account from = accounts.findByAccountNumberForUpdate (own (email).getAccountNumber ()).orElseThrow ();
        Account to = accounts.findByAccountNumberForUpdate (r.recipientAccount ()).orElseThrow (() -> new ApiException ("Recipient account not found", HttpStatus.NOT_FOUND));
        check (from);
        check (to);
        if (from.getAccountNumber ().equals (to.getAccountNumber ()))
            throw new ApiException ("Cannot transfer to the same account", HttpStatus.BAD_REQUEST);
        if (from.getBalance ().compareTo (r.amount ()) < 0)
            throw new ApiException ("Insufficient balance", HttpStatus.BAD_REQUEST);
        from.setBalance (from.getBalance ().subtract (r.amount ()));
        to.setBalance (to.getBalance ().add (r.amount ()));
        tx.save (new BankTransaction (from, TransactionType.TRANSFER_SENT, r.amount (), from.getBalance (), to.getAccountNumber (), r.description ()));
        tx.save (new BankTransaction (to, TransactionType.TRANSFER_RECEIVED, r.amount (), to.getBalance (), from.getAccountNumber (), r.description ()));
    }

    public Account getAccount (String email) {
        return own (email);
    }

    public List<TransactionResponse> history (String email) {
        return tx.findTop50ByAccountIdOrderByCreatedAtDesc (own (email).getId ()).stream ().map (t -> new TransactionResponse (t.getId (), t.getType (), t.getAmount (), t.getBalanceAfter (), t.getRelatedAccount (), t.getDescription (), t.getCreatedAt ())).collect (Collectors.toList ());
    }
}

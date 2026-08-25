package com.banking.controller;

import com.banking.dto.*;
import com.banking.entity.Account;
import com.banking.service.BankingService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/banking")
public class BankingController {
    private final BankingService service;

    public BankingController (BankingService s) {
        service = s;
    }

    private String email (Authentication a) {
        return a.getName ();
    }

    @GetMapping ("/account")
    public AccountResponse account (Authentication a) {
        Account x = service.getAccount (email (a));
        return new AccountResponse (x.getAccountNumber (), x.getUser ().getFullName (), x.getUser ().getEmail (), x.getBalance (), x.isActive (), x.getCreatedAt ());
    }

    @PostMapping ("/deposit")
    public AccountResponse deposit (Authentication a, @Valid @RequestBody AmountRequest r) {
        Account x = service.deposit (email (a), r);
        return new AccountResponse (x.getAccountNumber (), x.getUser ().getFullName (), x.getUser ().getEmail (), x.getBalance (), x.isActive (), x.getCreatedAt ());
    }

    @PostMapping ("/withdraw")
    public AccountResponse withdraw (Authentication a, @Valid @RequestBody AmountRequest r) {
        Account x = service.withdraw (email (a), r);
        return new AccountResponse (x.getAccountNumber (), x.getUser ().getFullName (), x.getUser ().getEmail (), x.getBalance (), x.isActive (), x.getCreatedAt ());
    }

    @PostMapping ("/transfer")
    public java.util.Map<String, String> transfer (Authentication a, @Valid @RequestBody TransferRequest r) {
        service.transfer (email (a), r);
        return java.util.Map.of ("message", "Transfer successful");
    }

    @GetMapping ("/transactions")
    public List<TransactionResponse> transactions (Authentication a) {
        return service.history (email (a));
    }
}

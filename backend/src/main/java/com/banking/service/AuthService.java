package com.banking.service;

import com.banking.dto.*;
import com.banking.entity.*;
import com.banking.exception.ApiException;
import com.banking.repository.*;
import com.banking.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthService {
    private final UserRepository users;
    private final AccountRepository accounts;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService (UserRepository u, AccountRepository a, PasswordEncoder e, JwtService j) {
        users = u;
        accounts = a;
        encoder = e;
        jwt = j;
    }

    @Transactional
    public AuthResponse register (RegisterRequest r) {
        if ( users.existsByEmail (r.email ()) )
            throw new ApiException ("Email already registered", HttpStatus.CONFLICT);
        User u = new User (r.email ().toLowerCase (), encoder.encode (r.password ()), r.fullName ());
        users.save (u);
        String number;
        do {
            number = String.valueOf (ThreadLocalRandom.current ().nextLong (1000000000L, 9999999999L));
        } while ( accounts.findByAccountNumberForUpdate (number).isPresent () );
        Account a = new Account (number, u);
        accounts.save (a);
        return new AuthResponse (jwt.generate (u.getEmail ()), u.getEmail (), u.getFullName (), a.getAccountNumber ());
    }

    public AuthResponse login (LoginRequest r) {
        User u = users.findByEmail (r.email ().toLowerCase ()).orElseThrow (() -> new ApiException ("Invalid credentials", HttpStatus.UNAUTHORIZED));
        if ( ! encoder.matches (r.password (), u.getPassword ()) )
            throw new ApiException ("Invalid credentials", HttpStatus.UNAUTHORIZED);
        Account a = accounts.findByUserId (u.getId ()).orElseThrow ();
        return new AuthResponse (jwt.generate (u.getEmail ()), u.getEmail (), u.getFullName (), a.getAccountNumber ());
    }
}

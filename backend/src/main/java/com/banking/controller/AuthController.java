package com.banking.controller;

import com.banking.dto.*;
import com.banking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController (AuthService s) {
        service = s;
    }

    @PostMapping ("/register")
    public AuthResponse register (@Valid @RequestBody RegisterRequest r) {
        return service.register (r);
    }

    @PostMapping ("/login")
    public AuthResponse login (@Valid @RequestBody LoginRequest r) {
        return service.login (r);
    }
}

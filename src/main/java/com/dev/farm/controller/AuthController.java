package com.dev.farm.controller;

import com.dev.farm.dto.AuthRequest;
import com.dev.farm.dto.AuthResponse;
import com.dev.farm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRequest> createUser(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}




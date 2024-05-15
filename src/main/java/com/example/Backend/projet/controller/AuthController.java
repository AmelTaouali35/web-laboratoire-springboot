package com.example.Backend.projet.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.projet.dto.AuthenticationRequest;
import com.example.Backend.projet.dto.AuthenticationResponse;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.service.AuthService;
import com.example.Backend.projet.service.EmailService;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }
}

package com.example.Backend.projet.service;

import org.springframework.http.ResponseEntity;

import com.example.Backend.projet.dto.AuthenticationRequest;
import com.example.Backend.projet.dto.AuthenticationResponse;

public interface AuthService {
ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) ;
}

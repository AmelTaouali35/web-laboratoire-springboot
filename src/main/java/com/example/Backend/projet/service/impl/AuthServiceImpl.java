package com.example.Backend.projet.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Backend.projet.dto.AuthenticationRequest;
import com.example.Backend.projet.dto.AuthenticationResponse;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.AuthService;
import com.example.Backend.projet.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest) {
        try {
            // Authentification
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Vérification de l'utilisateur
            User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElse(null);
            if (user == null) {
                throw new BadCredentialsException("User not found with email: " + authenticationRequest.getEmail());
            }

            // Génération du token
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(new AuthenticationResponse(token, user.getRole().name() , user.getId()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, null , null));
        }
    }
}

package com.example.Backend.projet.controller;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    
    @PostMapping("/api/logout")
    public void logout() {
        // Perform logout action
        SecurityContextHolder.clearContext();
    }
}

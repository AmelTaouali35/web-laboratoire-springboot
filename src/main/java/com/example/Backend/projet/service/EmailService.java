package com.example.Backend.projet.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendAccountCreationEmail(String email, String password);
    void sendEmail(String to, String subject, String text);
    void sendLabCreationEmail(String email, String password);
}

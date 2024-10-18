package com.example.Backend.projet.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.Backend.projet.service.EmailService;
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendAccountCreationEmail(String email, String password) {
        String subject = "Compte créé avec succès";
        String text = "Votre compte a été créé avec succès sur la plateforme Medicis. Vos données de connexion sont :\nEmail: " + email + "\nMot de passe: " + password;
        sendEmail(email, subject, text);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}

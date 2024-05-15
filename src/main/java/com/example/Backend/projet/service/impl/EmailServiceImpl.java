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
    public void sendLabCreationEmail(String email, String password) {
        String subject = "Compte creér avec succées";
        String text = "Votre Compte est crééer avec succées dans le palteform Medicis, votre Données de connexion est :\n Email: " + email + "\n Password: " + password;
        sendEmail(email, subject, text);
    }
    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Override
    public void sendAccountCreationEmail(String email, String password) {
        String subject = "Compte creér avec succées";
        String text = "Votre Compte est crééer avec succées dans le palteform Medicis, votre Données de connexion est :\n Email: " + email + "\n Password: " + password;
        sendEmail(email, subject, text);
    }
}

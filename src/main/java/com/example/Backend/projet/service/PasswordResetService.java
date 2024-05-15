package com.example.Backend.projet.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    /*@Autowired
    private JavaMailSender javaMailSender;*/

    /*@Value("${app.reset-password.url}")
    private String resetPasswordUrl;*/

    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user == null) {
            throw new IllegalArgumentException("User with given email does not exist.");
        }

        String token = UUID.randomUUID().toString();
        //user.setResetToken(token);
        userRepository.save(user);

        sendResetEmail(user.getEmail(), token);
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        //message.setText("To reset your password, click on the following link: " + resetPasswordUrl + token);

        //javaMailSender.send(message);
    }

    public boolean verifyToken(String email, String token) {
        User user = userRepository.findByEmail(email).orElseThrow();
        //return user != null && token.equals(user.getResetToken());
        return true ;
    }

    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user == null) {
            throw new IllegalArgumentException("User with given email does not exist.");
        }

        user.setPassword(newPassword);
        //user.setResetToken(null);
        userRepository.save(user);
    }
}

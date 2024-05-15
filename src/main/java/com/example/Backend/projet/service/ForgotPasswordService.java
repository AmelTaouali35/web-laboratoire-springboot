package com.example.Backend.projet.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.ForgotPasswordRepo;

@Service
public class ForgotPasswordService {
    private static final long EXPIRE_TOKEN=30;

    @Autowired
    private ForgotPasswordRepo repo;

    public String forgotPass(String email){
        Optional<User> userOptional = Optional.ofNullable(repo.findByEmail(email));

        if(!userOptional.isPresent()){
            return "Invalid email id.";
        }

        User user=userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user=repo.save(user);
        return user.getToken();
    }

    public String resetPass(String token, String password){
        Optional<User> userOptional= Optional.ofNullable(repo.findByToken(token));

        if(!userOptional.isPresent()){
            return "Invalid token";
        }
        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }

        User user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        repo.save(user);

        return "Your password successfully updated.";
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        long diffInMinutes = java.time.Duration.between(tokenCreationDate, now).toMinutes();
        return diffInMinutes >= EXPIRE_TOKEN;
    }
}

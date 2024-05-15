package com.example.Backend.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.projet.model.User;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByToken(String token);
}

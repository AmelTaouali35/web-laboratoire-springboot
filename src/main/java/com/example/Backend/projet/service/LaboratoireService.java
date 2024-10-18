package com.example.Backend.projet.service;

import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.model.User;

import org.springframework.http.ResponseEntity;

public interface LaboratoireService {
    ResponseEntity<User> getLaboratoireProfile(Long id);
}

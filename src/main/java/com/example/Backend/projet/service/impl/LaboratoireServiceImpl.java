package com.example.Backend.projet.service.impl;

import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.LaboratoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LaboratoireServiceImpl implements LaboratoireService {

    private final UserRepository userRepository;

    @Autowired
    public LaboratoireServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<User> getLaboratoireProfile(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

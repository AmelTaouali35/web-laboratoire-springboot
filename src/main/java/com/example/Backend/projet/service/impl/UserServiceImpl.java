package com.example.Backend.projet.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Backend.projet.dto.UserInfoDto;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<UserInfoDto>> getAll() {
        return ResponseEntity.ok(
            userRepository.findAll()
                .stream()
                .map(UserInfoDto::fromEntity)
                .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<User> getUserProfile(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}

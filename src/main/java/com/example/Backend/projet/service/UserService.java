package com.example.Backend.projet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.Backend.projet.dto.UserInfoDto;
import com.example.Backend.projet.model.User;

public interface UserService {
ResponseEntity<List<UserInfoDto>> getAll();
ResponseEntity<User> getUserProfile(Long id);
Optional<User> getUserById(Long id);

}

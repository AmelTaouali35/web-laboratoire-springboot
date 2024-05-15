package com.example.Backend.projet.controller;

import com.example.Backend.projet.model.User;
import com.example.Backend.projet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }
}

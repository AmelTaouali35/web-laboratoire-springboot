package com.example.Backend.projet.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.Backend.projet.model.ERole;
import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.EmailService;

import lombok.RequiredArgsConstructor;

import com.example.Backend.projet.dto.LabRequest;
import com.example.Backend.projet.dto.LabResponse;
import com.example.Backend.projet.dto.SignupRequest;
import com.example.Backend.projet.dto.SignupResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; 
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        try {
            // Check if the user already exists
            User existingUser = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
            if (existingUser != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("User with this email already exists."));
            }

            // Create a new user
            User user = new User();
            user.setEmail(signupRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encode password
            user.setRole(ERole.ROLE_RESPONSABLE);

            userRepository.save(user);

            // Send email to the user
            emailService.sendAccountCreationEmail(signupRequest.getEmail(), signupRequest.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(new SignupResponse("User registered successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignupResponse("Internal server error: " + e.getMessage()));
        }
    }
    @GetMapping("/getResponsibleUsers")
    public ResponseEntity<List<User>> getResponsibleUsers() {
        List<User> responsibleUsers = userRepository.findUsersByRole(ERole.ROLE_RESPONSABLE);
        return ResponseEntity.ok(responsibleUsers);
    }


    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<SignupResponse> updateUser(@PathVariable Long id, @Valid @RequestBody SignupRequest signupRequest) {
        try {
            // Vérifiez si le laboratoire existe avec l'ID donné
            User existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            // Mettre à jour les détails du laboratoire existant
            existingUser.setEmail(signupRequest.getEmail());
            existingUser.setPassword(signupRequest.getPassword());
            //existingLab.setEmail(labRequest.getEmail());
           // existingLab.setPassword(passwordEncoder.encode(labRequest.getPassword()));

            userRepository.save(existingUser);

            return ResponseEntity.ok(new SignupResponse("Responsable updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignupResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return ResponseEntity.ok(user);
    }
}
package com.example.Backend.projet.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.Backend.projet.dto.EmailRequest;
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
    public ResponseEntity<SignupResponse> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        try {
           
            System.out.println("Received signup request: " + signupRequest);

            // Check if the user already exists
            User existingResponsabe = userRepository.findByEmailAndRole(signupRequest.getEmail(), ERole.ROLE_RESPONSABLE).orElse(null);
            if (existingResponsabe != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Un responsable avec cet email existe déjà."));
            }

            // Create a new responsable
            User responsable = new User();
            responsable.setEmail(signupRequest.getEmail());
            responsable.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encode password
            responsable.setRole(ERole.ROLE_RESPONSABLE);

            userRepository.save(responsable);

            System.out.println("Responsable registered successfully: " + responsable);

            return ResponseEntity.status(HttpStatus.CREATED).body(new SignupResponse("Responsable enregistré avec succès."));
        } catch (Exception e) {
            e.printStackTrace(); 
            System.err.println("Error during user registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SignupResponse("Erreur interne du serveur: " + e.getMessage()));
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<Map<String, String>> sendEmail(@RequestBody SignupRequest signupRequest) {
        try {
            emailService.sendAccountCreationEmail(signupRequest.getEmail(), signupRequest.getPassword());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Email sent successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Failed to send email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
package com.example.Backend.projet.controller;

import com.example.Backend.projet.dto.LabRequest;
import com.example.Backend.projet.dto.LabResponse;
import com.example.Backend.projet.dto.LaboratoireProduitDto;
import com.example.Backend.projet.dto.UserInfoDto;
import com.example.Backend.projet.model.Customer;
import com.example.Backend.projet.model.ERole;
import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.CustomerRepository;
import com.example.Backend.projet.repository.LaboratoireRepository;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.CustomerService;
import com.example.Backend.projet.service.EmailService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/labs")
@RequiredArgsConstructor
public class LabController {

  
    private final  LaboratoireRepository laboratoireRepository;
    
    private final PasswordEncoder passwordEncoder ;
    @Autowired
    private EmailService emailService;
    private final UserRepository userRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    
    private CustomerRepository customerRepository;
    

    @GetMapping("/getAllProducts/{id}")
    public ResponseEntity<List<LaboratoireProduitDto>> getProductsByLaboratoryId(@PathVariable Long id) {
        return customerService.getLaboratoireProduits(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadExcelFile(@RequestParam("file") MultipartFile file ,@RequestParam("code") String code ) {
        customerService.processExcelFile(file , code);
        return ResponseEntity.ok(true);
    }
    @PostMapping("/add")
    public ResponseEntity<LabResponse> addLab(@Valid @RequestBody LabRequest labRequest) {
        try {
            // Check if the lab with the provided code already exists
            Laboratoire existingLab = laboratoireRepository.findByCode(labRequest.getCode()).orElse(null);
            if (existingLab != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabResponse("Laboratory with this code already exists."));
            }

            // Create a new lab
            Laboratoire newLab = new Laboratoire();
            newLab.setCode(labRequest.getCode());
            newLab.setDesignation(labRequest.getDesignation());
            newLab.setEmail(labRequest.getEmail());
            newLab.setPassword(passwordEncoder.encode(labRequest.getPassword()));
            newLab.setRole(ERole.ROLE_LABORATOIRE);
            laboratoireRepository.save(newLab);

            // Send email to the lab
            emailService.sendLabCreationEmail(newLab.getEmail(), labRequest.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(new LabResponse("Laboratory added successfully."));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabResponse("Duplicate entry or data integrity violation: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LabResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Laboratoire>> getAllLaboratories() {
        List<Laboratoire> laboratories = laboratoireRepository.findAll();
        return ResponseEntity.ok(laboratories);
    }
   
    @GetMapping("/getLab/{id}")
    public ResponseEntity<Laboratoire> getLaboratoire(@PathVariable Long id) {
        Laboratoire laboratoire = laboratoireRepository.findById(id).get();
        return ResponseEntity.ok(laboratoire);
    }
    
    @PutMapping("/updateLab/{id}")
    public ResponseEntity<LabResponse> updateLab(@PathVariable Long id, @Valid @RequestBody LabRequest labRequest) {
        try {
            // Vérifiez si le laboratoire existe avec l'ID donné
            Laboratoire existingLab = laboratoireRepository.findById(id).orElse(null);
            if (existingLab == null) {
                return ResponseEntity.notFound().build();
            }

            // Mettre à jour les détails du laboratoire existant
            existingLab.setCode(labRequest.getCode());
            existingLab.setDesignation(labRequest.getDesignation());
            //existingLab.setEmail(labRequest.getEmail());
           // existingLab.setPassword(passwordEncoder.encode(labRequest.getPassword()));

            laboratoireRepository.save(existingLab);

            return ResponseEntity.ok(new LabResponse("Laboratory updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LabResponse("Internal server error: " + e.getMessage()));
        }
    }
    @DeleteMapping("/deleteLab/{id}")
    public ResponseEntity<Void> deleteLab(@PathVariable Long id) {
        try {
            // Check if the lab with the provided ID exists
            if (!laboratoireRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            // Delete the lab
            laboratoireRepository.deleteById(id);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{laboratoireId}/donnees")
    public ResponseEntity<List<Customer>> getDonneesByLaboratoireId(@PathVariable Long laboratoireId) {
        List<Customer> donnees = customerRepository.findAllByLaboratoireId(laboratoireId);
        return ResponseEntity.ok(donnees);
    }
    
  
    
}
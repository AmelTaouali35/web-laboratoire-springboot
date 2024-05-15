package com.example.Backend.projet.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.Backend.projet.dto.LaboratoireProduitDto;
import com.example.Backend.projet.model.Customer;
import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.CustomerRepository;
import com.example.Backend.projet.repository.LaboratoireRepository;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.util.ExcelParser;

@Service
public class CustomerService {

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LaboratoireRepository laboratoireRepository;
    
    @Autowired
    private UserRepository userRepository ;

    @Transactional
    public void setLaboratoireForCustomers(String laboratoireCode) {
        Laboratoire laboratoire = laboratoireRepository.findByCode(laboratoireCode)
                .orElseThrow(() -> new IllegalArgumentException("Laboratoire with code " + laboratoireCode + " not found"));
        List<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            customer.setLaboratoire(laboratoire);
            customerRepository.save(customer);
        }
    }

    public void processExcelFile(MultipartFile file , String code) {
        try {
            List<Customer> customers = excelParser.parseExcel(file.getInputStream() , code);
            customerRepository.saveAll(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ResponseEntity<List<LaboratoireProduitDto>> getLaboratoireProduits(Long labId) {
        List<LaboratoireProduitDto> produits = customerRepository.findAllByLaboratoireId(labId)
                .stream()
                .map(LaboratoireProduitDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produits);
    }
    
 

    
}
package com.example.Backend.projet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Backend.projet.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findAllBylaboratoireId(Long idLaboratoire);
    List<Customer> findAllByLaboratoireId(Long laboratoireId);
    
}

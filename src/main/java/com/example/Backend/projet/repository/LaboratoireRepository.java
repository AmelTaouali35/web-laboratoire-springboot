package com.example.Backend.projet.repository;

import com.example.Backend.projet.model.Laboratoire;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long> {
	 List<Laboratoire> findAll(); 
	    Optional<Laboratoire> findByCode(String code);
	    Optional<Laboratoire> findById(Long id);
}

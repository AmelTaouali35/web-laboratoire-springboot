package com.example.Backend.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Backend.projet.model.produits;

@Repository
public interface ProduitsRepository extends JpaRepository<produits, Long> {
	
}

package com.example.Backend.projet.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Backend.projet.model.ERole;
import com.example.Backend.projet.model.Laboratoire;
import com.example.Backend.projet.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { 
    Optional<User> findByEmail(String email);
    
    boolean existsByRole(ERole eRole);
    Optional<User> findById(Long id);

    
    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findUsersByRole(@Param("role") ERole role);
}

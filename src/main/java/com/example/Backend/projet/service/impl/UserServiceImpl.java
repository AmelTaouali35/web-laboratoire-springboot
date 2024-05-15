package com.example.Backend.projet.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Backend.projet.dto.UserInfoDto;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository ;

	@Override
	public ResponseEntity<List<UserInfoDto>> getAll() {
		// TODO Auto-generated method stub

	        return ResponseEntity.ok(
	        					userRepository.findAll()
	        					.stream()
	        					.map(UserInfoDto::fromEntity)
	        					.collect(Collectors.toList())
	        			);
	}
	  @Override
	    public ResponseEntity<User> getUserProfile(Long id) {
	        Optional<User> userOptional = userRepository.findById(id);
	        return userOptional.map(ResponseEntity::ok)
	                           .orElse(ResponseEntity.notFound().build());
	    }
	}
	
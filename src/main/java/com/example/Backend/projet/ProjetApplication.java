package com.example.Backend.projet;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.Backend.projet.model.ERole;
import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication(scanBasePackages = "com.example.Backend.projet")
@RequiredArgsConstructor
public class ProjetApplication implements CommandLineRunner{
	private final UserRepository userRepository ;
	 private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ProjetApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		if(Boolean.FALSE.equals(userRepository.existsByRole(ERole.ROLE_ADMIN)))
		{
			User admin = new User();
			admin.setEmail("admin.com");
			admin.setPassword(passwordEncoder.encode("admin@"));
			admin.setRole(ERole.ROLE_ADMIN);
			userRepository.save(admin);
			
			
		}
		
		
	}
	public class Excel2DatabaseApplication {
	    public static void main(String[] args) {
	        SpringApplication.run(Excel2DatabaseApplication.class, args);
	    }
}
}
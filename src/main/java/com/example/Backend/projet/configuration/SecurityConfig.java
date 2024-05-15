																																															package com.example.Backend.projet.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.Backend.projet.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthFilter jwtAuthFilter ;
	private final UserRepository userRepository ;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http ,  AuthenticationManager authenticationManager ) throws Exception {
        return  http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(
                            request -> request
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/register").permitAll()

                                .requestMatchers("/api/v1/labs/**").permitAll()
                                .requestMatchers("/api/v1/users/**").permitAll()
                                .requestMatchers("/api/v1/upload/**").permitAll()
                                .requestMatchers("/api/v1/profile/**").permitAll()
                               

                                
                                    .anyRequest().authenticated())
                    .authenticationManager(authenticationManager)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();

    }
	// bean userdetailservice
	@Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User does not exist, email: %s", email)));
    }



	// bean password encoder
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService())
                                    .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }







    // Configure CORS settings
	   @Bean
	    public CorsFilter corsFilter()
	    {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        final CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
	        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
	        source.registerCorsConfiguration("/**",corsConfiguration);
	        return new CorsFilter(source);
	    }






	 
	 
	 

	 
}

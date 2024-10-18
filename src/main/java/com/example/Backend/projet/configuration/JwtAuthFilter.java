package com.example.Backend.projet.configuration;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import com.example.Backend.projet.model.User;
import com.example.Backend.projet.repository.UserRepository;
import com.example.Backend.projet.service.JwtService;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	private final  JwtService jwtService ;
	private final UserRepository userRepository ;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 final String authHeader = request.getHeader("Authorization");
		
		 String userEmail ; ; 
		 String jwtToken = null ;
		 
		 if(StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer "))
		 {
			 jwtToken = authHeader.substring(7); 
	            userEmail = jwtService.extractUsername(jwtToken);

		 } else {
	            userEmail = null;
	            logger.warn("JWT Token does not begin with Bearer String");
	        }
		 
		 if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
		 {
	            User user =userRepository.findByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException(String.format("User does not exist, email: %s", userEmail)));
	            
	            if(Boolean.TRUE.equals(jwtService.validateToken(jwtToken, user)))
	            {
	            	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                        user,
	                        null,
	                        user.getAuthorities()
	                );
	                authToken.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(request)
	                );
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
		 }
		 
		 filterChain.doFilter(request, response);
		
	}

}

package com.example.Backend.projet.model;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails  {

    /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String email;
	    private String password;

	    @Enumerated(EnumType.STRING)
	    private ERole role;
	    private String token;
	    private LocalDateTime tokenCreationDate;

	    public User(String email, String password, ERole role) {
	        this.email = email;
	        this.password = password;
	        this.role = role;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

	    public LocalDateTime getTokenCreationDate() {
	        return tokenCreationDate;
	    }

	    public void setTokenCreationDate(LocalDateTime tokenCreationDate) {
	        this.tokenCreationDate = tokenCreationDate;
	    }


	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(new SimpleGrantedAuthority(role.name()));
	    }

	   
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}



	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}



	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}



	

   
}

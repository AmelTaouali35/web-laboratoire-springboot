package com.example.Backend.projet.dto;

import com.example.Backend.projet.model.ERole;
import com.example.Backend.projet.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInfoDto {
	private Long id; 
    private String email;
    private String role;
    
    public static UserInfoDto fromEntity(User user)
    {
    	if(user == null)
    	{
    		return null ;
    	}
    	
    	return UserInfoDto.builder()
    			.id(user.getId())
    			.email(user.getEmail())
    			.role(user.getRole().name())
    			.build();
    }
    
}

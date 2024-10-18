package com.example.Backend.projet.dto;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {
@Email
    private String email;
    private String password;
    private String role;

}
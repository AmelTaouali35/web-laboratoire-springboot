package com.example.Backend.projet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabRequest {
    private String code;
    private String designation;
    private String email;
    private String password;
}

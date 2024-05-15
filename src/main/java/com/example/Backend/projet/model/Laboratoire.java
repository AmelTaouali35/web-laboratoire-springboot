package com.example.Backend.projet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "laboratoire")
@Getter
@Setter
@NoArgsConstructor

public class Laboratoire  extends User{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



    private String code;
    private String designation;
    
   
    
    
    
}

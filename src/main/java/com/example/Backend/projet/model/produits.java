package com.example.Backend.projet.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produits")
public class produits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Utilisez un type approprié pour l'ID (Long)

    @Column(nullable = false) // Assurez-vous d'ajouter les annotations @Column pour chaque champ
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private String codelabo;

    public produits() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public produits(String code, String libelle, String codelabo) {
        this.code = code;
        this.libelle = libelle;
        this.codelabo = codelabo;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCodelabo() {
        return codelabo;
    }

    public void setCodelabo(String codelabo) {
        this.codelabo = codelabo;
    }
}

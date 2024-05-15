package com.example.Backend.projet.dto;

import java.time.LocalDate;

import com.example.Backend.projet.model.Customer;
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
public class LaboratoireProduitDto {
    private String code;
    private String libelle;
    private int stockresoff;
    private int stockdepreg;
    private int Ventedepreg;
    private String cm;
    private String mois;
    private int venteresoff;
    private int entree;
    private int reception;
    private int sdouane;
    private int annocée;
    private int soldecde;
    private int encours;
    private String etat;
    private LocalDate date;

    public static LaboratoireProduitDto fromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }

        return LaboratoireProduitDto.builder()
                .code(customer.getCode())
                .libelle(customer.getLibelle())
                .stockresoff(customer.getStockresoff())
                .stockdepreg(customer.getStockdepreg())
                .Ventedepreg(customer.getVentedepreg())
                .date(customer.getDate())

                .cm(customer.getCm())
                .mois(customer.getMois())
                .venteresoff(customer.getVenteresoff())
                .entree(customer.getEntree())
                .reception(customer.getReception())
                .sdouane(customer.getSdouane())
                .annocée(customer.getAnnocee())
                .soldecde(customer.getSoldecde())
                .encours(customer.getEncours())
                .etat(customer.getEtat())
                .build();
    }
}

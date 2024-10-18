package com.example.Backend.projet.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "laboratoiredata")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;


    @Column(nullable = false)
    private int stockresoff;

    @Column(nullable = false)
    private String cm;

    @Column(nullable = false)
    private int mois;

    @Column(nullable = false)
    private int venteresoff;

    @Column(nullable = false)
    private int stockdepreg;

    @Column(nullable = false)
    private int ventedepreg;

    @Column(nullable = false)
    private int entree;

    @Column(nullable = false)
    private int reception;

    @Column(nullable = false)
    private int sdouane;

    @Column(nullable = false)
    private int annocee;

    @Column(nullable = false)
    private int soldecde;

    @Column(nullable = false)
    private int encours;

    @Column(nullable = false)
    private String etat;

    @ManyToOne
    private Laboratoire laboratoire;

    @Column(name = "date")
    private LocalDate date;

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



	public int getStockresoff() {
		return stockresoff;
	}

	public void setStockresoff(int stockresoff) {
		this.stockresoff = stockresoff;
	}

	public String getCm() {
		return cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	public int getMois() {
		return mois;
	}

	public void setMois(int mois) {
		this.mois = mois;
	}

	public int getVenteresoff() {
		return venteresoff;
	}

	public void setVenteresoff(int venteresoff) {
		this.venteresoff = venteresoff;
	}

	public int getStockdepreg() {
		return stockdepreg;
	}

	public void setStockdepreg(int stockdepreg) {
		this.stockdepreg = stockdepreg;
	}

	public int getVentedepreg() {
		return ventedepreg;
	}

	public void setVentedepreg(int ventedepreg) {
		this.ventedepreg = ventedepreg;
	}

	public int getEntree() {
		return entree;
	}

	public void setEntree(int entree) {
		this.entree = entree;
	}

	public int getReception() {
		return reception;
	}

	public void setReception(int reception) {
		this.reception = reception;
	}

	public int getSdouane() {
		return sdouane;
	}

	public void setSdouane(int sdouane) {
		this.sdouane = sdouane;
	}

	public int getAnnocee() {
		return annocee;
	}

	public void setAnnocee(int annocee) {
		this.annocee = annocee;
	}

	public int getSoldecde() {
		return soldecde;
	}

	public void setSoldecde(int soldecde) {
		this.soldecde = soldecde;
	}

	public int getEncours() {
		return encours;
	}

	public void setEncours(int encours) {
		this.encours = encours;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Laboratoire getLaboratoire() {
		return laboratoire;
	}

	public void setLaboratoire(Laboratoire laboratoire) {
		this.laboratoire = laboratoire;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

    // Getters and setters for all fields
    
    // Constructor, equals, hashCode, toString, etc.
    
}

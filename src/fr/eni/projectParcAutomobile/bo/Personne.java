package fr.eni.projectParcAutomobile.bo;

import java.util.List;

public class Personne 
{

	// déclaration des attributs
	private int idPerso;
	private String nom;
	private String prenom;
	private String nomStructure;
	private String email ;
	private String motDePasse;
	private boolean administrateur ;
	 
	
	
	
	//constructear vide 
	
	public Personne() {
		super();
	}


	//constructeur sand id et sans réservation 


	public Personne(String nom, String prenom, String nomStructure, String email, String motDePasse,
			boolean administrateur) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.nomStructure = nomStructure;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}


	//constructeur avec id et sans réservation 
	
	public Personne(int idPerso, String nom, String prenom, String nomStructure, String email, String motDePasse,
			boolean administrateur) {
		super();
		this.idPerso = idPerso;
		this.nom = nom;
		this.prenom = prenom;
		this.nomStructure = nomStructure;
		this.email = email;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
	}

	
	//getters and setters sans réservation

	public int getIdPerso() {
		return idPerso;
	}


	

	public String getNomStructure() {
		return nomStructure;
	}


	public void setNomStructure(String nomStructure) {
		this.nomStructure = nomStructure;
	}


	public void setIdPerso(int idPerso) {
		this.idPerso = idPerso;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getMotDePasse() {
		return motDePasse;
	}


	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}


	public boolean isAdministrateur() {
		return administrateur;
	}


	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}


	

      // méthode toString sans réservation 
	
    @Override
	public String toString()
    {
		return "Personne [idPerso=" + idPerso + ", nom=" + nom + ", prenom=" + prenom + ", nomStructure=" + nomStructure
				+ ", email=" + email + ", motDePasse=" + motDePasse + ", administrateur=" + administrateur + "]";
	}
	
	
	
	
	
	
}

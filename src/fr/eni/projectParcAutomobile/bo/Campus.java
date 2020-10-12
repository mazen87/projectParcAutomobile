package fr.eni.projectParcAutomobile.bo;

public class Campus 
{

	//déclaration des attributs du campus
	private int idCampus;
	private String libelle;
	//déclaration d'un constructeur avec Id
	public Campus(int idCampus, String libelle) 
	{
		super();
		this.idCampus = idCampus;
		this.libelle = libelle;
	}
	//déclaration d'un constructeur sans Id
	public Campus(String libelle) 
	{
		super();
		this.libelle = libelle;
	}
	//déclaration d'un constructeur vide 
	public Campus() {
		super();
	}
	//déclaration des getters and setters 
	public int getIdCampus() {
		return idCampus;
	}
	public void setIdCampus(int idCampus) {
		this.idCampus = idCampus;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	//déclaration de la méthode toString()
	@Override
	public String toString() {
		return "Campus [idCampus=" + idCampus + ", libelle=" + libelle + "]";
	}
	
	
}

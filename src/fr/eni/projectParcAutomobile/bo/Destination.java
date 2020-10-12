package fr.eni.projectParcAutomobile.bo;

public class Destination 
{
	//déclaration des attribute 
	private int idDes ; 
	private String libelle ;
	private String codeDes;
	
	
	//constructeur vide 
	public Destination() 
	{
		super();
	}
	//constructeur san id 
     public Destination(String libelle, String codeDes)
     {
		super();
		this.libelle = libelle;
		this.codeDes = codeDes;
	}
	//constructeur avec id 
	public Destination(int idDes, String libelle, String codeDes) {
		super();
		this.idDes = idDes;
		this.libelle = libelle;
		this.codeDes = codeDes;
	}
	
	//getters and setters 
	public int getIdDes() {
		return idDes;
	}
	public void setIdDes(int idDes) {
		this.idDes = idDes;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCodeDes() {
		return codeDes;
	}
	public void setCodeDes(String codeDes) {
		this.codeDes = codeDes;
	}
	
	//méthode toString 
	@Override
	public String toString() {
		return "Destination [idDes=" + idDes + ", libelle=" + libelle + ", codeDes=" + codeDes + "]";
	}
     
}

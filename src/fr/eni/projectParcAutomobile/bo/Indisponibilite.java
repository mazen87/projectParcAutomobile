package fr.eni.projectParcAutomobile.bo;

import java.util.Date;

public class Indisponibilite 
{
//déclaration des attributs 
	private int idIndis ; 
	private Date dateDebut ;
	private Date dateFin ;
	private String motifIndisponibilite;
	private Vehicule vehicule;
	
	// construcyeur vide 
	public Indisponibilite() 
	{
		super();
	}
	

	//constructeur sans id 
	public Indisponibilite(Date dateDebut, Date dateFin, String motifIndisponibilite, Vehicule vehicule) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.motifIndisponibilite = motifIndisponibilite;
		this.vehicule = vehicule;
	}


	//constructeur avec id 

	public Indisponibilite(int idIndis, Date dateDebut, Date dateFin, String motifIndisponibilite, Vehicule vehicule) {
		super();
		this.idIndis = idIndis;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.motifIndisponibilite = motifIndisponibilite;
		this.vehicule = vehicule;
	}
	//getters and setters 


	public int getIdIndis() {
		return idIndis;
	}


	public void setIdIndis(int idIndis) {
		this.idIndis = idIndis;
	}


	public Date getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Date getDateFin() {
		return dateFin;
	}


	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}


	public String getMotifIndisponibilite() {
		return motifIndisponibilite;
	}


	public void setMotifIndisponibilite(String motifIndisponibilite) {
		this.motifIndisponibilite = motifIndisponibilite;
	}


	public Vehicule getVehicule() {
		return vehicule;
	}


	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}


	//méthode to string

	@Override
	public String toString() {
		return "Indisponibilite [idIndis=" + idIndis + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin
				+ ", motifIndisponibilite=" + motifIndisponibilite + ", vehicule=" + vehicule + "]";
	}
	
}



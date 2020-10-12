package fr.eni.projectParcAutomobile.bo;

import java.time.LocalDateTime;
import java.util.Date;

public class Reservation 
{

	//déclaration des attributs
	private int idRes ;
	private Date dateHeureRes ;
	private Date dateHeureFin;
	private String motif ; 
	private Destination destination ;
	private Personne personne ; 
	private Vehicule vehicule ;
	private LocalDateTime dateHeureRes1;
	private LocalDateTime dateHeureFin1;
	
	
	//constructeur vide 
	public Reservation() 
	{
		super();
	}

	//constructeur sans idRes
	public Reservation(Date dateHeureRes, Date dateHeureFin, String motif, Destination destination, Personne personne,
			Vehicule vehicule) {
		super();
		this.dateHeureRes = dateHeureRes;
		this.dateHeureFin = dateHeureFin;
		this.motif = motif;
		this.destination = destination;
		this.personne = personne;
		this.vehicule = vehicule;
	}
	

	//constructeur avec idRes
	public Reservation(int idRes, Date dateHeureRes, Date dateHeureFin, String motif, Destination destination,
			Personne personne, Vehicule vehicule) {
		super();
		this.idRes = idRes;
		this.dateHeureRes = dateHeureRes;
		this.dateHeureFin = dateHeureFin;
		this.motif = motif;
		this.destination = destination;
		this.personne = personne;
		this.vehicule = vehicule;
	}
	
	//déclaration des getters and setters 

	public LocalDateTime getDateHeureRes1() {
		return dateHeureRes1;
	}

	public void setDateHeureRes1(LocalDateTime dateHeureRes1) {
		this.dateHeureRes1 = dateHeureRes1;
	}

	public LocalDateTime getDateHeureFin1() {
		return dateHeureFin1;
	}

	public void setDateHeureFin1(LocalDateTime dateHeureFin1) {
		this.dateHeureFin1 = dateHeureFin1;
	}

	public int getIdRes() {
		return idRes;
	}

	public void setIdRes(int idRes) {
		this.idRes = idRes;
	}

	public Date getDateHeureRes() {
		return dateHeureRes;
	}

	public void setDateHeureRes(Date dateHeureRes) {
		this.dateHeureRes = dateHeureRes;
	}

	public Date getDateHeureFin() {
		return dateHeureFin;
	}

	public void setDateHeureFin(Date dateHeureFin) {
		this.dateHeureFin = dateHeureFin;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Vehicule getVehicule() {
		return vehicule;
	}

	public void setVehicule(Vehicule vehicule) {
		this.vehicule = vehicule;
	}

	//définition de la méthode toString 
	@Override
	public String toString() {
		return "Reservation [idRes=" + idRes + ", dateHeureRes=" + dateHeureRes + ", dateHeureFin=" + dateHeureFin
				+ ", motif=" + motif + ", destination=" + destination + ", personne=" + personne + ", vehicule="
				+ vehicule + "]";
	}
	
	
	
	
	
	
}

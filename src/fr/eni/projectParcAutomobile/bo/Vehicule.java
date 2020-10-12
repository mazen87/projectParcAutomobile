package fr.eni.projectParcAutomobile.bo;

import java.util.Date;

public class Vehicule 
{
//déclaration desattributs 
	private int idVehic;
	private String designation;
	private String immatriculation;
	private Date dateAchat ; 
	private Campus campus;
	
	// constructeur vide 
	public Vehicule() {
		super();
	}
	//constructeur sans idVehic 

	public Vehicule(String designation, String immatriculation, Date dateAchat, Campus campus) {
		super();
		this.designation = designation;
		this.immatriculation = immatriculation;
		this.dateAchat = dateAchat;
		this.campus = campus;
	}
	//constructeur avec idVehic
	public Vehicule(int idVehic, String designation, String immatriculation, Date dateAchat, Campus campus) {
		super();
		this.idVehic = idVehic;
		this.designation = designation;
		this.immatriculation = immatriculation;
		this.dateAchat = dateAchat;
		this.campus = campus;
	}

	// les getters and setters 
	public int getIdVehic() {
		return idVehic;
	}

	public void setIdVehic(int idVehic) {
		this.idVehic = idVehic;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}

	public Date getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(Date dateAchat) {
		this.dateAchat = dateAchat;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	//méthode toString 

	@Override
	public String toString() {
		return "Vehicule [idVehic=" + idVehic + ", designation=" + designation + ", immatriculation=" + immatriculation
				+ ", dateAchat=" + dateAchat + ", campus=" + campus + "]";
	}
	
	
	
}

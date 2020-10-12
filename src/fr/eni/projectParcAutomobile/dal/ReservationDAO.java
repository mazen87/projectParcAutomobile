package fr.eni.projectParcAutomobile.dal;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

public interface ReservationDAO
{
//méthode pour insérer une réservation à la base de données 
	public int insertReservation (Reservation reservation) throws BusinessException ;
	//méthode pour récupérer une indisponibilité  par rapport à la période d'une réservation 
	public Indisponibilite selectindisponibiliteByDateResAndIdVehic (Date dateRes ,Date dateFin , int idVehic ) throws BusinessException;
	//méthode pour récupérer une indisponibilité  par rapport à la période d'une réservation Version 2
	public Indisponibilite selectindisponibiliteByDateResAndIdVehicVersion2 (Date dateRes ,Date dateFin , int idVehic)throws BusinessException;
	//méthode pour récupérer une réservation par rapport à la période d'une réservation 
	public Reservation selectReservationByDateResAndIdVehic (Date dateRes , Date dateFin , int idVehic) throws BusinessException;
	//méthode pour récupérer une réservation par rapport à la période d'une réservation 
	public Reservation selectReservationByDateResAndIdVehicVersion2(Date dateRes ,Date dateFin , int idVehic) throws BusinessException;
	
	//méthodes pour valider le véhicule choisi pour la Réservation 
	public Reservation selectReservationParDateDebutFinAndIdVehic(LocalDateTime dateHeureRes , LocalDateTime dateHeureFin ,int idVehic)throws BusinessException;
	public Reservation selectReservationParDateDebutAndIdVehic(LocalDateTime dateHeureRes ,int idVehic)throws BusinessException;
	public Indisponibilite selectIndisponibiliteByDateDebutResAndIdVehic (LocalDateTime dateHeureRes , int idVehic)throws BusinessException ;
	public Indisponibilite selectIndisponibiliteByDateDebutDateFinResAndIdVehic (LocalDateTime dateHeureRes,LocalDateTime dateHeureFin , int idVehic)throws BusinessException;
    public Reservation selectReservationParDateDebutFinAndIdVehicModifier (LocalDateTime dateHeureRes , LocalDateTime dateHeureFin ,int idVehic , int idRes) throws BusinessException;
	public Reservation selectReservationParDateDebutAndIdVehicModifier (LocalDateTime dateHeureRes ,int idVehic,int idRes)throws BusinessException;
    
	//méthodes pour valider la personne choisie pour la Réservation 
	public Reservation selectReservationByDateHeurDebutResAndIdPerso(LocalDateTime dateHeureRes , int idPerso)throws BusinessException;
	public Reservation selectReservationByDateHeureDebutFinReAndIdPerso (LocalDateTime dateHeureRes ,LocalDateTime dateHeureFin , int idPerso)throws BusinessException;

	//méthode pour valider la personne choisi pour la Réservation à modifier 
	public Reservation selectReservationByDateHeurDebutResAndIdPersoModifier(LocalDateTime dateHeureRes , int idPerso,int idRes) throws BusinessException;
	public Reservation selectReservationByDateHeureDebutFinResAndIdPersoModifier (LocalDateTime dateHeureRes ,LocalDateTime dateHeureFin , int idPerso, int idRes)throws BusinessException;

    // méthode pour afficher les Réservation dans la page d'accueil 
	public List<Reservation> selectResevationsParMois (Date dateDeJour) throws BusinessException;

	//méthode pour récupéere une réservation par son id 
	public Reservation selectReservationByIdRes (int idRes) throws BusinessException;
	
	// méthode pour modifier une réservation 
	public void modifierReservation (Reservation reservation , int idRes) throws BusinessException ;
    //méthode pour supprimer une Réservation 
	public void supprimerReservation (int idRes) throws BusinessException;
	//méthode pour récupérer les Réservation par nom et prénom d'utilisateur 
	public List<Reservation> selectListeReservationParPersonne (String nom , String prenom) throws BusinessException;

}


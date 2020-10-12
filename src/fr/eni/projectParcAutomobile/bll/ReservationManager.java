package fr.eni.projectParcAutomobile.bll;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.dal.ReservationDAO;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

public class ReservationManager
{
	private ReservationDAO reservationDAO ;

	public ReservationManager() 
	{

		this.reservationDAO = DAOFactory.getReservationDAO();
	}
	
	//méthode qui appelle la DAL pour insérer une réservation à la base de données 
	public void ajouterReservation (Reservation reservation) throws BusinessException 
	{
		if(validerReservation(reservation)) {
			reservationDAO.insertReservation(reservation);
		     }
	}
	
	
	//méthode qui vérifier l'objet Réservation avant de le envoyer vers la base de données 
	public boolean validerReservation (Reservation reservation) throws BusinessException 
	{
		StringBuilder sb = new StringBuilder();
		boolean reservationEstValide = true ;
		LocalDateTime dateDeJour = LocalDateTime.now();
		//valider la date et l'heure  du début de la Réservation avec la date et l'heure actuelle (au moment de réservation ) 
		if ((reservation.getDateHeureRes1().minusMinutes(60).isBefore(dateDeJour))||(reservation.getDateHeureRes1().isBefore(dateDeJour)) ) 
		{
			sb.append(" la date Du début de la Réservation doit être postérieure à la date actuelle par une Heure au minimum  . ");
			reservationEstValide = false;
		}
		//valider la date et l'heure de fin de réservation avec la date et l'heure du début de la réservation 
		if(reservation.getDateHeureFin1().isBefore(reservation.getDateHeureRes1())|| 
				reservation.getDateHeureFin1().minusMinutes(60).isBefore(reservation.getDateHeureRes1())) 
		{sb.append(" la date de fin de la Réservation doit être postérieure à la date du Début de Réservation par une heure  au minimum. ");
			reservationEstValide = false;	}
		// valider le motif de la réservation 
		if(reservation.getMotif() == null || reservation.getMotif().trim().length() == 0 || reservation.getMotif().trim().length()>250) 
		{sb.append(" le motif doit être renseigné et ne dépasse pas 250 caractères. ") ;
			reservationEstValide = false ; }
		//valider la zone de saisie du motif 
		if(!detecterCaracterNonValide(reservation.getMotif())) 
		{
			sb.append(" la zone de saisie du motif n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	reservationEstValide = false ; 
		}
		/*
	    if(reservation.getMotif().trim().contains("<")||reservation.getMotif().trim().contains(">")||reservation.getMotif().trim().contains(",")||
	    		reservation.getMotif().trim().contains("?")||reservation.getMotif().trim().contains(";")||reservation.getMotif().trim().contains("/")||
	    		reservation.getMotif().trim().contains("!")||reservation.getMotif().trim().contains("§")||reservation.getMotif().trim().contains("*")||
	    		reservation.getMotif().trim().contains("%")||reservation.getMotif().trim().contains("$")||reservation.getMotif().trim().contains("£")||
	    		reservation.getMotif().trim().contains("¤")||reservation.getMotif().trim().contains("+")||reservation.getMotif().trim().contains("}")||
	    		reservation.getMotif().trim().contains("=")||reservation.getMotif().trim().contains("^")||reservation.getMotif().trim().contains(")")||
	    		reservation.getMotif().trim().contains("°")||reservation.getMotif().trim().contains("]")||reservation.getMotif().trim().contains("@")||
	    		reservation.getMotif().trim().contains("\\")||reservation.getMotif().trim().contains("`")||reservation.getMotif().trim().contains("|")||
	    		reservation.getMotif().trim().contains("(")||reservation.getMotif().trim().contains("[")||reservation.getMotif().trim().contains("{")||
	    		reservation.getMotif().trim().contains("\"")||reservation.getMotif().trim().contains("#")||reservation.getMotif().trim().contains("~")||
	    		reservation.getMotif().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie du motif n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	reservationEstValide = false ; 
	    }
	    */
	    
		/*
		//valider la disponibilité du véhicule
		if(reservation.getVehicule().getIdVehic() != 0) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		String dateResString = reservation.getDateHeureRes().toString();
		String dateFinString = reservation.getDateHeureFin().toString();
		try {
			dateResDate = sdf1.parse(dateResString);
			dateFinDate = sdf1.parse(dateFinString);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		*/
		//tester le véhicule 
		//tester si le véhicule a une ou des Réservation pendant la période de Réservation à ajouter 
		
		if(reservation.getVehicule()!= null)
		{
			if(reservation.getVehicule().getIdVehic() != 1028) 
			{
		if(!(verifierVehiculeParDateDebutFinResAndIdVehic(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getVehicule().getIdVehic()))|| !(verifierVehiculeParDateDebutResAndIdVehic(reservation.getDateHeureRes1(), reservation.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule choisi a une ou des réservations pendant la péroiode de la Réservation que vous avez saisie. ");
			reservationEstValide = false;
		}
		// tester si le véhicule est disponible pendant la période de Réservation à ajouter 
		if(!(verifierVehiculeParDateDebutResAndIdVehicIndisponibilite(reservation.getDateHeureRes1(), reservation.getVehicule().getIdVehic())) || !(verifierVehiculeParDateDebutDateFinResAndIdVehicIndisponibilite(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule ne sera pas disponible pour la période de la Réservation que vous aves saisie. ");
			reservationEstValide = false ;
		}
		}
		}	// fin le test du véhicule 
		/*
		 * 
		 */
		/*
		if(!verifierDisponibiliteVehicule(dateResDate, dateFinDate,reservation.getVehicule().getIdVehic())) 
		{
			sb.append(" le véhicule choisi ne sera pas disponible pendant la période de Votre réservation. ");
			reservationEstValide = false ;
		}
		//valider la disponibilité du véhicule par la vérification si la période de réservation contient une indisponibilité  
		if(!verifierDisponibiliteVehiculeVersion2(dateResDate, dateFinDate, reservation.getVehicule().getIdVehic())) 
		{
			sb.append(" la période de votre réservtion contient une période d'indisponibilité pour le véhicule choisi. ");
			reservationEstValide = false ;
		}
		//valider si le véhicule est déjà réservé pendant la période de réservation à ajouter 
		if(!verifierVehiculeEstReserveParDateRes(reservation.getDateHeureRes(),reservation.getDateHeureFin(), reservation.getVehicule().getIdVehic())) 
		{
			sb.append(" le véhicule est réservé pendant la même période de votre réservation. ");
			reservationEstValide =false;
		}
		//valider si le véhicule est déjà réservé pendant la période de réservation à ajouter Version2
		if(!verifierVehiculeEstReserveParDateResVersion2(reservation.getDateHeureRes(), reservation.getDateHeureFin(), reservation.getVehicule().getIdVehic())) 
		{
			sb.append(" le période de votre réservation contient une période d'indisponibilité pour le véhicule choisi. ");
			reservationEstValide = false ;
		}

		} // fin du validation du véhicule 
		*/
		//valider la disponibilite de la personne 
		if(reservation.getPersonne()== null) 
		{
			sb.append(" vous devez choisir une perssone à laquelle la réservation va être associée. ");
			reservationEstValide = false ;
		}
		if(reservation.getPersonne() != null) {
		if(!(verifierPersonneParDateDebutFinResEtIdPerso(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getPersonne().getIdPerso())) || !(verifierPersonneParDateDebutResEtIdPerso(reservation.getDateHeureRes1(), reservation.getPersonne().getIdPerso())))
		{
			sb.append(" la personne choisie pour la Réservation à Ajouter a une ou des Autres Resrvations pour la même période que vous avez choisie. ");
			reservationEstValide = false ;
		}
		}// fin la validation de la personne
		
		//valider la destination 
		if(reservation.getDestination() == null) 
		{
			sb.append(" vous devez choisir une destination à laquelle la réservation va être associée. ");
			reservationEstValide = false;
		}
		
		if(!reservationEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return reservationEstValide ;
	}
	
	//méthode qui fait appel au DAL pour vérifier si le véhicule sera disponible ou pas 
	public boolean verifierDisponibiliteVehicule (Date dateRes ,Date dateFin , int idVehic)throws BusinessException 
	{
		boolean vehiculeDisponible = true ;
		Indisponibilite indisponibilite = new Indisponibilite();
		indisponibilite = reservationDAO.selectindisponibiliteByDateResAndIdVehic(dateRes, dateFin, idVehic);
		if(indisponibilite.getMotifIndisponibilite() != null ) 
		{
			vehiculeDisponible =false ;
		}
		return vehiculeDisponible;
	}
	
	//méthode qui fait appel au DAL pour vérifier si la période de la réservation à ajouter contient une période d'une indisponibilité ou pas 
	public boolean verifierDisponibiliteVehiculeVersion2 (Date dateRes ,Date dateFin ,int idVehic) throws BusinessException
	{
		boolean vehiculeEstDisponible = true ;
		Indisponibilite indisponibilite = new Indisponibilite();
		indisponibilite = reservationDAO.selectindisponibiliteByDateResAndIdVehicVersion2(dateRes, dateFin, idVehic);
		if(indisponibilite.getMotifIndisponibilite()!=null) 
		{
			vehiculeEstDisponible =false ; 
		}
		return vehiculeEstDisponible;
	}
	
	//méthode qui fait appel au DAL pour vérifier si le véhicle est réservé pour la même période de la réservation à ajouter
	public boolean verifierVehiculeEstReserveParDateRes (Date dateRes , Date dateFin , int idVehic) throws BusinessException
	{
		boolean vehiculeEstReserve = true ;
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationByDateResAndIdVehic(dateRes, dateFin, idVehic);
		if(reservation.getMotif() != null && reservation.getIdRes()!=0) 
		{
			vehiculeEstReserve = false;
		}
		return vehiculeEstReserve;
		
	}
	
	//méthode qui fait appel au DAL pour vérifier si la période de réservation à ajouter contient une autre période de réservation pour le même véhicule 
	public boolean verifierVehiculeEstReserveParDateResVersion2(Date dateRes ,Date dateFin , int idVehic)throws BusinessException
	{
		boolean vehiculeEstReserve = true ; 
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationByDateResAndIdVehicVersion2(dateRes, dateFin, idVehic);
		if(reservation.getMotif()!=null && reservation.getIdRes()!=0) 
		{
			vehiculeEstReserve = false ;
		}
		return vehiculeEstReserve;
	}
	
	//méthode qui fait Appel au DAL pour vérifier si le véhicule a des réservations pendant  la période de nouvelle réservation à ajouter 
	public boolean verifierVehiculeParDateDebutFinResAndIdVehic (LocalDateTime dateHeureRes ,LocalDateTime dateHeureFin,int idVehic) throws BusinessException
	{
		boolean vehiculeEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationParDateDebutFinAndIdVehic(dateHeureRes, dateHeureFin, idVehic);
		if(reservation.getMotif()!= null) 
		{
			vehiculeEstValide = false ; 
		}
		return vehiculeEstValide;
	}
	
	//méthode qui fait Appel au DAL pour vérifier si le véhicule a des réservations pendant la période la période de nouvelle réservation à ajouter Partie2
    public boolean verifierVehiculeParDateDebutResAndIdVehic (LocalDateTime dateHeureRes , int idVehic)throws BusinessException 
    {
    	boolean vehiculeEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationParDateDebutAndIdVehic(dateHeureRes, idVehic);
		if(reservation.getMotif()!= null) 
		{
			vehiculeEstValide = false ; 
		}
		return vehiculeEstValide;
    } 
    
    //méthode qui fait appel au DAL pour vérifier si le véhicule choisi pour la réservation à ajouter sera disponible  
    public boolean verifierVehiculeParDateDebutResAndIdVehicIndisponibilite(LocalDateTime dateHeureRes , int idVehic) throws BusinessException
    {
    	Indisponibilite indisponibilite = new Indisponibilite();
    	indisponibilite = reservationDAO.selectIndisponibiliteByDateDebutResAndIdVehic(dateHeureRes, idVehic);
    	boolean vehiculeDisponible = true ;
    	if(indisponibilite.getMotifIndisponibilite() !=null) 
    	{
    		vehiculeDisponible = false ;
    	}
    	return vehiculeDisponible;
    } 
    
    //méthode qui fait appel au DAL pour vérifier si le véhicule choisi pour la réservation à ajouter sera disponible  partie2

    public boolean verifierVehiculeParDateDebutDateFinResAndIdVehicIndisponibilite(LocalDateTime dateHeureRes ,LocalDateTime dateHeureFin , int idVehic) throws BusinessException
    {
    	Indisponibilite indisponibilite = new Indisponibilite();
    	indisponibilite = reservationDAO.selectIndisponibiliteByDateDebutDateFinResAndIdVehic(dateHeureRes,dateHeureFin,idVehic);
    	boolean vehiculeDisponible = true ;
    	if(indisponibilite.getMotifIndisponibilite() !=null) 
    	{
    		vehiculeDisponible = false ;
    	}
    	return vehiculeDisponible;
    } 
    
    
	//méthode qui fait Appel au DAL pour vérifier si le véhicule a des réservations pendant  la période de  réservation à modifier  
    public boolean verifierVehiculeParDateDebutFinResAndIdVehicModifier (LocalDateTime dateHeureRes ,LocalDateTime dateHeureFin,int idVehic,int idRes) throws BusinessException
	{
		boolean vehiculeEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationParDateDebutFinAndIdVehicModifier(dateHeureRes, dateHeureFin, idVehic,idRes);
		if(reservation.getMotif()!= null) 
		{
			vehiculeEstValide = false ; 
		}
		return vehiculeEstValide;
	} 
    
	//méthode qui fait Appel au DAL pour vérifier si le véhicule a des réservations pendant la période la période de réservation à modifier  Partie2
    public boolean verifierVehiculeParDateDebutResAndIdVehicModifier (LocalDateTime dateHeureRes , int idVehic,int idRes)throws BusinessException 
    {
    	boolean vehiculeEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = reservationDAO.selectReservationParDateDebutAndIdVehicModifier(dateHeureRes, idVehic,idRes);
		if(reservation.getMotif()!= null) 
		{
			vehiculeEstValide = false ; 
		}
		return vehiculeEstValide;
    } 
    
    //méthode qui fait appel au DAL pour vérifier si la personne choisie sera disponible pendant la période de la Réservation à àjouter 
    public boolean verifierPersonneParDateDebutResEtIdPerso(LocalDateTime dateHeureRes, int idPerso)throws BusinessException
    {
    	boolean personneEstDisponible = true ;
    	Reservation reservation = new Reservation();
    	reservation = reservationDAO.selectReservationByDateHeurDebutResAndIdPerso(dateHeureRes, idPerso);
    	if(reservation.getMotif()!= null ) 
    	{
    		personneEstDisponible = false ;
    	}
    	return personneEstDisponible;
    }
    
    //méthode qui fait appel au DAL pour vérifier si la personne choisie sera disponible pendant la période de la Réservation à àjouter partie 2 
    public boolean verifierPersonneParDateDebutFinResEtIdPerso (LocalDateTime dateHeureRes, LocalDateTime dateHeureFin , int idPerso) throws BusinessException
    {
    	boolean personneEstDisponible = true ;
    	Reservation reservation = new Reservation();
    	reservation = reservationDAO.selectReservationByDateHeureDebutFinReAndIdPerso(dateHeureRes, dateHeureFin, idPerso);
    	if(reservation.getMotif()!= null ) 
    	{
    		personneEstDisponible = false ;
    	}
    	return personneEstDisponible;
    }
    
    //méthode qui fait appel au DAL pour vérifier si la personne choisie sera disponible pendant la période de la Réservation à modifier 
    public boolean verifierPersonneParDateDebutResEtIdPersoModifier(LocalDateTime dateHeureRes, int idPerso,int idRes)throws BusinessException
    {
    	boolean personneEstDisponible = true ;
    	Reservation reservation = new Reservation();
    	reservation = reservationDAO.selectReservationByDateHeurDebutResAndIdPersoModifier(dateHeureRes, idPerso,idRes);
    	if(reservation.getMotif()!= null ) 
    	{
    		personneEstDisponible = false ;
    	}
    	return personneEstDisponible;
    }
  
  //méthode qui fait appel au DAL pour vérifier si la personne choisie sera disponible pendant la période de la Réservation à àjouter partie 2 quand on modifie la Réservation
    public boolean verifierPersonneParDateDebutFinResEtIdPersoModifier (LocalDateTime dateHeureRes, LocalDateTime dateHeureFin , int idPerso, int idRes) throws BusinessException
    {
    	boolean personneEstDisponible = true ;
    	Reservation reservation = new Reservation();
    	reservation = reservationDAO.selectReservationByDateHeureDebutFinResAndIdPersoModifier(dateHeureRes, dateHeureFin, idPerso , idRes);
    	if(reservation.getMotif()!= null ) 
    	{
    		personneEstDisponible = false ;
    	}
    	return personneEstDisponible;
    }
    
    //méthode qui fait appel au DAL pour récupérer les Réservations seleon le mois choisi 
    public List<Reservation> selectlisteReservationsParMois (Date dateDeJour) throws BusinessException
    {
    	List<Reservation> listeReservations = new ArrayList<Reservation>();
    	listeReservations = reservationDAO.selectResevationsParMois(dateDeJour) ;
    	
    	return listeReservations;
    } 
    
    //méthode qui fait appel au DAL pour récupérer une Réservation selon son id 
    public Reservation selctReservationByIdRes (int idRes) throws BusinessException 
    {
    	Reservation reservation = new Reservation();
    	reservation = reservationDAO.selectReservationByIdRes(idRes);
    	
    	return reservation;
    }
    
    //méthode qui fait Appel au DAL pour modifier une Réservation 
    public void modifierReservation (Reservation reservation , int idRes)throws BusinessException 
    {
    	if(validerReservationModifier(reservation))
    	{
    	reservationDAO.modifierReservation(reservation, idRes);
    	}
    }
    
    //méthode pour valider la Réservation à modifier 
    public boolean validerReservationModifier (Reservation reservation) throws BusinessException 
    {
    	StringBuilder sb = new StringBuilder();
		boolean reservationEstValide = true ;
		LocalDateTime dateDeJour = LocalDateTime.now();
		//valider la date et l'heure  de Réservation avec la date et l'heure actuelle (au moment de réservation ) 
		if ((reservation.getDateHeureRes1().minusMinutes(60).isBefore(dateDeJour))||(reservation.getDateHeureRes1().isBefore(dateDeJour)) ) 
		{
			sb.append(" la date du début de la Réservation doit être postérieure à la date actuelle par une Heure au minimum  . ");
			reservationEstValide = false;
		}
		
		//valider la date et l'heure de fin de réservation avec la date et l'heure de la réservation 
		if(reservation.getDateHeureFin1().isBefore(reservation.getDateHeureRes1())|| reservation.getDateHeureFin1().minusMinutes(60).isBefore(reservation.getDateHeureRes1())) 
		{
			sb.append(" la date de fin de la Réservation doit être postérieure à la date du Début de la Réservation par une heure au minimum. ");
			reservationEstValide = false;
		}
		// valider le motif de la réservation 
		if(reservation.getMotif() == null || reservation.getMotif().trim().length() == 0 || reservation.getMotif().trim().length()>250) 
		{
			sb.append(" le motif doit être renseigné et ne dépasse pas 250 caractères. ") ;
			reservationEstValide = false ; 
		}
		//valider la zone de saisie du motif 
	    if(reservation.getMotif().trim().contains("<")||reservation.getMotif().trim().contains(">")||reservation.getMotif().trim().contains(",")||reservation.getMotif().trim().contains("?")||reservation.getMotif().trim().contains(";")||reservation.getMotif().trim().contains("/")||reservation.getMotif().trim().contains("!")||reservation.getMotif().trim().contains("§")||reservation.getMotif().trim().contains("*")||reservation.getMotif().trim().contains("%")||reservation.getMotif().trim().contains("$")||reservation.getMotif().trim().contains("£")||reservation.getMotif().trim().contains("¤")||reservation.getMotif().trim().contains("+")||reservation.getMotif().trim().contains("}")||reservation.getMotif().trim().contains("=")||reservation.getMotif().trim().contains("^")||reservation.getMotif().trim().contains(")")||reservation.getMotif().trim().contains("°")||reservation.getMotif().trim().contains("]")||reservation.getMotif().trim().contains("@")||reservation.getMotif().trim().contains("\\")||reservation.getMotif().trim().contains("`")||reservation.getMotif().trim().contains("|")||reservation.getMotif().trim().contains("(")||reservation.getMotif().trim().contains("[")||reservation.getMotif().trim().contains("{")||reservation.getMotif().trim().contains("\"")||reservation.getMotif().trim().contains("#")||reservation.getMotif().trim().contains("~")||reservation.getMotif().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie du motif  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	reservationEstValide = false ; 
	    }
		
		//tester le véhicule 
		//tester si le véhicule a une ou des Réservation pendant la période de Réservation à ajouter 
		
		if(reservation.getVehicule()!= null)
		{
			if(reservation.getVehicule().getIdVehic() != 1028) 
			{
		if(!(verifierVehiculeParDateDebutFinResAndIdVehicModifier(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getVehicule().getIdVehic(),reservation.getIdRes()))|| !(verifierVehiculeParDateDebutResAndIdVehicModifier(reservation.getDateHeureRes1(), reservation.getVehicule().getIdVehic(),reservation.getIdRes()))) 
		{
			sb.append(" le véhicule choisi a une ou des réservations pendant la péroiode de la Réservation que vous avez saisie. ");
			reservationEstValide = false;
		}
		// tester si le véhicule est disponible pendant la période de Réservation à ajouter 
		if(!(verifierVehiculeParDateDebutResAndIdVehicIndisponibilite(reservation.getDateHeureRes1(), reservation.getVehicule().getIdVehic())) || !(verifierVehiculeParDateDebutDateFinResAndIdVehicIndisponibilite(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule ne sera pas disponible pour la période de la Réservation que vous aves saisie. ");
			reservationEstValide = false ;
		}
		}
		}	// fin le test du véhicule 
		
		//valider la disponibilite de la personne 
		if(reservation.getPersonne()== null) 
		{
			sb.append(" vous devez choisir une perssone à laquelle la réservation va être associée. ");
			reservationEstValide = false ;
		}
		if(reservation.getPersonne() != null) {
		if(!(verifierPersonneParDateDebutFinResEtIdPersoModifier(reservation.getDateHeureRes1(), reservation.getDateHeureFin1(), reservation.getPersonne().getIdPerso(),reservation.getIdRes())) || !(verifierPersonneParDateDebutResEtIdPersoModifier(reservation.getDateHeureRes1(), reservation.getPersonne().getIdPerso(),reservation.getIdRes())))
		{
			sb.append(" la personne choisie pour la Réservation à Ajouter a une ou des Autres Resrvations pour la même période que vous avez choisie. ");
			reservationEstValide = false ;
		}
		}// fin la validation de la personne
		
		//valider la destination 
		if(reservation.getDestination() == null) 
		{
			sb.append(" vous devez choisir une déstination à laquelle la réservation va être associée. ");
			reservationEstValide = false;
		}
		
		if(!reservationEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return reservationEstValide ;
    }
    
    //méthode qui fait appel au DAL pour supprimer une Réservation 
    public void supprimerReservation (int idRes) throws BusinessException
    {
    	if (idRes != 0) 
    	{
    		reservationDAO.supprimerReservation(idRes);
    	}
    }
    
    //méthode qui fait appel au DAL pour récupérer les Réservation par nom et Prénom d'une Personne 
    public List<Reservation> selectReservationsParNomPrenomPersonne (String nom ,String prenom) throws BusinessException
    {
    	List<Reservation> listeReservations = new ArrayList<Reservation>();
    	if(nom != null && prenom != null ) 
    	{
    		listeReservations = reservationDAO.selectListeReservationParPersonne(nom, prenom);
    	}
    	return listeReservations;
    }
    // méthode pour détécter les caractères non valides 
    public boolean detecterCaracterNonValide (String phrase)  
    	{
    	String[] caracteresNonValides = {"&","#","\"","'","{","(","[","|","~","`","\\","^","@",")","]","°","+","=","}","£","$","¤","^","¨","%","µ","*","<",">","?",",",";","/","!","§","."};
    	boolean estvalide = true ; 
    	for(int i=0;i<caracteresNonValides.length;i++) 
	    	{
	    		if(phrase.trim().contains(caracteresNonValides[i])) 
	    			{
	    			estvalide = false;
	    			}
	    	}
    	return estvalide;
    	}  
    
}

package fr.eni.projectParcAutomobile.bll;

import java.nio.channels.GatheringByteChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.dal.IndisponibiliteDAO;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class IndisponibiliteManager 
{

	private IndisponibiliteDAO indisponibiliteDAO;

	public IndisponibiliteManager() 
	{
	
		this.indisponibiliteDAO = DAOFactory.getIndisponibiliteDAO();
	}
	
	//méthode pour valider l'objet indisponibilité avant de le envoyer vers le DAL 
	public boolean validerIndisponibilite (Indisponibilite indisponibilite) throws BusinessException 
	{
		StringBuilder sb = new StringBuilder();
		boolean estValide = true ; 
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateDeJourString = sdf.format(new Date());
		Date dateDeJour = null ;
		try {
			dateDeJour = sdf.parse(dateDeJourString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		//System.out.println(dateDeJour);
		//dateDeJour.setHours(00);
		//dateDeJour.setMinutes(00);
		//dateDeJour.setSeconds(00);
		
		
		//System.out.println(dateDeJour.toString());
		//System.out.println(indisponibilite.getDateDebut().toString());
		//System.out.println(indisponibilite.getDateFin().toString());
		
		//tester si l'objet est null 
		if (indisponibilite.getMotifIndisponibilite() == null ) 
		{
			sb.append(" aucune indisponibilité à insérer. ");
			estValide = false ; 
		}
		//tester la date de début d'indisponibilité 
		//long dd = indisponibilite.getDateDebut().getTime();
		//long ddj = dateDeJour.getTime();
		if(indisponibilite.getDateDebut().compareTo(dateDeJour) <0)
		{
			sb.append(" la date de début doit être égale ou postérieure à la date de Jour. ");
			estValide = false ;
		}
		//tester la date de fin d'indisponibilité 
		if(indisponibilite.getDateFin().compareTo(indisponibilite.getDateDebut())<0) 
		{
			sb.append(" la date de fin doit être égale ou postérieure à la date de début. ");
			estValide = false ; 
		}
		//tester le motif
		if(indisponibilite.getMotifIndisponibilite()== null || indisponibilite.getMotifIndisponibilite().trim().length()==0||indisponibilite.getMotifIndisponibilite().trim().length()>250) 
		{
			sb.append(" le motif doit être renseigné et ne dépasse pas 250 caractères. ");
			estValide = false;
		}
		/*//tester date de début 
		if(! verifierDateDebut(indisponibilite)) 
		{
			sb.append(" le Véhicule est déjà enregistré indisponible pour la date de début choisi ,Essayez avec une autre Date ...  ");
		    estValide = false;
		}
		*/
		//tester la période de l'indisponibilité 
		if(!(verifierDateDebutFinIndisponibilite(indisponibilite.getDateDebut(),indisponibilite.getVehicule().getIdVehic())) || !(verifierDateDebutFinIndisponibilitePartie2(indisponibilite.getDateDebut(), indisponibilite.getDateFin(), indisponibilite.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule est déjà enregistré non-disponible pendant la période de l'indisponibilité que vous avez saisie . ");
			estValide =false ;
		}
		/*
		//tester la période de l'indisponibilité partie2
		if(!verifierDateDebutFinIndisponibilitePartie2(indisponibilite.getDateDebut(),indisponibilite.getDateFin(),indisponibilite.getVehicule().getIdVehic())) 
		{
			sb.append(" la période de l'indisponibilité à ajouter contient une autre indisponibilité pour le même véhicule. ");
			estValide = false ;
		}
		*/
		//tester véhicule 
		if(indisponibilite.getVehicule().getIdVehic() == 0 ) 
		{
			sb.append(" vous devez choisir un véhicule auquel l'indisponibilité va être associée. ");
			estValide = false ; 
		}
		//tester si la véhicule a des réservation pendant la période d'indisponibilité à ajouter 
		if(!(verifierPeriodeIndisponibiliteParReservation(indisponibilite.getDateDebut(),indisponibilite.getDateFin(),indisponibilite.getVehicule().getIdVehic())) || !(verifierPeriodeIndisponibiliteParReservationPartie2(indisponibilite.getDateDebut(), indisponibilite.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule a une ou des résrvations pendant la période de l'indisponibilité que vouz avez saisie.l'immobilisation du véhicule n'a pas été effectuée avec succès.  ");
			estValide = false;
		}
		//valider la zone de saisie du motif 
	    if(indisponibilite.getMotifIndisponibilite().trim().contains("<")||indisponibilite.getMotifIndisponibilite().trim().contains(">")||indisponibilite.getMotifIndisponibilite().trim().contains(",")||indisponibilite.getMotifIndisponibilite().trim().contains("?")||indisponibilite.getMotifIndisponibilite().trim().contains(";")||indisponibilite.getMotifIndisponibilite().trim().contains("/")||indisponibilite.getMotifIndisponibilite().trim().contains("!")||indisponibilite.getMotifIndisponibilite().trim().contains("§")||indisponibilite.getMotifIndisponibilite().trim().contains("*")||indisponibilite.getMotifIndisponibilite().trim().contains("%")||indisponibilite.getMotifIndisponibilite().trim().contains("$")||indisponibilite.getMotifIndisponibilite().trim().contains("£")||indisponibilite.getMotifIndisponibilite().trim().contains("¤")||indisponibilite.getMotifIndisponibilite().trim().contains("+")||indisponibilite.getMotifIndisponibilite().trim().contains("}")||indisponibilite.getMotifIndisponibilite().trim().contains("=")||indisponibilite.getMotifIndisponibilite().trim().contains("^")||indisponibilite.getMotifIndisponibilite().trim().contains(")")||indisponibilite.getMotifIndisponibilite().trim().contains("°")||indisponibilite.getMotifIndisponibilite().trim().contains("]")||indisponibilite.getMotifIndisponibilite().trim().contains("@")||indisponibilite.getMotifIndisponibilite().trim().contains("\\")||indisponibilite.getMotifIndisponibilite().trim().contains("`")||indisponibilite.getMotifIndisponibilite().trim().contains("|")||indisponibilite.getMotifIndisponibilite().trim().contains("(")||indisponibilite.getMotifIndisponibilite().trim().contains("[")||indisponibilite.getMotifIndisponibilite().trim().contains("{")||indisponibilite.getMotifIndisponibilite().trim().contains("\"")||indisponibilite.getMotifIndisponibilite().trim().contains("#")||indisponibilite.getMotifIndisponibilite().trim().contains("~")||indisponibilite.getMotifIndisponibilite().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie du Motif  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	estValide = false;
	    }
		
		if(!estValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception ;
		}
	
		return estValide ;
	}
	
	//méthode qui fait appel au DAL pour insérer une Indisponibilité 
	public void ajouterIndisponibilité (Indisponibilite indisponibilite) throws BusinessException
	{
		if(validerIndisponibilite(indisponibilite)) 
		{
			indisponibiliteDAO.insertIndisponibilie(indisponibilite);
		}
	}
	
	//méthode qui fait appel au dal pour récupérer toutes les indisponibilites dans la base de données 
	public List<Indisponibilite> selectToutesLesIndisponibilites () throws BusinessException
	{
		List<Indisponibilite> listeIndisponibilites = new ArrayList<Indisponibilite>();
		listeIndisponibilites = indisponibiliteDAO.selectToutesLesIndisponibilites();
		return listeIndisponibilites;
	}
	
	//méthode pour vérifier si la date de début d'une indisponibilité est déjà existe quand on ajoute une indisponibilité 
	public boolean verifierDateDebut (Indisponibilite indisponibilite) throws BusinessException 
	{
		boolean dateDebutEstValide = true ;
		Indisponibilite indisponibilite2 = new Indisponibilite();
		indisponibilite2 = indisponibiliteDAO.selectIndisponibiliteByDateDebutAndIdVehic(indisponibilite.getDateDebut(),indisponibilite.getVehicule().getIdVehic());
	
		if(indisponibilite2.getMotifIndisponibilite() != null) 
		{
             dateDebutEstValide = false ;	
            
             
		}
		
	    	return dateDebutEstValide;
	}
	
	//méthode qui fit appel au DAL pour modifier une indisponibilité 
	public void modifierIndisponibilite (Indisponibilite indisponibilite , int id ) throws BusinessException 
	{
		if (validerIndisponibiliteModifier(indisponibilite)) 
		{
			indisponibiliteDAO.modifierIndisponibilite(indisponibilite, id);
		}
	}

	//méthode qui fait appel au DAL pour récupérer une indisponibilité par son id 
	public Indisponibilite selectIndisponibiliteById(int id ) throws BusinessException
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		if (id !=0) 
		{
			indisponibilite = indisponibiliteDAO.selectIndisponibiliteById(id);
		}
		
		return indisponibilite;
	}
	
	//méthode pour vérifier si la date de début d'une indisponibilité est déjà existe quand on Modifie une indisponibilité
	public boolean verifierDateDebutModifier (Indisponibilite indisponibilite)throws BusinessException
	{
		boolean dateDebutEstValide = true ;
		Indisponibilite indisponibilite2 = new Indisponibilite();
		indisponibilite2 = indisponibiliteDAO.selectIndisponibiliteByDateDebutAndIdVehicModifier(indisponibilite.getDateDebut(),indisponibilite.getVehicule().getIdVehic(),indisponibilite.getIdIndis());
	
		if(indisponibilite2.getMotifIndisponibilite() != null) 
		{
             dateDebutEstValide = false ;	
            
             
		}
		
	    	return dateDebutEstValide;
		
	}
	
	//méthode pour valider l'objet indisponibilité avant de le envoyer vers le DAL quand on Modifie une indisponibilité

	public boolean validerIndisponibiliteModifier(Indisponibilite indisponibilite) throws BusinessException
	{
		StringBuilder sb = new StringBuilder();
		boolean estValide = true ; 
		/*Date dateDeJour = new Date();
		System.out.println(dateDeJour);
		dateDeJour.setHours(00);
		dateDeJour.setMinutes(00);
		dateDeJour.setSeconds(00);
		System.out.println(dateDeJour);
		System.out.println(indisponibilite.getDateDebut());
		System.out.println(indisponibilite.getDateFin());*/
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateDeJourString = sdf.format(new Date());
		Date dateDeJour = null ;
		try {
			dateDeJour = sdf.parse(dateDeJourString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//tester si l'objet est null 
		if (indisponibilite.getMotifIndisponibilite() == null ) 
		{
			sb.append(" aucune indisponibilité à insérer. ");
			estValide = false ; 
		}
		//tester la date de début d'indisponibilité 
		//long dd = indisponibilite.getDateDebut().getTime();
		//long ddj = dateDeJour.getTime();
		if(indisponibilite.getDateDebut().compareTo(dateDeJour) <0)
		{
			sb.append(" la date de début doit être égale ou postérieure à la date de Jour. ");
			estValide = false ;
		}
		//tester la date de fin d'indisponibilité 
		if(indisponibilite.getDateFin().compareTo(indisponibilite.getDateDebut())<0) 
		{
			sb.append(" la date de fin doit être égale ou postérieure à la date de début. ");
			estValide = false ; 
		}
		//tester le motif
		if(indisponibilite.getMotifIndisponibilite()== null || indisponibilite.getMotifIndisponibilite().trim().length()==0||indisponibilite.getMotifIndisponibilite().trim().length()>250) 
		{
			sb.append(" le motif doit être renseigné et ne dépasse pas 250 caractères. ");
			estValide = false;
		}
		/*
		//tester date de début 
		if(! verifierDateDebutModifier(indisponibilite)) 
		{
			sb.append(" le Véhicule est déjà enregistré indisponible pour la date de début choisi ,Essayez avec une autre Date ...  ");
		    estValide = false;
		}
		*/
		//tester la période de l'indisponibilité 
		if(!(verifierDateDebutFinIndisponibiliteModifier(indisponibilite.getDateDebut(),indisponibilite.getVehicule().getIdVehic(),indisponibilite.getIdIndis())) || !(verifierDateDebutFinIndisponibilitePartie2Modifier(indisponibilite.getDateDebut(), indisponibilite.getDateFin(), indisponibilite.getVehicule().getIdVehic(), indisponibilite.getIdIndis()))) 
		{
			sb.append("le véhicule est déjà enregistré non-ndisponible pendant la période de l'indisponibilité que vous avez choisie. ");
			estValide = false;
		}
		/*
		//tester la période de l'indisponibilité partie2 quand on modifie indisponibilité
		if(!verifierDateDebutFinIndisponibilitePartie2Modifier(indisponibilite.getDateDebut(),indisponibilite.getDateFin(),indisponibilite.getVehicule().getIdVehic(),indisponibilite.getIdIndis())) 
		{
			sb.append(" la période de l'indisponibilité choisi  contient une autre indisponibilité pour le même véhicule.");
			estValide = false ;
		}
        */
		//tester véhicule 
		if(indisponibilite.getVehicule().getIdVehic() == 0 ) 
		{
			sb.append(" vous devez choisir un véhicule auquel l'indisponibilité va être associée. ");
			estValide = false ; 
		}
		//tester si la véhicule a des réservation pendant la période d'indisponibilité à ajouter 
		if(!(verifierPeriodeIndisponibiliteParReservation(indisponibilite.getDateDebut(),indisponibilite.getDateFin(),indisponibilite.getVehicule().getIdVehic())) || !(verifierPeriodeIndisponibiliteParReservationPartie2(indisponibilite.getDateDebut(), indisponibilite.getVehicule().getIdVehic()))) 
		{
			sb.append(" le véhicule a une ou des résrvations pendant la période de l'indisponibilité que vouz avez saisie.l'immobilisation du véhicule n'a pas été effectuée avec succès.  ");
			estValide = false;
		}
		//valider la zone de saisie du motif 
	    if(indisponibilite.getMotifIndisponibilite().trim().contains("<")||indisponibilite.getMotifIndisponibilite().trim().contains(">")||indisponibilite.getMotifIndisponibilite().trim().contains(",")||indisponibilite.getMotifIndisponibilite().trim().contains("?")||indisponibilite.getMotifIndisponibilite().trim().contains(";")||indisponibilite.getMotifIndisponibilite().trim().contains("/")||indisponibilite.getMotifIndisponibilite().trim().contains("!")||indisponibilite.getMotifIndisponibilite().trim().contains("§")||indisponibilite.getMotifIndisponibilite().trim().contains("*")||indisponibilite.getMotifIndisponibilite().trim().contains("%")||indisponibilite.getMotifIndisponibilite().trim().contains("$")||indisponibilite.getMotifIndisponibilite().trim().contains("£")||indisponibilite.getMotifIndisponibilite().trim().contains("¤")||indisponibilite.getMotifIndisponibilite().trim().contains("+")||indisponibilite.getMotifIndisponibilite().trim().contains("}")||indisponibilite.getMotifIndisponibilite().trim().contains("=")||indisponibilite.getMotifIndisponibilite().trim().contains("^")||indisponibilite.getMotifIndisponibilite().trim().contains(")")||indisponibilite.getMotifIndisponibilite().trim().contains("°")||indisponibilite.getMotifIndisponibilite().trim().contains("]")||indisponibilite.getMotifIndisponibilite().trim().contains("@")||indisponibilite.getMotifIndisponibilite().trim().contains("\\")||indisponibilite.getMotifIndisponibilite().trim().contains("`")||indisponibilite.getMotifIndisponibilite().trim().contains("|")||indisponibilite.getMotifIndisponibilite().trim().contains("(")||indisponibilite.getMotifIndisponibilite().trim().contains("[")||indisponibilite.getMotifIndisponibilite().trim().contains("{")||indisponibilite.getMotifIndisponibilite().trim().contains("\"")||indisponibilite.getMotifIndisponibilite().trim().contains("#")||indisponibilite.getMotifIndisponibilite().trim().contains("~")||indisponibilite.getMotifIndisponibilite().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie du Motif  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	estValide = false;
	    }
		
		if(!estValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception ;
		}
	
		return estValide ;
	}
	
	//méthode qui fait appel au DAL pour supprimer une indisponibilité 
	public void supprimerIndisponibilite (int id) throws BusinessException
	{
		if(id != 0) 
		{
			indisponibiliteDAO.deleteIndisponibilite(id);
		}
	}
	
	// 1 méthode qui fait appel au DAL pour vérifier si il ya une autre disponibilite pour la même periode et pour le même véhicule 
	public boolean verifierDateDebutFinIndisponibilite(Date dateDebut ,int idVehic) throws BusinessException
	{
		boolean dateDebutFinEstValide = true ; 
		Indisponibilite indisponibilite = new Indisponibilite();
		indisponibilite = indisponibiliteDAO.selectIndisponibiliteByDateDebutFinAndIdVehic(dateDebut, idVehic);
		if(indisponibilite.getMotifIndisponibilite()!= null ) 
		{
			dateDebutFinEstValide = false ;
		}
		return dateDebutFinEstValide;
		
	}
	
	// 1 modifier -méthode qui fait appel au DAL pour vérifier si il ya une autre disponibilite pour la même periode et pour le même véhicule quand on la modifie
	public boolean verifierDateDebutFinIndisponibiliteModifier(Date dateDebut ,int idVehic,int idIndis) throws BusinessException
	{
		boolean dateDebutFinEstValide = true ;
		Indisponibilite  indisponibilite = new Indisponibilite();
		indisponibilite = indisponibiliteDAO.selectIndisponibiliteByDateDebutFinAndIdVehicModifier(dateDebut, idVehic, idIndis);
		if(indisponibilite.getMotifIndisponibilite() != null ) 
		{
			dateDebutFinEstValide = false ; 
		}
		return dateDebutFinEstValide;
	}
	
	// 2 méthode qui fait appel au Dal pour vérifier si la période de l'indisponibilité contient une autre indisponibilité pour le même véhicule 
	public boolean verifierDateDebutFinIndisponibilitePartie2 (Date dateDebut ,Date dateFin ,int idVehic)throws BusinessException 
	{
		boolean dateDebutFinEstValide = true ; 
		Indisponibilite indisponibilite = new Indisponibilite();
		indisponibilite = indisponibiliteDAO.selectIndisponibiliteByDateDebutFinAndIdVehicPartie2(dateDebut, dateFin, idVehic);
		if(indisponibilite.getMotifIndisponibilite()!= null ) 
		{
			dateDebutFinEstValide = false ;
		}
		return dateDebutFinEstValide;
		
	}
	
	//méthode qui fait appel au Dal pour vérifier si la période de l'indisponibilité contient une autre indisponibilité pour le même véhicule quand on modifie une indisponibilité

	public boolean verifierDateDebutFinIndisponibilitePartie2Modifier (Date dateDebut ,Date dateFin ,int idVehic,int idIndis) throws BusinessException
	{
		boolean dateDebutFinEstValide = true ; 
		Indisponibilite indisponibilite = new Indisponibilite();
		indisponibilite = indisponibiliteDAO.selectIndisponibiliteByDateDebutFinAndIdVehicPartie2Modifier(dateDebut, dateFin, idVehic,idIndis);
		if(indisponibilite.getMotifIndisponibilite()!= null ) 
		{
			dateDebutFinEstValide = false ;
		}
		return dateDebutFinEstValide;
		
	}
	
	//méthode qui fait appel au DAL pour vérifier si le véhicule à immobiliser a des réservation pendant la période d'indisponibilité à ajouter 
	public boolean verifierPeriodeIndisponibiliteParReservation (Date dateDebut , Date dateFin ,int idVehic ) throws BusinessException
	{
		boolean indisponibiliteEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = indisponibiliteDAO.selectReservationByDateDebutFinIndisponibiliteAndIdvehic(dateDebut, dateFin, idVehic);
		if(reservation.getMotif() != null) 
		{
			indisponibiliteEstValide = false;
		}
		return indisponibiliteEstValide;	
	}
	
	//méthode qui fait appel au DAL pour vérifier si le véhicule à immobiliser a des réservation pendant la période d'indisponibilité à ajouter Partie2
	public boolean verifierPeriodeIndisponibiliteParReservationPartie2(Date dateDebut ,int idVehic)throws BusinessException
	{
		boolean indisponibiliteEstValide = true ; 
		Reservation reservation = new Reservation();
		reservation = indisponibiliteDAO.selectReservationByDateDebutFinIndisponibiliteAndIdvehicPartie2(dateDebut, idVehic);
		if(reservation.getMotif() != null) 
		{
			indisponibiliteEstValide = false;
		}
		return indisponibiliteEstValide;	
	}

	
}

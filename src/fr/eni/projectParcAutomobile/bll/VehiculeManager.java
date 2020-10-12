package fr.eni.projectParcAutomobile.bll;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.dal.VehiculeDAO;
import fr.eni.projectParcAutomobile.exception.BusinessException;


public class VehiculeManager 
{

	private VehiculeDAO vehiculeDAO ;

	public VehiculeManager() {
		this.vehiculeDAO = DAOFactory.getVehiculeDAO();
	} 
	
	//méthode qui fait appel au Dal pour insérer un véhicule à la base de données 
	public void ajouterVehicule (Vehicule vehicule) throws BusinessException 
	{
		if(validerVehicule(vehicule)) 
		{
			vehiculeDAO.insertVehicule(vehicule);
		}
	}
	
	// méthode pour verifier si l'immatriculation est unique quand on ajoute un véhicule 
	public boolean verifierImmatriculationUniqueAjouter(String immatriculation) throws BusinessException 
	{
		boolean immatUniqueAjouter = true ; 
		Vehicule vehicule = new Vehicule();
		vehicule = vehiculeDAO.selectVehiculeByImmat(immatriculation);
		//tester si l'immatriculation est unique 
		if(vehicule.getDesignation()!= null) 
		{
			immatUniqueAjouter = false ; 
		}
		return immatUniqueAjouter;
		
	}
	
	//méthode pour vérifier si l'immatriculation est unique quand on modifie un véhicule 
	public boolean verifierImmatriculationUniqueModifier (String immatriculation , int id) throws BusinessException 
	{
		boolean immatUniqueAjouter = true ; 
		Vehicule vehicule = new Vehicule();
		vehicule = vehiculeDAO.selectVehiculeByImmatModifier(immatriculation, id);
		
		//tester si l'immatriculation est unique 
		if(vehicule.getDesignation()!= null) 
		{
			immatUniqueAjouter = false ; 
		}
		return immatUniqueAjouter;
	}
	
	// méthode pour verifier si l'objet de véhicule est validé pour l'insérer 
	public boolean validerVehicule (Vehicule vehicule) throws BusinessException 
	{
		boolean vehiculeEstValide = true ;
		StringBuilder sb = new StringBuilder();
		
				
		if(vehicule.getImmatriculation() == null ) 
		{
			sb.append(" aucun véhicule à insérer. ");
			vehiculeEstValide = false;
		}
		//tester la designation 
		if(vehicule.getDesignation()==null ||vehicule.getDesignation().trim().length()==0||vehicule.getDesignation().trim().length()>250) 
		{
			sb.append(" la désignation doit être renseignée et ne dépasse pas 250 caractères. ");
			vehiculeEstValide = false ; 
		}
		//tester l'immatriculation 
		if(vehicule.getImmatriculation()==null ||vehicule.getImmatriculation().trim().length()==0||vehicule.getImmatriculation().trim().length()>11) 
		{
			sb.append(" l'mmatriculation doit être renseignée et ne dépasse pas 11 caractères. ");
			vehiculeEstValide = false ; 
		}
		//teste la date d'achat 
		if(vehicule.getDateAchat() == null) 
		{
			sb.append(" la date d'achat doit être choisie. ");
			vehiculeEstValide = false ;
		}
		//vérifier la date d'achat 
		//récupérer la date de jour au format "yyyy-MM-dd 00:00:00"
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String dateDeJourString = sdf.format(new Date());
		Date dateDeJour = null ;
		try {
			dateDeJour = sdf.parse(dateDeJourString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(vehicule.getDateAchat().compareTo(dateDeJour)>0 || vehicule.getDateAchat().compareTo(dateDeJour)==0) 
		{
			sb.append(" la Date d'achat diot être inférieure à la date de jour.  ");
			vehiculeEstValide = false ; 
		}
		//tester le campus du véhicule 
		if(vehicule.getCampus()==null)
		{
			sb.append(" vous devez choisir un campus auquel le véhicule va être associé. ");
			vehiculeEstValide = false ;
		}
		// tester si l'immatriculation est unique 
		if(!verifierImmatriculationUniqueAjouter(vehicule.getImmatriculation())) 
		{
			sb.append(" l'immatriculation est déjà utilisée et doit être unique. ");
			vehiculeEstValide = false ; 
		}
		//valider la zone de saisie de Désignation 
	    if(vehicule.getDesignation().trim().contains("<")||vehicule.getDesignation().trim().contains(">")||vehicule.getDesignation().trim().contains(",")||vehicule.getDesignation().trim().contains("?")||vehicule.getDesignation().trim().contains(";")||vehicule.getDesignation().trim().contains("/")||vehicule.getDesignation().trim().contains("!")||vehicule.getDesignation().trim().contains("§")||vehicule.getDesignation().trim().contains("*")||vehicule.getDesignation().trim().contains("%")||vehicule.getDesignation().trim().contains("$")||vehicule.getDesignation().trim().contains("£")||vehicule.getDesignation().trim().contains("¤")||vehicule.getDesignation().trim().contains("+")||vehicule.getDesignation().trim().contains("}")||vehicule.getDesignation().trim().contains("=")||vehicule.getDesignation().trim().contains("^")||vehicule.getDesignation().trim().contains(")")||vehicule.getDesignation().trim().contains("°")||vehicule.getDesignation().trim().contains("]")||vehicule.getDesignation().trim().contains("@")||vehicule.getDesignation().trim().contains("\\")||vehicule.getDesignation().trim().contains("`")||vehicule.getDesignation().trim().contains("|")||vehicule.getDesignation().trim().contains("(")||vehicule.getDesignation().trim().contains("[")||vehicule.getDesignation().trim().contains("{")||vehicule.getDesignation().trim().contains("\"")||vehicule.getDesignation().trim().contains("#")||vehicule.getDesignation().trim().contains("~")||vehicule.getDesignation().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de Désignation  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	vehiculeEstValide = false;
	    }
	  //valider la zone de saisie d'immatriculation  
	    if(vehicule.getImmatriculation().trim().contains("_")||vehicule.getImmatriculation().trim().contains("<")||vehicule.getImmatriculation().trim().contains(">")||vehicule.getImmatriculation().trim().contains(",")||vehicule.getImmatriculation().trim().contains("?")||vehicule.getImmatriculation().trim().contains(";")||vehicule.getImmatriculation().trim().contains("/")||vehicule.getImmatriculation().contains("!")||vehicule.getImmatriculation().trim().contains("§")||vehicule.getImmatriculation().trim().contains("*")||vehicule.getImmatriculation().trim().contains("%")||vehicule.getImmatriculation().trim().contains("$")||vehicule.getImmatriculation().trim().contains("£")||vehicule.getImmatriculation().trim().contains("¤")||vehicule.getImmatriculation().trim().contains("+")||vehicule.getImmatriculation().trim().contains("}")||vehicule.getImmatriculation().trim().contains("=")||vehicule.getImmatriculation().trim().contains("^")||vehicule.getImmatriculation().trim().contains(")")||vehicule.getImmatriculation().trim().contains("°")||vehicule.getImmatriculation().trim().contains("]")||vehicule.getImmatriculation().trim().contains("@")||vehicule.getImmatriculation().trim().contains("\\")||vehicule.getImmatriculation().trim().contains("`")||vehicule.getImmatriculation().trim().contains("|")||vehicule.getImmatriculation().trim().contains("(")||vehicule.getImmatriculation().trim().contains("[")||vehicule.getImmatriculation().trim().contains("{")||vehicule.getImmatriculation().trim().contains("\"")||vehicule.getImmatriculation().trim().contains("#")||vehicule.getImmatriculation().trim().contains("~")||vehicule.getImmatriculation().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie d'immatriculation  n'accepte que des lettres,des nombres Et tiret. ");
	    	vehiculeEstValide = false;
	    }
		// lever l'exception si l'objet du véhicule n'est pas valide 
		if(! vehiculeEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return vehiculeEstValide;
	}
	
	//méthode pour vérifier si l'objet du véhicule est valide pour podifier 
	public boolean validerVehiculeModifier (Vehicule vehicule) throws BusinessException 
	{
		boolean vehiculeEstValide = true ;
		StringBuilder sb = new StringBuilder();
		
		if(vehicule.getImmatriculation() == null ) 
		{
			sb.append(" aucun véhicule à insérer. ");
			vehiculeEstValide = false;
		}
		//tester la designation 
		if(vehicule.getDesignation()==null ||vehicule.getDesignation().trim().length()==0||vehicule.getDesignation().trim().length()>250) 
		{
			sb.append(" la désignation doit être renseignée et ne dépasse pas 250 caractères. ");
			vehiculeEstValide = false ; 
		}
		//tester l'immatriculation 
		if(vehicule.getImmatriculation()==null ||vehicule.getImmatriculation().trim().length()==0||vehicule.getImmatriculation().trim().length()>11) 
		{
			sb.append(" l'mmatriculation doit être renseignée et ne dépasse pas 11 caractères. ");
			vehiculeEstValide = false ; 
		}
		//teste la date d'achat 
		if(vehicule.getDateAchat() == null) 
		{
			sb.append(" la date d'achat doit être choisie. ");
			vehiculeEstValide = false ;
		}
		//vérifier la date d'achat 
		//récupérer la date de jour au format "yyyy-MM-dd 00:00:00"
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				String dateDeJourString = sdf.format(new Date());
				Date dateDeJour = null ;
				try {
					dateDeJour = sdf.parse(dateDeJourString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
		if(vehicule.getDateAchat().compareTo(dateDeJour)>0 || vehicule.getDateAchat().compareTo(dateDeJour)==0) 
		{
					sb.append(" la Date d'achat diot être inférieure à la date de jour.  ");
					vehiculeEstValide = false ; 
		}
		//tester le campus du véhicule 
		if(vehicule.getCampus()==null)
		{
			sb.append(" vous devez choisir un campus auquel le véhicule va être associé. ");
			vehiculeEstValide = false ;
		}
		// tester si l'immatriculation est unique 
		if(!verifierImmatriculationUniqueModifier(vehicule.getImmatriculation(),vehicule.getIdVehic())) 
		{
			sb.append(" l'immatriculation est déjà utilisée et doit être unique. ");
			vehiculeEstValide = false ; 
		}
		//valider la zone de saisie de Désignation 
	    if(vehicule.getDesignation().trim().contains("<")||vehicule.getDesignation().trim().contains(">")||vehicule.getDesignation().trim().contains(",")||vehicule.getDesignation().trim().contains("?")||vehicule.getDesignation().trim().contains(";")||vehicule.getDesignation().trim().contains("/")||vehicule.getDesignation().trim().contains("!")||vehicule.getDesignation().trim().contains("§")||vehicule.getDesignation().trim().contains("*")||vehicule.getDesignation().trim().contains("%")||vehicule.getDesignation().trim().contains("$")||vehicule.getDesignation().trim().contains("£")||vehicule.getDesignation().trim().contains("¤")||vehicule.getDesignation().trim().contains("+")||vehicule.getDesignation().trim().contains("}")||vehicule.getDesignation().trim().contains("=")||vehicule.getDesignation().trim().contains("^")||vehicule.getDesignation().trim().contains(")")||vehicule.getDesignation().trim().contains("°")||vehicule.getDesignation().trim().contains("]")||vehicule.getDesignation().trim().contains("@")||vehicule.getDesignation().trim().contains("\\")||vehicule.getDesignation().trim().contains("`")||vehicule.getDesignation().trim().contains("|")||vehicule.getDesignation().trim().contains("(")||vehicule.getDesignation().trim().contains("[")||vehicule.getDesignation().trim().contains("{")||vehicule.getDesignation().trim().contains("\"")||vehicule.getDesignation().trim().contains("#")||vehicule.getDesignation().trim().contains("~")||vehicule.getDesignation().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de Désignation  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	vehiculeEstValide = false;
	    }
	  //valider la zone de saisie d'immatriculation  
	    if(vehicule.getImmatriculation().trim().contains("_")||vehicule.getImmatriculation().trim().contains("<")||vehicule.getImmatriculation().trim().contains(">")||vehicule.getImmatriculation().trim().contains(",")||vehicule.getImmatriculation().trim().contains("?")||vehicule.getImmatriculation().trim().contains(";")||vehicule.getImmatriculation().trim().contains("/")||vehicule.getImmatriculation().contains("!")||vehicule.getImmatriculation().trim().contains("§")||vehicule.getImmatriculation().trim().contains("*")||vehicule.getImmatriculation().trim().contains("%")||vehicule.getImmatriculation().trim().contains("$")||vehicule.getImmatriculation().trim().contains("£")||vehicule.getImmatriculation().trim().contains("¤")||vehicule.getImmatriculation().trim().contains("+")||vehicule.getImmatriculation().trim().contains("}")||vehicule.getImmatriculation().trim().contains("=")||vehicule.getImmatriculation().trim().contains("^")||vehicule.getImmatriculation().trim().contains(")")||vehicule.getImmatriculation().trim().contains("°")||vehicule.getImmatriculation().trim().contains("]")||vehicule.getImmatriculation().trim().contains("@")||vehicule.getImmatriculation().trim().contains("\\")||vehicule.getImmatriculation().trim().contains("`")||vehicule.getImmatriculation().trim().contains("|")||vehicule.getImmatriculation().trim().contains("(")||vehicule.getImmatriculation().trim().contains("[")||vehicule.getImmatriculation().trim().contains("{")||vehicule.getImmatriculation().trim().contains("\"")||vehicule.getImmatriculation().trim().contains("#")||vehicule.getImmatriculation().trim().contains("~")||vehicule.getImmatriculation().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie d'immatriculation  n'accepte que des lettres,des nombres Et tiret. ");
	    	vehiculeEstValide = false;
	    }
		// lever l'exception si l'objet du véhicule n'est pas valide 
		if(! vehiculeEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return vehiculeEstValide;
	}
	//création de la méthode qui appelle la DAL pour récupérer tous les véhicules stockés dans la base de données 
	public List<Vehicule> selectTousLesVehicules () throws BusinessException 
	{
		List<Vehicule> listeVehicules = new ArrayList<Vehicule>();
		listeVehicules = vehiculeDAO.selectTousLesVehicules();
		return listeVehicules;
	}
	//création de la méthoded qui fait appem au DAL pour récupérer un véhicule par son id 
	public Vehicule selectVehiculeById (int id ) throws BusinessException
	{
		Vehicule vehicule = new Vehicule();
		if(id != 0) 
		{
			vehicule = vehiculeDAO.selectVehiculeById(id);
		}
		return vehicule;
	}
	
	//méthode qui fait appel au DAL pour modifier un véhicule 
	public void modifierVehicule (int id , Vehicule vehicule) throws BusinessException 
	{
		//tester la validation de l'objet véhicule 
		if(validerVehiculeModifier(vehicule)) 
		{
			vehiculeDAO.modifierVehicule(id, vehicule);
		}
	}
	
	//création de la méthode qui fait appel au DAL pour supprimer un véhicule 
	public void supprimerVehicule (int id)throws BusinessException
	{
		if(id != 0)
		{
			vehiculeDAO.supprimerVehicule(id);
		}
	}
}

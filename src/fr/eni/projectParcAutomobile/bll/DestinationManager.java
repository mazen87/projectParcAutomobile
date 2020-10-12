package fr.eni.projectParcAutomobile.bll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.dal.ConnectionProvider;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.dal.DestinationDAO;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class DestinationManager 
{

	private DestinationDAO destinationDAO;

	public DestinationManager()
	{
	   this.destinationDAO = DAOFactory.getDestinationDAO();
	}
	
	
	//création de la méthode qui fait appel au DAl pour insérer une destination 
	public void ajouterDestination (Destination destination) throws BusinessException
	{
	    if(validerDestination(destination)) 
	    {
	    	destinationDAO.insertDestination(destination);
	    }
	}
	//création de la méthode qui fait appel au DAL pour verifier si la libillé de la destination est unique 
	public boolean verifierLibelleUnique(String libelle) throws BusinessException 
	{
		boolean libelleEstUnique = true ; 
		Destination destination = new Destination();
		destination = destinationDAO.selectDestinationByLibelle(libelle);
		if(destination.getLibelle() !=null)
		{
			libelleEstUnique = false;
		}
		return libelleEstUnique;
	}  
	
	//création de la méthode qui fait appel au DAL pour verifier si la libillé de la destination est unique quand on modifie la destination 
	
	public boolean verifierLibelleUniqueModifier(String libelle , int id) throws BusinessException 
	{
		boolean libelleEstUnique = true ; 
		Destination destination = new Destination();
		destination = destinationDAO.selectDestinationByLibelleAndId(libelle, id);
		if(destination.getLibelle() !=null)
		{
			libelleEstUnique = false;
		}
		return libelleEstUnique;
	}  
	//création de la méthode qui fait appel au DAL pour vérifier si le codeDes est unique 
	public boolean verifierCodeDesUnique (String codeDes)throws BusinessException
	{
		boolean codeDesEstUnique = true ; 
		Destination destination = new Destination();
		if(codeDes != null)
		{
		destination = destinationDAO.selectDestinationByCodeDes(codeDes);
		if(destination.getCodeDes()!=null)
		{
			codeDesEstUnique = false; 
		}
		}
		return codeDesEstUnique;
	}
	//création de la méthode qui fait appel au DAL pour vérifier si le codeDes est unique quand on modifie la destination
	public boolean verifierCodeDesUniqueModifier (String codeDes , int id)throws BusinessException
	{
		boolean codeDesEstUnique = true ; 
		Destination destination = new Destination();
		if(codeDes != null)
		{
		destination = destinationDAO.selectDestinationByCodeDesAndId(codeDes, id);
		if(destination.getCodeDes()!=null)
		{
			codeDesEstUnique = false; 
		}
		}
		return codeDesEstUnique;
	}
	
	//création de la méthode pour valider la destination 
	public boolean validerDestination (Destination destination)throws BusinessException 
	{
		StringBuilder sb = new StringBuilder();
		boolean destinationEstValide = true ; 
		if(destination == null ) 
		{
			sb.append(" aucune destination à valider ");
			destinationEstValide = false;
		}
		//valider la libellé
		if(destination.getLibelle()== null || destination.getLibelle().trim().length()==0 || destination.getLibelle().trim().length()>50) 
		{
			sb.append(" le libellé doit être renseigné et ne dépasse pas 50 caractères. ");
			destinationEstValide = false ; 
		}
		//valider le code destination 
		if(destination.getCodeDes() == null|| destination.getCodeDes().trim().length()==0 ||destination.getCodeDes().trim().length()>50)
		{
			sb.append(" le code Destination doit être renseigné et ne depasse pas 50 caractères. ");
			destinationEstValide = false ; 
		}
		// valider si la libellé est unique 
		if(!verifierLibelleUnique(destination.getLibelle())) 
		{
			sb.append(" le libellé est déja utilisé et doit être unique. ");
			destinationEstValide = false;
		}
		//valider si le code destination est unique 
		if(!verifierCodeDesUnique(destination.getCodeDes())) 
		{
			sb.append(" le code destination est déjà utilisé et doit être unique. ");
			destinationEstValide = false ; 
		}
		//valider la zone de saisie du libellé
	    if(destination.getLibelle().trim().contains("<")||destination.getLibelle().trim().contains(">")||destination.getLibelle().trim().contains(",")||destination.getLibelle().trim().contains("?")||destination.getLibelle().trim().contains(";")||destination.getLibelle().trim().contains("/")||destination.getLibelle().trim().contains("!")||destination.getLibelle().trim().contains("§")||destination.getLibelle().trim().contains("*")||destination.getLibelle().trim().contains("%")||destination.getLibelle().trim().contains("$")||destination.getLibelle().trim().contains("£")||destination.getLibelle().trim().contains("¤")||destination.getLibelle().trim().contains("+")||destination.getLibelle().trim().contains("}")||destination.getLibelle().trim().contains("=")||destination.getLibelle().trim().contains("^")||destination.getLibelle().trim().contains(")")||destination.getLibelle().trim().contains("°")||destination.getLibelle().trim().contains("]")||destination.getLibelle().trim().contains("@")||destination.getLibelle().trim().contains("\\")||destination.getLibelle().trim().contains("`")||destination.getLibelle().trim().contains("|")||destination.getLibelle().trim().contains("(")||destination.getLibelle().trim().contains("[")||destination.getLibelle().trim().contains("{")||destination.getLibelle().trim().contains("\"")||destination.getLibelle().trim().contains("#")||destination.getLibelle().trim().contains("~")||destination.getLibelle().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de libellé  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	destinationEstValide = false;
	    }
	    //valider la zone de saisie de code Destination
	    if(destination.getCodeDes().trim().contains("<")||destination.getCodeDes().trim().contains(">")||destination.getCodeDes().trim().contains(",")||destination.getCodeDes().trim().contains("?")||destination.getCodeDes().trim().contains(";")||destination.getCodeDes().trim().contains("/")||destination.getCodeDes().trim().contains("!")||destination.getCodeDes().trim().contains("§")||destination.getCodeDes().trim().contains("*")||destination.getCodeDes().trim().contains("%")||destination.getCodeDes().trim().contains("$")||destination.getCodeDes().trim().contains("£")||destination.getCodeDes().trim().contains("¤")||destination.getCodeDes().trim().contains("+")||destination.getCodeDes().trim().contains("}")||destination.getCodeDes().trim().contains("=")||destination.getCodeDes().trim().contains("^")||destination.getCodeDes().trim().contains(")")||destination.getCodeDes().trim().contains("°")||destination.getCodeDes().trim().contains("]")||destination.getCodeDes().trim().contains("@")||destination.getCodeDes().trim().contains("\\")||destination.getCodeDes().trim().contains("`")||destination.getCodeDes().trim().contains("|")||destination.getCodeDes().trim().contains("(")||destination.getCodeDes().trim().contains("[")||destination.getCodeDes().trim().contains("{")||destination.getCodeDes().trim().contains("\"")||destination.getCodeDes().trim().contains("#")||destination.getCodeDes().trim().contains("~")||destination.getCodeDes().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de code Destination n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	destinationEstValide = false;
	    } 

		
		if(!destinationEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return destinationEstValide;
	}
	
	//création de la méthode pour valider la destination quand on modifie la destination 
	public boolean validerDestinationModifier (Destination destination)throws BusinessException 
	{
		StringBuilder sb = new StringBuilder();
		boolean destinationEstValide = true ; 
		if(destination == null ) 
		{
			sb.append(" aucune destination à valider ");
			destinationEstValide = false;
		}
		//valider la libellé
		if(destination.getLibelle()== null || destination.getLibelle().trim().length()==0 || destination.getLibelle().trim().length()>50) 
		{
			sb.append(" le libellé doit être renseigné et ne dépasse pas 50 caractères. ");
			destinationEstValide = false ; 
		}
		//valider le code destination 
		if(destination.getCodeDes() == null|| destination.getCodeDes().trim().length()==0 ||destination.getCodeDes().trim().length()>50)
		{
			sb.append(" le code Destination doit être renseigné et ne depasse pas 50 caractères. ");
			destinationEstValide = false ; 
		}
		// valider si la libellé est unique 
		if(!verifierLibelleUniqueModifier(destination.getLibelle(),destination.getIdDes())) 
		{
			sb.append(" le libellé est déja utilisé et doit être unique. ");
			destinationEstValide = false;
		}
		//valider si le code destination est unique 
		if(!verifierCodeDesUniqueModifier(destination.getCodeDes(),destination.getIdDes())) 
		{
			sb.append(" le code destination est déjà utilisé et doit être unique. ");
			destinationEstValide = false ; 
		}
		//valider la zone de saisie de libelle
	    if(destination.getLibelle().trim().contains("<")||destination.getLibelle().trim().contains(">")||destination.getLibelle().trim().contains(",")||destination.getLibelle().trim().contains("?")||destination.getLibelle().trim().contains(";")||destination.getLibelle().trim().contains("/")||destination.getLibelle().trim().contains("!")||destination.getLibelle().trim().contains("§")||destination.getLibelle().trim().contains("*")||destination.getLibelle().trim().contains("%")||destination.getLibelle().trim().contains("$")||destination.getLibelle().trim().contains("£")||destination.getLibelle().trim().contains("¤")||destination.getLibelle().trim().contains("+")||destination.getLibelle().trim().contains("}")||destination.getLibelle().trim().contains("=")||destination.getLibelle().trim().contains("^")||destination.getLibelle().trim().contains(")")||destination.getLibelle().trim().contains("°")||destination.getLibelle().trim().contains("]")||destination.getLibelle().trim().contains("@")||destination.getLibelle().trim().contains("\\")||destination.getLibelle().trim().contains("`")||destination.getLibelle().trim().contains("|")||destination.getLibelle().trim().contains("(")||destination.getLibelle().trim().contains("[")||destination.getLibelle().trim().contains("{")||destination.getLibelle().trim().contains("\"")||destination.getLibelle().trim().contains("#")||destination.getLibelle().trim().contains("~")||destination.getLibelle().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de libellé  n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	destinationEstValide = false;
	    }
	  //valider la zone de saisie de code Destination
	    if(destination.getCodeDes().trim().contains("<")||destination.getCodeDes().trim().contains(">")||destination.getCodeDes().trim().contains(",")||destination.getCodeDes().trim().contains("?")||destination.getCodeDes().trim().contains(";")||destination.getCodeDes().trim().contains("/")||destination.getCodeDes().trim().contains("!")||destination.getCodeDes().trim().contains("§")||destination.getCodeDes().trim().contains("*")||destination.getCodeDes().trim().contains("%")||destination.getCodeDes().trim().contains("$")||destination.getCodeDes().trim().contains("£")||destination.getCodeDes().trim().contains("¤")||destination.getCodeDes().trim().contains("+")||destination.getCodeDes().trim().contains("}")||destination.getCodeDes().trim().contains("=")||destination.getCodeDes().trim().contains("^")||destination.getCodeDes().trim().contains(")")||destination.getCodeDes().trim().contains("°")||destination.getCodeDes().trim().contains("]")||destination.getCodeDes().trim().contains("@")||destination.getCodeDes().trim().contains("\\")||destination.getCodeDes().trim().contains("`")||destination.getCodeDes().trim().contains("|")||destination.getCodeDes().trim().contains("(")||destination.getCodeDes().trim().contains("[")||destination.getCodeDes().trim().contains("{")||destination.getCodeDes().trim().contains("\"")||destination.getCodeDes().trim().contains("#")||destination.getCodeDes().trim().contains("~")||destination.getCodeDes().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie de code Destination n'accepte que des lettres,des nombres , tiret Et tiret bas. ");
	    	destinationEstValide = false;
	    } 
		if(!destinationEstValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
		return destinationEstValide;
	}
	//création de la méthode qui appelle la DAL pour récupérer toutes les destinations stockées dans la base de données 
	public List<Destination> selectToutesLesDestinations () throws BusinessException
	{
		List<Destination> listeDestinations = new ArrayList<Destination>();
		listeDestinations = destinationDAO.selectlisteDestinations();
		
		return listeDestinations;
	}
	
	// création de la méthode qui fait appel au DAL pour modifier une destination 
	public void modifierDestination (int id , Destination destination)throws BusinessException
	{
		if(validerDestinationModifier(destination)) 
		{
			destinationDAO.modifierDestination(id, destination);
		}
	}
	
	//création de la méthode qui fait apel au DAL pour récupérer une destination par son id 
	public Destination selectDestinationById(int id) throws BusinessException
	{
		Destination destination = new Destination();
		destination = destinationDAO.selectDestinationById(id);
		return destination;
	}
	//création de la méthode qui fait appel au DAL pour supprimer une destination 
	public void supprimerDestination (int id)throws BusinessException
	{
		if(id != 0) 
		{
	         destinationDAO.supprimerDestination(id);
		}
	}
}

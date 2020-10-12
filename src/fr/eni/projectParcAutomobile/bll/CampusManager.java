package fr.eni.projectParcAutomobile.bll;

import java.util.ArrayList;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.dal.CampusDAO;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class CampusManager 
{


	private CampusDAO campusDAO ;

	public CampusManager() 
	{
	
		this.campusDAO = DAOFactory.getCampusDAO();
	} 
	
	// méthode permettant d'appeler la méthode insertCampus pour insérée l'objet campus dans la bdd 
	
	public void ajouterCampus (Campus campus) throws BusinessException
	{
		Campus campus2 = new Campus();
	
		campus2 = selectCampusByLibelle(campus.getLibelle());
		//System.out.println(campus2.toString());
		if(campus2.getLibelle() != null ) 
		{
			BusinessException exception = new BusinessException("le libellé est déjà utilisé et doit être unique ");
			throw exception;
		}
		
		else if (! validerCampus(campus)) 
		{
			BusinessException exception = new BusinessException("le libellé doit être renseigné et ne dépasse pas 150 caractères ");
			throw exception;
		}
		else if(campus.getLibelle().trim().contains("<")||campus.getLibelle().trim().contains(">")||campus.getLibelle().trim().contains(",")||campus.getLibelle().trim().contains("?")||campus.getLibelle().trim().contains(";")||campus.getLibelle().trim().contains("/")||campus.getLibelle().trim().contains("!")||campus.getLibelle().trim().contains("§")||campus.getLibelle().trim().contains("*")||campus.getLibelle().trim().contains("%")||campus.getLibelle().trim().contains("$")||campus.getLibelle().trim().contains("£")||campus.getLibelle().trim().contains("¤")||campus.getLibelle().trim().contains("+")||campus.getLibelle().trim().contains("}")||campus.getLibelle().trim().contains("=")||campus.getLibelle().trim().contains("^")||campus.getLibelle().trim().contains(")")||campus.getLibelle().trim().contains("°")||campus.getLibelle().trim().contains("]")||campus.getLibelle().trim().contains("@")||campus.getLibelle().trim().contains("\\")||campus.getLibelle().trim().contains("`")||campus.getLibelle().trim().contains("|")||campus.getLibelle().trim().contains("(")||campus.getLibelle().trim().contains("[")||campus.getLibelle().trim().contains("{")||campus.getLibelle().trim().contains("\"")||campus.getLibelle().trim().contains("#")||campus.getLibelle().trim().contains("~")||campus.getLibelle().trim().contains("&"))
		{
			BusinessException exception = new BusinessException(" la zone de saisie n'accepte que des lettres,des nombres , tiret Et tiret bas");
			throw exception;
		} 
		else 
		{
			campusDAO.insertCampus(campus);
		}
	}
	
	// méthode qui permet de valider l'objet campus qui vient de l'IHM avans de le envoyer vers la bdd
	public boolean validerCampus (Campus campus) 
	{
		boolean estValide = true ; 
		if(campus.getLibelle()== null || campus.getLibelle().trim().length()== 0 ||campus.getLibelle().trim().length() >150)
		{
			estValide = false ; 
		}
		return estValide ; 
	}
	
	// methode qui recherche un objet campus par sa libelle pour vérifier si la libillé saisie par l'utilisateur déjà existe dans la bdd ou pas 
	public Campus selectCampusByLibelle (String libelle) throws BusinessException
	{
		Campus campus = new Campus();
		campus = campusDAO.selectCampusByLibelle(libelle);
		//if(campus == null ) 
		//{
			//BusinessException exception = new BusinessException("Aucun Campus existe dans la base de données avec cette libellé ");
			//throw exception;
		//}
		return campus;
	}
	
	public List<Campus> selectListeCampus () throws BusinessException
	{
		List<Campus> listeCampus = new ArrayList<Campus>();
		listeCampus = campusDAO.selectTousLesCampus();
		if(listeCampus.size() == 0) 
		{
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucun Campus existe dans la base de données ");
			throw exception;
		}
		return listeCampus;
	}
	
	public void  modifierCampus (String libelle,Campus campus) throws BusinessException
	{
		Campus campus1 = new Campus();
		Campus campus2 = new Campus(libelle);
		if(libelle != null) 
		{
		   campus1 = campusDAO.selectCampusByLibelleAndId(libelle, campus.getIdCampus());
		   if(campus1.getLibelle() != null) 
		   {
			   BusinessException exception = new BusinessException("le libellé est déjà utilisé et doit être unique ");
				throw exception;
		   }
		   else if (! validerCampus(campus2)) 
			{
				BusinessException exception = new BusinessException("le libellé doit être renseigné et ne dépasse pas 150 caractères ");
				throw exception;
			}
		   else if(libelle.trim().contains("<")||libelle.trim().contains(">")||libelle.trim().contains(",")||libelle.trim().contains("?")||libelle.trim().contains(";")||libelle.trim().contains("/")||libelle.trim().contains("!")||libelle.trim().contains("§")||libelle.trim().contains("*")||libelle.trim().contains("%")||libelle.trim().contains("$")||libelle.trim().contains("£")||libelle.trim().contains("¤")||libelle.trim().contains("+")||libelle.trim().contains("}")||libelle.trim().contains("=")||libelle.trim().contains("^")||libelle.trim().contains(")")||libelle.trim().contains("°")||libelle.trim().contains("]")||libelle.trim().contains("@")||libelle.trim().contains("\\")||libelle.trim().contains("`")||libelle.trim().contains("|")||libelle.trim().contains("(")||libelle.trim().contains("[")||libelle.trim().contains("{")||libelle.trim().contains("\"")||libelle.trim().contains("#")||libelle.trim().contains("~")||libelle.trim().contains("&"))
			{
				BusinessException exception = new BusinessException(" la zone de saisie n'accepte que des lettres,des nombres , tiret Et tiret bas");
				throw exception;
			} 
		   else 
		   {
			   campusDAO.modifierCampus(libelle,campus);
		   }
		}
	}
	
	// méthode qui fait appelle à la méthode permettant de supprimer un Campus 
	public void supprimerCampus (int idCampus) throws BusinessException 
	{
		if(idCampus != 0) 
		{
			campusDAO.supprimerCampus(idCampus);
		}
		

		
	}
	
	
	
	
	
	
	
	
	
	
}

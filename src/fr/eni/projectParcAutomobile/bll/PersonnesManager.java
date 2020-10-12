package fr.eni.projectParcAutomobile.bll;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.dal.DAOFactory;
import fr.eni.projectParcAutomobile.dal.PersonneDAO;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class PersonnesManager
{
	private PersonneDAO personneDAO;
	

	public PersonnesManager() {
		this.personneDAO = DAOFactory.getPersonneDAO();
	}

	// création de la méthode qui fait appele à la méthode permettante d'insérer une personne à la BDD
	public void ajouterUnePersonne (Personne personne) throws BusinessException 
	{
	
		if(validerPersonnePourAjouter(personne))
		{
			personneDAO.insertPersonne(personne);
		}
	}
	
	
	
	// méthode pour vérifier si l'email est unique 
	public boolean verifierEmailPersonneUnique(String email)throws BusinessException
	{
	    boolean emailEstUnique = true ;
		Personne  personne = new Personne();
		personne = personneDAO.selectPersonneByEmail(email);
		
		if(personne.getNom() != null) 
		{
			emailEstUnique = false;
		
		}
		return emailEstUnique;
	}
	
	//méthode pour valider les données insérées par l'utilisateur quand on ajoute une personne 
	public boolean validerPersonnePourAjouter (Personne personne) throws BusinessException
	{
		boolean estValide = true ; 
		StringBuilder sb = new StringBuilder();
		//vérifier si l'objet est null 
		if (personne == null) 
		{
			sb.append(" il n'y a pas un objet à insérer ");
			estValide = false ; 
		}
		//vérifier le nom 
		if(personne.getNom()==null ||personne.getNom().trim().length()==0|| personne.getNom().trim().length()>150) 
		{
			sb.append("Le nom doit être renseigné et ne doit pas dépasser 150 caractères. ");
			estValide = false ; 
		}
		//vérifier le prénom
		if(personne.getPrenom()==null ||personne.getPrenom().trim().length()==0|| personne.getPrenom().trim().length()>150) 
		{
			sb.append("Le prénom doit être renseigné et ne doit pas dépasser 150 caractères. ");
			estValide = false ;
		}
		//vérifier le nom du structure 
		if(personne.getNomStructure().trim().length()>250) 
		{
			sb.append(" Nom du structure dépasse 250 caractères. ");
			estValide = false ;
		}
		//verifier l'email
		if(personne.getEmail()==null ||personne.getEmail().trim().length()==0|| personne.getEmail().trim().length()>150) 
		{
			sb.append("L'Email doit être renseigné et ne doit pas dépasser 150 caractères. ");
			estValide = false ;	
		}
		//vérifier le mot de pase 
		if(personne.getMotDePasse().trim().length()>250 )
		{
			sb.append(" Mot de Passe est  érroné ou dépassé 250 caractères. ");
			estValide = false ;	
		}
		// valider email unique 
		if(!verifierEmailPersonneUnique(personne.getEmail())) 
		{
			sb.append("l'Email est déjà utilisé et doit être unique ");
			estValide= false;
		}
		//vérifier le nom et le prénom ensemble si déjaà utilisé pour une autre personne 
		if(! verifierNomPrenomUniquesEnsemble(personne.getNom(),personne.getPrenom())) 
		{
			sb.append(" le Nom et le Prénom font référence à une personne déjà enregistrée. ");
			estValide = false;
		}
		//valider la zone de saisie du nom 
	    if(personne.getNom().trim().contains("_")||personne.getNom().trim().contains(".")||personne.getNom().trim().contains(" ")||personne.getNom().trim().contains("<")||personne.getNom().trim().contains(">")||personne.getNom().trim().contains(",")||personne.getNom().trim().contains("?")||personne.getNom().trim().contains(";")||personne.getNom().trim().contains("/")||personne.getNom().trim().contains("!")||personne.getNom().trim().contains("§")||personne.getNom().trim().contains("*")||personne.getNom().trim().contains("%")||personne.getNom().trim().contains("$")||personne.getNom().trim().contains("£")||personne.getNom().trim().contains("¤")||personne.getNom().trim().contains("+")||personne.getNom().trim().contains("}")||personne.getNom().trim().contains("=")||personne.getNom().trim().contains("^")||personne.getNom().trim().contains(")")||personne.getNom().trim().contains("°")||personne.getNom().trim().contains("]")||personne.getNom().trim().contains("@")||personne.getNom().trim().contains("\\")||personne.getNom().trim().contains("`")||personne.getNom().trim().contains("|")||personne.getNom().trim().contains("(")||personne.getNom().trim().contains("[")||personne.getNom().trim().contains("{")||personne.getNom().trim().contains("\"")||personne.getNom().trim().contains("#")||personne.getNom().trim().contains("~")||personne.getNom().trim().contains("&")||personne.getNom().trim().contains("8")||personne.getNom().trim().contains("7")||personne.getNom().trim().contains("6")||personne.getNom().trim().contains("5")||personne.getNom().trim().contains("4")||personne.getNom().trim().contains("3")||personne.getNom().trim().contains("2")||personne.getNom().trim().contains("1")||personne.getNom().trim().contains("0")||personne.getNom().trim().contains("9")) 
	    {
	    	sb.append(" la zone de saisie du Nom  n'accepte que des lettres et le tiret. ");
	    	estValide = false;
	    }
	  //valider la zone de saisie du Prénom 
	    if(personne.getPrenom().trim().contains("_")||personne.getPrenom().trim().contains(".")||personne.getPrenom().trim().contains(" ")||personne.getPrenom().trim().contains("<")||personne.getPrenom().trim().contains(">")||personne.getPrenom().trim().contains(",")||personne.getPrenom().trim().contains("?")||personne.getPrenom().trim().contains(";")||personne.getPrenom().trim().contains("/")||personne.getPrenom().trim().contains("!")||personne.getPrenom().trim().contains("§")||personne.getPrenom().trim().contains("*")||personne.getPrenom().trim().contains("%")||personne.getPrenom().trim().contains("$")||personne.getPrenom().trim().contains("£")||personne.getPrenom().trim().contains("¤")||personne.getPrenom().trim().contains("+")||personne.getPrenom().trim().contains("}")||personne.getPrenom().trim().contains("=")||personne.getPrenom().trim().contains("^")||personne.getPrenom().trim().contains(")")||personne.getPrenom().trim().contains("°")||personne.getPrenom().trim().contains("]")||personne.getPrenom().trim().contains("@")||personne.getPrenom().trim().contains("\\")||personne.getPrenom().trim().contains("`")||personne.getPrenom().trim().contains("|")||personne.getPrenom().trim().contains("(")||personne.getPrenom().trim().contains("[")||personne.getPrenom().trim().contains("{")||personne.getPrenom().trim().contains("\"")||personne.getPrenom().trim().contains("#")||personne.getPrenom().trim().contains("~")||personne.getPrenom().trim().contains("&")||personne.getPrenom().trim().contains("8")||personne.getPrenom().trim().contains("7")||personne.getPrenom().trim().contains("6")||personne.getPrenom().trim().contains("5")||personne.getPrenom().trim().contains("4")||personne.getPrenom().trim().contains("3")||personne.getPrenom().trim().contains("2")||personne.getPrenom().trim().contains("1")||personne.getPrenom().trim().contains("0")||personne.getPrenom().trim().contains("9")) 
	    {
	    	sb.append(" la zone de saisie du Prénom  n'accepte que des lettres et le tiret. ");
	    	estValide = false;
	    }
	    //valider la zone de saisie du nom Structure 
	    if(personne.getNomStructure().trim().contains(".")||personne.getNomStructure().trim().contains("<")||personne.getNomStructure().trim().contains(">")||personne.getNomStructure().trim().contains(",")||personne.getNomStructure().trim().contains("?")||personne.getNomStructure().trim().contains(";")||personne.getNomStructure().trim().contains("/")||personne.getNomStructure().trim().contains("!")||personne.getNomStructure().trim().contains("§")||personne.getNomStructure().trim().contains("*")||personne.getNomStructure().trim().contains("%")||personne.getNomStructure().trim().contains("$")||personne.getNomStructure().trim().contains("£")||personne.getNomStructure().trim().contains("¤")||personne.getNomStructure().trim().contains("+")||personne.getNomStructure().trim().contains("}")||personne.getNomStructure().trim().contains("=")||personne.getNomStructure().trim().contains("^")||personne.getNomStructure().trim().contains(")")||personne.getNomStructure().trim().contains("°")||personne.getNomStructure().trim().contains("]")||personne.getNomStructure().trim().contains("@")||personne.getNomStructure().trim().contains("\\")||personne.getNomStructure().trim().contains("`")||personne.getNomStructure().trim().contains("|")||personne.getNomStructure().trim().contains("(")||personne.getNomStructure().trim().contains("[")||personne.getNomStructure().trim().contains("{")||personne.getNomStructure().trim().contains("\"")||personne.getNomStructure().trim().contains("#")||personne.getNomStructure().trim().contains("~")||personne.getNomStructure().trim().contains("&")) 
	    {
	    	sb.append(" la zone de saisie du Nom du Structure   n'accepte que des lettres,Des nombres ,Tiret et Tiret bas. ");
	    	estValide = false;
	    }
		if(! estValide) 
		{
			BusinessException exception = new BusinessException(sb.toString());
			throw exception;
		}
	
		return estValide ;
	}
	

	//création de la méthode qui appelle la DAL pour récupérer toutes les personnes stockées dans la base de données 
	public List<Personne> selectTousLesPersonnes() throws BusinessException
	{
		List<Personne> listePersonne = new ArrayList<Personne>();
		listePersonne = personneDAO.selectTousLesPersonne();
		return listePersonne ; 
	}
	
	// création de la méthode qui fait appel au dal pour modifier une personne 
	public void modifierPersonne (int id , Personne personne) throws BusinessException
	{

		if(validerPersonnePourModifier(personne))
		{
			personneDAO.modifierPersonne(id, personne);
		}
	}
	
	//création de la méthode qui fait appel au DAL pour récupérer une personne par son id 
	public Personne selectPersonneById(int id) throws BusinessException
	{
		Personne personne = new Personne(); 
        personne= personneDAO.selctPersonneById(id);
		
		
		return personne ;
	}
	
	//création de la méthode qui fait appel au DAL pour supprimer une personnes 
	public void supprimerPersonne(int id)throws BusinessException
	{
		if(id != 0) 
		{
		personneDAO.supprimerPersonne(id);
		
	}
		
		
	
	}
	
	// méthode qui fait appel au Dal pour verifier la personne par son email et son id  
	public boolean verifierEmailPersonneUniqueAvecId (String email , int id ) throws BusinessException
	{
		Personne personne = new Personne();
		boolean estValide = true ; 
		personne = personneDAO.selectPersonneByEmailAvecId(email, id);
		if(personne.getNom()!= null) 
		{
			estValide = false;
		}
		return estValide;
	}
	
	//méthode pour valider les données insérées par l'utilisateur quand on ajoute une personne 
		public boolean validerPersonnePourModifier (Personne personne) throws BusinessException
		{
			boolean estValide = true ; 
			StringBuilder sb = new StringBuilder();
			//vérifier si l'objet est null 
			if (personne == null) 
			{
				sb.append(" il n'y a pas un objet à insérer ");
				estValide = false ; 
			}
			//vérifier le nom 
			if(personne.getNom()==null ||personne.getNom().trim().length()==0|| personne.getNom().trim().length()>150) 
			{
				sb.append("Le nom doit être renseigné et ne doit pas dépasser 150 caractères. ");
				estValide = false ; 
			}
			//vérifier le prénom
			if(personne.getPrenom()==null ||personne.getPrenom().trim().length()==0|| personne.getPrenom().trim().length()>150) 
			{
				sb.append("Le prénom doit être renseigné et ne doit pas dépasser 150 caractères. ");
				estValide = false ;
			}
			//vérifier le nom du structure 
			if(personne.getNomStructure().trim().length()>250) 
			{
				sb.append(" Nom du structure dépasse 250 caractères. ");
				estValide = false ;
			}
			//verifier l'email
			if(personne.getEmail()==null ||personne.getEmail().trim().length()==0|| personne.getEmail().trim().length()>150) 
			{
				sb.append("L'Email doit être renseigné et ne doit pas dépasser 150 caractères. ");
				estValide = false ;	
			}
			//vérifier le mot de pase 
			if(personne.getMotDePasse().trim().length()>250 )
			{
				sb.append(" Mot de Passe est  érroné ou dépasse 250 caractères. ");
				estValide = false ;	
			}
			// valider email unique avec id 
			if(!verifierEmailPersonneUniqueAvecId(personne.getEmail(),personne.getIdPerso())) 
			{
				sb.append("l'Email est déjà utilisé et doit être unique !... ");
				estValide= false;
			}
			//vérifier le nom et le prénom ensemble si déjaà utilisé pour une autre personne quand on modifier une personne 
			if(! verifierNomPrenomUniquesEnsembleModifier(personne.getNom(),personne.getPrenom(),personne.getIdPerso())) 
			{
				sb.append(" le Nom et le Prénom font référence à une personne déjà enregistrée. ");
				estValide = false;
			}
			//valider la zone de saisie du nom 
		    if(personne.getNom().trim().contains("_")||personne.getNom().trim().contains(".")||personne.getNom().trim().contains(" ")||personne.getNom().trim().contains("<")||personne.getNom().trim().contains(">")||personne.getNom().trim().contains(",")||personne.getNom().trim().contains("?")||personne.getNom().trim().contains(";")||personne.getNom().trim().contains("/")||personne.getNom().trim().contains("!")||personne.getNom().trim().contains("§")||personne.getNom().trim().contains("*")||personne.getNom().trim().contains("%")||personne.getNom().trim().contains("$")||personne.getNom().trim().contains("£")||personne.getNom().trim().contains("¤")||personne.getNom().trim().contains("+")||personne.getNom().trim().contains("}")||personne.getNom().trim().contains("=")||personne.getNom().trim().contains("^")||personne.getNom().trim().contains(")")||personne.getNom().trim().contains("°")||personne.getNom().trim().contains("]")||personne.getNom().trim().contains("@")||personne.getNom().trim().contains("\\")||personne.getNom().trim().contains("`")||personne.getNom().trim().contains("|")||personne.getNom().trim().contains("(")||personne.getNom().trim().contains("[")||personne.getNom().trim().contains("{")||personne.getNom().trim().contains("\"")||personne.getNom().trim().contains("#")||personne.getNom().trim().contains("~")||personne.getNom().trim().contains("&")||personne.getNom().trim().contains("8")||personne.getNom().trim().contains("7")||personne.getNom().trim().contains("6")||personne.getNom().trim().contains("5")||personne.getNom().trim().contains("4")||personne.getNom().trim().contains("3")||personne.getNom().trim().contains("2")||personne.getNom().trim().contains("1")||personne.getNom().trim().contains("0")||personne.getNom().trim().contains("9")) 
		    {
		    	sb.append(" la zone de saisie du Nom  n'accepte que des lettres et le tiret. ");
		    	estValide = false;
		    }
		  //valider la zone de saisie du Prénom 
		    if(personne.getPrenom().trim().contains("_")||personne.getPrenom().trim().contains(".")||personne.getPrenom().trim().contains(" ")||personne.getPrenom().trim().contains("<")||personne.getPrenom().trim().contains(">")||personne.getPrenom().trim().contains(",")||personne.getPrenom().trim().contains("?")||personne.getPrenom().trim().contains(";")||personne.getPrenom().trim().contains("/")||personne.getPrenom().trim().contains("!")||personne.getPrenom().trim().contains("§")||personne.getPrenom().trim().contains("*")||personne.getPrenom().trim().contains("%")||personne.getPrenom().trim().contains("$")||personne.getPrenom().trim().contains("£")||personne.getPrenom().trim().contains("¤")||personne.getPrenom().trim().contains("+")||personne.getPrenom().trim().contains("}")||personne.getPrenom().trim().contains("=")||personne.getPrenom().trim().contains("^")||personne.getPrenom().trim().contains(")")||personne.getPrenom().trim().contains("°")||personne.getPrenom().trim().contains("]")||personne.getPrenom().trim().contains("@")||personne.getPrenom().trim().contains("\\")||personne.getPrenom().trim().contains("`")||personne.getPrenom().trim().contains("|")||personne.getPrenom().trim().contains("(")||personne.getPrenom().trim().contains("[")||personne.getPrenom().trim().contains("{")||personne.getPrenom().trim().contains("\"")||personne.getPrenom().trim().contains("#")||personne.getPrenom().trim().contains("~")||personne.getPrenom().trim().contains("&")||personne.getPrenom().trim().contains("8")||personne.getPrenom().trim().contains("7")||personne.getPrenom().trim().contains("6")||personne.getPrenom().trim().contains("5")||personne.getPrenom().trim().contains("4")||personne.getPrenom().trim().contains("3")||personne.getPrenom().trim().contains("2")||personne.getPrenom().trim().contains("1")||personne.getPrenom().trim().contains("0")||personne.getPrenom().trim().contains("9")) 
		    {
		    	sb.append(" la zone de saisie du Prénom  n'accepte que des lettres et le tiret. ");
		    	estValide = false;
		    }
		    //valider la zone de saisie du nom Structure 
		    if(personne.getNomStructure().trim().contains(".")||personne.getNomStructure().trim().contains("<")||personne.getNomStructure().trim().contains(">")||personne.getNomStructure().trim().contains(",")||personne.getNomStructure().trim().contains("?")||personne.getNomStructure().trim().contains(";")||personne.getNomStructure().trim().contains("/")||personne.getNomStructure().trim().contains("!")||personne.getNomStructure().trim().contains("§")||personne.getNomStructure().trim().contains("*")||personne.getNomStructure().trim().contains("%")||personne.getNomStructure().trim().contains("$")||personne.getNomStructure().trim().contains("£")||personne.getNomStructure().trim().contains("¤")||personne.getNomStructure().trim().contains("+")||personne.getNomStructure().trim().contains("}")||personne.getNomStructure().trim().contains("=")||personne.getNomStructure().trim().contains("^")||personne.getNomStructure().trim().contains(")")||personne.getNomStructure().trim().contains("°")||personne.getNomStructure().trim().contains("]")||personne.getNomStructure().trim().contains("@")||personne.getNomStructure().trim().contains("\\")||personne.getNomStructure().trim().contains("`")||personne.getNomStructure().trim().contains("|")||personne.getNomStructure().trim().contains("(")||personne.getNomStructure().trim().contains("[")||personne.getNomStructure().trim().contains("{")||personne.getNomStructure().trim().contains("\"")||personne.getNomStructure().trim().contains("#")||personne.getNomStructure().trim().contains("~")||personne.getNomStructure().trim().contains("&")) 
		    {
		    	sb.append(" la zone de saisie du Nom du Structure   n'accepte que des lettres,Des nombre ,Tiret et Tiret bas. ");
		    	estValide = false;
		    }
			
			if(! estValide) 
			{
				BusinessException exception = new BusinessException(sb.toString());
				throw exception;
			}
		
			return estValide ;
		}
		
		//création de la méthode qui fait appel au DAL pour se connecter 
		public Personne selectPersonneConnexion(String email , String motDePasse)throws BusinessException
		{
			Personne personne = new Personne();
			if(email != null && motDePasse != null) 
			{
				personne= personneDAO.selectPersonneByEmailAndIdConnexion(email, motDePasse);
			}
			return personne;
		}
		
		//méthode qui fait appel au DAL pour vérifier si le nom et prénom de la personne à créer sont déjà utilisés pour une autre personne quant on ajoute une npuvelle personne
        public boolean verifierNomPrenomUniquesEnsemble(String nom , String prenom) throws BusinessException
        {
        	Personne personne = new Personne();
        	boolean nomPrenomUnique = true ;
        	personne = personneDAO.selectPersonneByNomAndPrenom(nom, prenom);
        	if(personne.getNom() != null)
        	{
        		nomPrenomUnique = false;
        	}
        	return nomPrenomUnique;
        }
        
        
		//méthode qui fait appel au DAL pour vérifier si le nom et prénom de la personne à créer sont déjà utilisés pour une autre personne quant on modifie une  personne

        public boolean verifierNomPrenomUniquesEnsembleModifier (String nom , String prenom ,int id) throws BusinessException
        {
        	Personne personne = new Personne();
        	boolean nomPrenomUnique = true ;
        	personne = personneDAO.selectPersonneByNomAndPrenomModifier(nom, prenom, id);
        	if(personne.getNom() != null)
        	{
        		nomPrenomUnique = false;
        	}
        	return nomPrenomUnique;
        }
}

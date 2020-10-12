package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.classfile.PMGClass;

import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class PersonneDAOJdbcImpl implements PersonneDAO
{

	private final static String INSERT_PERSONNE = "INSERT INTO personnes (nom,prenom,nomStructure,email,motDePasse,administrateur)values(?,?,?,?,?,?)";
	private final static String SELECT_PERSONNE_BY_EMAIL = "SELECT * FROM personnes where email = ?;";
	private final static String SELECT_TOUS_LES_PERSONNES ="SELECT * FROM personnes ORDER BY idPerso ASC ";
	private final static String MODIFIER_PERSONNE = "UPDATE personnes SET nom = ?,prenom=?,nomStructure=?,email =?,motDePasse=?,administrateur = ?  where idPerso = ?;";
	private final static String SELECT_PERSONNE_BY_ID = "SELECT * FROM personnes WHERE idPerso = ?";
	private final static String DELETE_PERSONNE = "DELETE FROM personnes WHERE idPerso =?";
	private final static String SELECT_PERSONNE_BY_EMAIL_AND_ID_POUR_MODIFIER = "SELECT * FROM personnes WHERE (idPerso !=?  AND email =?  );";
	private final static String SELECT_PERSONNE_CONNEXION = "SELECT * FROM personnes WHERE email = ? AND motDePasse = ?;";
	private final static String SELECT_PERSONNE_BY_NOM_AND_PRENOM = "SELECT idPerso ,nom ,prenom ,nomStructure ,email ,motDePasse , administrateur from personnes where nom =? and prenom =?;";
	private final static String SELECT_PERSONNR_BY_NOM_AND_PRENOM_MODIFIER = "SELECT idPerso ,nom ,prenom ,nomStructure ,email ,motDePasse , administrateur from personnes where nom =?  and prenom = ? and idPerso != ?;";  
		
	
	@Override
	public int insertPersonne(Personne personne) throws BusinessException {
		//test si l'objet est null 
		if(personne == null) 
		{
			BusinessException exception = new BusinessException("il n'y a aucune Personne à insérer ");
			throw exception;
		}
		//ovrire la connexion
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la reqûete
			PreparedStatement stmt = cnx.prepareStatement(INSERT_PERSONNE,PreparedStatement.RETURN_GENERATED_KEYS);
			//pramettre la reqûete
			stmt.setString(1,personne.getNom());
			stmt.setString(2, personne.getPrenom());
			stmt.setString(3,personne.getNomStructure());
			stmt.setString(4,personne.getEmail());
			stmt.setString(5,personne.getMotDePasse());
			stmt.setBoolean(6,personne.isAdministrateur());
			// envoyer la reqûete à la BDD
			stmt.executeUpdate() ; 
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) 
			{
				personne.setIdPerso(rs.getInt(1));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération,l'insersion de la Personne n'a pas été effectuée avec succès");
			throw exception;
		}
		
		return personne.getIdPerso();
	}

	//méthode pour vérifier si l'email saisie par l'utilisateur est déjà utilisé ou pas quand on ajoute une nouvelle personne
	@Override
	public Personne selectPersonneByEmail(String email) throws BusinessException {
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			
			// préparation de la reqûete 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNE_BY_EMAIL);
			// paramétrer la reqête
			stmt.setString(1,email);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setIdPerso(rs.getInt("idPerso"));;
				personne.setPrenom(rs.getString("prenom"));
				personne.setNom(rs.getString("nom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
			}
			
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, l'insertion de la personne  n'a pas été effectuée avec succès ");
			throw exception;
		}
		
		return personne;
	}
	
	//création de la méthode pour verifier l'email si déja utilisé quand on modifie une personne déja existe dans la BDD
	@Override
	public Personne selectPersonneByEmailAvecId(String email, int id) throws BusinessException 
	{
	
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNE_BY_EMAIL_AND_ID_POUR_MODIFIER);
			//paramétrer la requête 
			stmt.setString(2, email);
			stmt.setInt(1,id);
			//envoyer la requête
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setIdPerso(rs.getInt("idPerso"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération en récupérant la personne depuis la base de données ");
			throw exception;
		}
		
		return personne;
	}
   // méthode pour Récupérer toutes les personnes stockées dans la base de données 
	@Override
	public List<Personne> selectTousLesPersonne() throws BusinessException {
		List<Personne> listePersonnes = new ArrayList<Personne>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la reqûete
			PreparedStatement stmt = cnx.prepareStatement(SELECT_TOUS_LES_PERSONNES);
			//envoyer la reqûete 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				Personne personne = new Personne();
				personne.setIdPerso(rs.getInt("idPerso"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
				
				listePersonnes.add(personne);
			}
			
		} catch (SQLException e) {
			
e.printStackTrace();
BusinessException exception = new BusinessException("problème pendant la récupération des Personnes,ou aucune personne existe dans la base de données !..");
throw exception;
		}
		return listePersonnes;
	}

	//création de la méthode qui pemet de supprimer une personne
	@Override
	public void modifierPersonne(int id ,Personne personne) throws BusinessException 
	{
	try(Connection cnx = ConnectionProvider.getConnection())
	{
		//préparation de la reqûete 
		PreparedStatement stmt = cnx.prepareStatement(MODIFIER_PERSONNE);
		stmt.setString(1,personne.getNom());
		stmt.setString(2,personne.getPrenom());
		stmt.setString(3, personne.getNomStructure());
		stmt.setString(4,personne.getEmail());
		stmt.setString(5, personne.getMotDePasse());
		stmt.setBoolean(6, personne.isAdministrateur());
		stmt.setInt(7, id);
		//envoyer la reqûete 
		stmt.executeUpdate();
	} catch (SQLException e) {
	
		e.printStackTrace();
		BusinessException exception = new BusinessException("problème pendant l'opération , la modification n'a pas été effectuée avec succès");
		throw exception;
	}
		
	}

	//création de la méthode qui récupère une personne par son id 
	@Override
	public Personne selctPersonneById(int id) throws BusinessException 
	{
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la reqûete 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNE_BY_ID);
			//parametrer la reqûete 
			stmt.setInt(1, id);
			//envoyer la reqûete
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune personne existe pour l'id utilisé");
			throw exception;
		}
		return personne;
	}

	@Override
	public void supprimerPersonne(int id) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(DELETE_PERSONNE);
			//parametrer la requête 
			stmt.setInt(1,id);
			//envoyer la requête 
			stmt.executeUpdate();
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe pour cette personne ,Gérez ce problème par la gestion Des Réservations, la suppressoin n'a pas été effectuée avec succès ");
			throw exception;
		}
		
	}

	@Override
	public Personne selectPersonneByEmailAndIdConnexion(String email, String motDePasse) throws BusinessException {
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNE_CONNEXION);
			//pramétrer la requête 
			stmt.setString(1, email);
			stmt.setString(2, motDePasse);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setIdPerso(rs.getInt("idPerso"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune personne existe pour l'email et le mot de passe utilisés pour se connecter ");
			throw exception;
		}
		return personne;
	}

	//méthode pour récupérer pesonne par son nom et son prénom 
	@Override
	public Personne selectPersonneByNomAndPrenom(String nom, String prenom) throws BusinessException 
	{
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNE_BY_NOM_AND_PRENOM);
			//paramétrer la requête 
			stmt.setString(1,nom);
			stmt.setString(2,prenom);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setIdPerso(rs.getInt("idPerso"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune personne existe pour ce nom et ce prénom.");
			throw exception;
		}
		return personne;
	}

	//méthode pour récupérer personne par son nom et son prénom quand on le modifie 
	@Override
	public Personne selectPersonneByNomAndPrenomModifier(String nom, String prenom, int id) throws BusinessException 
	{
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_PERSONNR_BY_NOM_AND_PRENOM_MODIFIER);
			//paramétrer la requête 
			stmt.setString(1,nom);
			stmt.setString(2,prenom);
			stmt.setInt(3,id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				personne.setIdPerso(rs.getInt("idPerso"));
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
			}
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune personne existe pour ce nom et ce prénom.");
			throw exception;
		}
		return personne;
	}

	

	
}

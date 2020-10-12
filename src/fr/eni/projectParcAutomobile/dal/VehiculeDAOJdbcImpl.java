package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.BuildJarIndex;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class VehiculeDAOJdbcImpl implements VehiculeDAO
{
	private final static String INSERT_VEHICULE ="INSERT INTO vehicules (designation,immat,dateAchat,campus_idCampus) values (?,?,?,?);";
	private final static String SELECT_VEHICULE_BY_IMMAT_AJOUTER = "SELECT idVehic AS idVehic ,designation AS designation , immat AS immatriculation ,dateAchat AS dateAchat , campus_idCampus AS noCampus , libelle as campus FROM vehicules join campus on  campus_idCampus = idCampus WHERE immat =?;";
	private final static String SELECT_TOUS_LES_VEHICULES = "SELECT idVehic AS idVehic ,designation AS designation , immat AS immatriculation ,dateAchat AS dateAchat , campus_idCampus AS noCampus , libelle as campus FROM vehicules join campus on  campus_idCampus = idCampus ORDER BY idVehic ASC;";
	private final static String SELECT_VEHICULE_BY_ID = "SELECT idVehic AS idVehic ,designation AS designation , immat AS immatriculation ,dateAchat AS dateAchat , campus_idCampus AS noCampus , libelle as campus FROM vehicules join campus on  campus_idCampus = idCampus  WHERE idVehic = ?  ORDER BY idVehic ASC ;";
	private final static String MODIFIER_VEHICULE = "UPDATE vehicules SET designation = ? , immat = ?, dateAchat = ?, campus_idCampus = ?  WHERE idVehic = ? ;";
	private final static String SELECT_VEHICULE_BY_IMMAT_MODIFIER = "SELECT idVehic AS idVehic ,designation AS designation , immat AS immatriculation ,dateAchat AS dateAchat , campus_idCampus AS noCampus , libelle as campus FROM vehicules join campus on  campus_idCampus = idCampus WHERE immat =? AND idVehic != ?;";
	private final static String SUPPRIMER_VEHICULE = "DELETE FROM vehicules WHERE idVehic =? ;";

	// méthode qui pemet d'insérer un nouveau véhicule à la base de données 
	@Override
	public int insertVehicule(Vehicule vehicule) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(INSERT_VEHICULE,PreparedStatement.RETURN_GENERATED_KEYS);
			//pramétrer la requête 
			stmt.setString(1,vehicule.getDesignation());
			stmt.setString(2,vehicule.getImmatriculation());
			stmt.setDate(3,new java.sql.Date(vehicule.getDateAchat().getTime()));
			stmt.setInt(4,vehicule.getCampus().getIdCampus());
			
			// envoyer la requête 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
			{
				vehicule.setIdVehic(rs.getInt(1));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , l'insertion d'un véhicule n'a pas été effectuée avec succès");
			throw exception;
			
		}
		return vehicule.getIdVehic();
	}

	//méthode permet de chercher un véhicule par sa immatriculation
	@Override
	public Vehicule selectVehiculeByImmat(String immatriculation) throws BusinessException {
		Vehicule vehicule = new Vehicule() ; 
		Campus campus = new Campus() ;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_VEHICULE_BY_IMMAT_AJOUTER);
			//pramétrer la requête 
			stmt.setString(1,immatriculation);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				campus.setIdCampus(rs.getInt("noCampus"));
				campus.setLibelle(rs.getString("campus"));
				vehicule.setCampus(campus);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucun véhicule existe pour cette immatriculation ");
			throw exception;
		}
		
		return vehicule;
	}
    // méthode pour récupérer tous les véhicules stockés dans la base de données 
	@Override
	public List<Vehicule> selectTousLesVehicules() throws BusinessException 
	{
		List<Vehicule> listeVehicule = new ArrayList<Vehicule>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparer la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_TOUS_LES_VEHICULES);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				
				Vehicule vehicule = new Vehicule();
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				Campus campus = new Campus();
				campus.setIdCampus(rs.getInt("noCampus"));
				campus.setLibelle(rs.getString("campus"));
				vehicule.setCampus(campus);
				listeVehicule.add(vehicule);
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception  = new BusinessException(" problème pendant l'opération, ou aucun véhicule existe dans la base de données. ");
			throw exception;
		} 
		return listeVehicule;
	}

	//méthode qui permet de récupérer un véhicule par son id 
	@Override
	public Vehicule selectVehiculeById(int id) throws BusinessException 
	{
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparer la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_VEHICULE_BY_ID);
			//pramétrer la requête 
			stmt.setInt(1, id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				Campus campus = new Campus();
				campus.setIdCampus(rs.getInt("noCampus"));
				campus.setLibelle(rs.getString("campus"));
				vehicule.setCampus(campus);
				
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception  = new BusinessException(" problème pendant l'opération ,ou aucun véhicule existe dans la base de données pour cet id . ");
			throw exception;
		}
		
		return vehicule;
	}

	//méthode qui permet de modifier un véhicule existe dans la base de données 
	@Override
	public void modifierVehicule(int id ,Vehicule vehicule) throws BusinessException 
	{
	
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparer la requête 
			PreparedStatement stmt = cnx.prepareStatement(MODIFIER_VEHICULE);
			//paramétrer la véhicule 
			stmt.setString(1,vehicule.getDesignation());
			stmt.setString(2,vehicule.getImmatriculation());
			stmt.setDate(3,new java.sql.Date(vehicule.getDateAchat().getTime()));
			stmt.setInt(4,vehicule.getCampus().getIdCampus());
			stmt.setInt(5,id);
			
			//envoyer la requête 
			stmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , la modification n'a pas été effectuée avec succès. ");
			throw exception;
		}
		
	}

	//méthode permet de récupérer un véhicule selon son immatriculation et la condition de l'id 
	@Override
	public Vehicule selectVehiculeByImmatModifier(String immatriculation ,int id ) throws BusinessException {
		
		Vehicule vehicule = new Vehicule() ; 
		Campus campus = new Campus() ;
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_VEHICULE_BY_IMMAT_MODIFIER);
			//pramétrer la requête 
			stmt.setString(1,immatriculation);
			stmt.setInt(2, id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				campus.setIdCampus(rs.getInt("noCampus"));
				campus.setLibelle(rs.getString("campus"));
				vehicule.setCampus(campus);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucun véhicule existe pour cette immatriculation et l'id");
			throw exception;
		}
		return vehicule;
	}

	//méthode pour supprimer un véhicule 
	@Override
	public void supprimerVehicule(int id) throws BusinessException 
	{
	try(Connection cnx = ConnectionProvider.getConnection())
	{
	
		//préparation de la requête 
		PreparedStatement stmt=cnx.prepareStatement(SUPPRIMER_VEHICULE);
		//paramétrer la requête 
		stmt.setInt(1, id);
		//envoyer la requête 
		stmt.executeUpdate();
	} catch (SQLException e) {
		e.printStackTrace();
		BusinessException exception = new BusinessException("problème pendant l'opération,ou ce véhicule a une ou des périodes d'indisponibilités , gérerez ce problème par la Gestion Des Indisponibilités ,la suppression n'a pas été effectuée avec succès. ");
	    throw exception;
	}
	
		
		
	}

}

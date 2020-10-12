package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class DestinationDAOJdbcImpl implements DestinationDAO 
{

	private final static String INSERT_DESTINATION = "INSERT INTO destinations (libelle , codeDes) values (?,?);" ;
	private final static String SELECT_DESTINATION_BY_LIBELLE = "SELECT idDes as idDes , libelle as libelle , codeDes as codeDes  FROM destinations WHERE libelle = ?;";
	private final static String SELECT_DESTINATION_BY_CODEDES = "SELECT idDes as idDes , libelle as libelle , codeDes as codeDes  FROM destinations WHERE codeDes = ?;";
	private final static String SELECT_TOUS_LES_DESTINATIONS =  "SELECT idDes as idDes , libelle as libelle , codeDes as codeDes  FROM destinations ; ";
	private final static String MODIFIER_DESTINATION = "UPDATE destinations SET libelle = ? ,codeDes = ? WHERE idDEs =?;";
	private final static String SELECT_DESTINATION_BY_ID = "SELECT idDes AS idDes ,libelle AS libelle , codeDes AS codeDes FROM destinations WHERE idDes = ?;";
	private final static String DELETE_DESTINATION = "DELETE FROM destinations WHERE idDes = ? ;";
	private final static String SELECT_DESTINATION_BY_LIBELLE_AND_ID = "SELECT idDes AS idDes , libelle AS libelle , codeDes AS codeDes  FROM destinations WHERE libelle = ? and idDes !=? ;";
	private final static String SELECT_DESTINATION_BY_CODEDES_AND_ID = "SELECT idDes AS idDes , libelle AS libelle , codeDes AS codeDes  FROM destinations WHERE codeDes = ? and idDes !=?;";
	@Override
	public int insertDestination(Destination destination) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(INSERT_DESTINATION,PreparedStatement.RETURN_GENERATED_KEYS);
			//paramétrer la requête 
			stmt.setString(1,destination.getLibelle());
			stmt.setString(2, destination.getCodeDes());
			//envoyer la requête
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) 
			{
				destination.setIdDes(rs.getInt(1));
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , l'insertion n'a pas été effectuée avec succès");
			throw exception;
		}
		return destination.getIdDes();
	}
	@Override
	public Destination selectDestinationByLibelle(String libelle) throws BusinessException
	{
		Destination destination = new Destination();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_DESTINATION_BY_LIBELLE);
			//paramétrer la requête 
			stmt.setString(1,libelle);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune Destination exite dan la Base de Données pour ce libellé");
			throw exception;
		}
		return destination;
	}
	@Override
	public Destination selectDestinationByCodeDes(String codeDes) throws BusinessException {
		
		Destination destination = new Destination();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_DESTINATION_BY_CODEDES);
			//paramétrer la requête 
			stmt.setString(1,codeDes);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune Destination exite dan la Base de Données pour ce libellé");
			throw exception;
		}
		return destination;
	}
	
	//méthode pour récupérer toutes les destinations stockées dans la base de données 
	@Override
	public List<Destination> selectlisteDestinations() throws BusinessException {
		List<Destination> listeDestinations = new ArrayList<Destination>();
		try(Connection cnx =ConnectionProvider.getConnection())
		{
			//préparer la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_TOUS_LES_DESTINATIONS);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				Destination destination = new Destination();
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
				
				listeDestinations.add(destination);
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune Destination stockée dans la base de données  ");
			throw exception ; 
		}
		return listeDestinations;
	}
	@Override
	public void modifierDestination(int id, Destination destination) throws BusinessException {
	    
	
	    try(Connection cnx = ConnectionProvider.getConnection())
	    {
	    	//préparation de la requête
	    	PreparedStatement stmt = cnx.prepareStatement(MODIFIER_DESTINATION);
	    	//pramétrer la requête 
	    	stmt.setString(1, destination.getLibelle());
	    	stmt.setString(2,destination.getCodeDes());
	    	stmt.setInt(3,id);
	    	//envoyer la requête 
	    	stmt.executeUpdate();
	    } catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , la modification n'a pas été executée avec succès");
			throw exception;
		}
		
	}
	@Override
	public Destination selectDestinationById(int id) throws BusinessException 
	{
		Destination destination = new Destination();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_DESTINATION_BY_ID);
			//pramétrer la requête 
			stmt.setInt(1,id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
			}
		} catch (SQLException e) {
	
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune destination existe pour cet id ");
		}
		return destination;
	}
	//création de la méthode qui permet de suppeimer une destination de la base de données
	@Override
	public void supprimerDestination(int id) throws BusinessException {
	
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête
			PreparedStatement stmt = cnx.prepareStatement(DELETE_DESTINATION);
			//pramétrer la requête 
			stmt.setInt(1,id);
			//envoyer la requête 
			stmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , ou il y a des Réservations qui sont associées à cette Destination , gérez ce problème par la gestion des Réservations,la suppression n'a pas été effectuée avec succès. ");
			throw exception;
		}
		
	}
	
	// création la methode pour vérifier si la libellé de la destination est unique quand on modifie la destination
	@Override
	public Destination selectDestinationByLibelleAndId(String libelle, int id) throws BusinessException
	    {
		Destination destination = new Destination();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_DESTINATION_BY_LIBELLE_AND_ID);
			//paramétrer la requête 
			stmt.setString(1,libelle);
			stmt.setInt(2, id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune Destination exite dan la Base de Données pour ce libellé et pour l'id utilisé. ");
			throw exception;
		}
		return destination;
		
	}
	
	// création la methode pour vérifier si le code destination  est unique quand on modifie la destination
	@Override
	public Destination selectDestinationByCodeDesAndId(String codeDes, int id) throws BusinessException 
	{
		Destination destination = new Destination();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_DESTINATION_BY_CODEDES_AND_ID);
			//paramétrer la requête 
			stmt.setString(1,codeDes);
			stmt.setInt(2, id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
			{
				destination.setIdDes(rs.getInt("idDes"));
				destination.setLibelle(rs.getString("libelle"));
				destination.setCodeDes(rs.getString("codeDes"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune Destination exite dan la Base de Données pour ce code destination et pour l'id utilisé. ");
			throw exception;
		}
		return destination;
		
	
	}

}

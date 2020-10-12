package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public class CampusDAOJdbcImpl implements CampusDAO 
{
	  private final static String INSERT_CAMPUS = "INSERT INTO campus (libelle)values (?);  ";
	  private final static String SELECT_CAMPUS_BY_LIBELLE = "SELECT idCampus as id   ,libelle as campus FROM campus WHERE libelle =?;";
	  private final static String SELECT_TOUS_LES_CAMPUS = "SELECT idCampus as id , libelle as campus FROM campus ORDER BY idCampus ASC  ; ";
	  private final static String MODIFIER_CAMPUS = "UPDATE campus SET libelle = ? where libelle = ?;";
      private final static String SUPPRIMER_CAMPUS ="DELETE FROM campus where idCampus = ? ; ";
      private final static String SELECT_CAMPUS_BY_LIBELLE_AND_ID ="select * from campus where libelle =?  and idCampus !=?;";
	@Override
	public int insertCampus(Campus campus) throws BusinessException 
	{
		if(campus == null )
		{
			BusinessException exception = new BusinessException("il n'y a pas aucun Campus à inserer  ");
			throw exception;
		}
		// initialisation des variables de connexion et d'accès à la bdd
		Connection cnx = null;
		PreparedStatement pstmt = null;
		try 
		{
			cnx = ConnectionProvider.getConnection();
			pstmt = cnx.prepareStatement(INSERT_CAMPUS,PreparedStatement.RETURN_GENERATED_KEYS);
			// paramétrer les données dans la reqûte 
			pstmt.setString(1,campus.getLibelle());
			//envoyer la repûte 
			pstmt.executeUpdate();
			//récupération la clé autogénéré 
			ResultSet rs = pstmt.getGeneratedKeys();
			// vérification de la réussite de l'insertion 
			if(rs.next())
			{
				campus.setIdCampus(rs.getInt(1));
			}
			//ferméture 
			rs.close();
			pstmt.close();
		} catch (SQLException e) 
		{
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération,l'insertion du campus n'a pas été effectuée avec succès ");
			throw exception;
		}
		return campus.getIdCampus();
	}

	@Override
	public Campus selectCampusByLibelle(String libelle) throws BusinessException {
		Campus campus = new Campus();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la reqûete 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_CAMPUS_BY_LIBELLE);
			//paramétrer les données dans la reqûete
			stmt.setString(1,libelle);
			//envoyer la reqûete 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				campus.setIdCampus(rs.getInt("id"));
				campus.setLibelle(rs.getString("campus"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucun Campus exite dan la Base de Données pour ce libellé ");
			throw exception;
		}
		
		return campus;
	}

	@Override
	public List<Campus> selectTousLesCampus() throws BusinessException {
	
		List<Campus> listeCampus = new ArrayList<Campus>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la reqûete 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_TOUS_LES_CAMPUS);
			//envoyer la reqûete
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				//charger la liste de Campus par les données venues de la base de données  
				Campus campus = new Campus();
				campus.setIdCampus(rs.getInt("id"));
				campus.setLibelle(rs.getString("campus"));
				listeCampus.add(campus);

			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucun Campus existe dans la base de données ");
			throw exception;
		}
		return listeCampus;
	}

	@Override
	public void modifierCampus(String libelle , Campus campus) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement stmt = cnx.prepareStatement(MODIFIER_CAMPUS);
			stmt.setString(1, libelle);
			stmt.setString(2,campus.getLibelle());
		    stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,la modification n'a pas été effectuée avec succès. ");
			throw exception;
		}
		
	}

	//méthode qui permet decsupprimer un Campus 
	@Override
	public void supprimerCampus(int idCampus) throws BusinessException {
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement stmt = cnx.prepareStatement(SUPPRIMER_CAMPUS);
			stmt.setInt(1, idCampus);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération,ou il y a des véhicules qui sont associés à ce campus ,Gérez ce problème par la Gestion Des véhicules , la suppression n'a pas été effectuée avec succès. ");
			throw exception;
		}
		
	}

	//méthode pour récupérer un campus par sa libelle et la condition de l'id
	@Override
	public Campus selectCampusByLibelleAndId(String libelle, int id) throws BusinessException 
	{
		Campus campus = new Campus();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			// préparation de la reqûete 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_CAMPUS_BY_LIBELLE_AND_ID);
			//paramétrer les données dans la reqûete
			stmt.setString(1,libelle);
			stmt.setInt(2,id);
			//envoyer la reqûete 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				campus.setIdCampus(rs.getInt("idCampus"));
				campus.setLibelle(rs.getString("libelle"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucun Campus exite dan la Base de Données pour ce libellé et pour la condition de l'id ");
			throw exception;
		}
		return campus;
	}

}

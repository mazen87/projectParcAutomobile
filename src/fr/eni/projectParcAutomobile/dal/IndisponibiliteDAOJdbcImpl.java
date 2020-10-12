
package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

public class IndisponibiliteDAOJdbcImpl implements IndisponibiliteDAO
{
	private final static String INSERT_INDISPONIBILITE = "INSERT INTO indisponibilites (dateDebut ,dateFin,motifIndisponibilite,vehicules_idVehic) values (?,?,?,?);";
	private final static String SELECT_TOUTES_LES_INDISPONIBILITES = "SELECT idIndis AS idIndis , dateDebut AS dateDebut , dateFin AS dateFin , motifIndisponibilite AS motif ,idVehic as idVehic , designation AS designation,immat as immatriculation, dateAchat AS dateAchat ,idCampus AS idCampus ,libelle AS libelle FROM indisponibilites join vehicules on vehicules_idVehic = idVehic  join campus on campus_idCampus = idCampus ORDER BY designation ,dateDebut ASC ;";
	private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUT_AND_IDVEHIC ="SELECT idIndis AS idIndis , dateDebut AS dateDebut , dateFin AS dateFin , motifIndisponibilite AS motif ,idVehic AS idVehic , designation AS designation,immat as immatriculation, dateAchat AS dateAchat ,idCampus AS idCampus ,libelle AS libelle FROM indisponibilites join vehicules on vehicules_idVehic = idVehic  join campus on campus_idCampus = idCampus  WHERE dateDebut = ? and vehicules_idVehic = ?;";
	private final static String MODIFIER_INDISPONIBILITE =  "UPDATE indisponibilites SET dateDebut = ? , dateFin = ?, motifIndisponibilite = ?, vehicules_idVehic =?  WHERE idIndis = ? ;  ";
    private final static String SELECT_INDISPONIBILITE_BY_ID = "SELECT idIndis AS idIndis , dateDebut AS dateDebut , dateFin AS dateFin , motifIndisponibilite AS motif ,idVehic as idVehic , designation AS designation,immat as immatriculation, dateAchat AS dateAchat ,idCampus AS idCampus ,libelle AS libelle FROM indisponibilites join vehicules on vehicules_idVehic = idVehic  join campus on campus_idCampus = idCampus  WHERE idIndis =? ";
    private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUT_AND_IDVEHIC_MODIFIER = "SELECT idIndis AS idIndis , dateDebut AS dateDebut , dateFin AS dateFin , motifIndisponibilite AS motif ,idVehic AS idVehic , designation AS designation,immat as immatriculation, dateAchat AS dateAchat ,idCampus AS idCampus ,libelle AS libelle FROM indisponibilites join vehicules on vehicules_idVehic = idVehic  join campus on campus_idCampus = idCampus  WHERE dateDebut =? and vehicules_idVehic =?  AND idIndis !=?;";
	private final static String DELETE_INDISPONIBILITE = "DELETE FROM indisponibilites where idIndis =?;";
    private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC = "select * from indisponibilites where ? between dateDebut and dateFin and vehicules_idVehic =? ;" ;//1
	private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_MODIFIER = "select * from indisponibilites where ? between dateDebut and dateFin and vehicules_idVehic =? and idIndis !=? ;" ; //1 modifier
    private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_PARTIE2 = "select * from indisponibilites where dateDebut between ? and ? and vehicules_idVehic = ? ;";//2
	private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_PARTIE2_MODIFIER = "select * from indisponibilites where dateDebut between ? and ? and vehicules_idVehic = ? and idIndis !=? ;"; // 2 modifier 
	private final static String SELECT_RESERVATION_BY_DATEDEBUTFIN_INDISPONIBILITE_AND_IDVEHIC = "select * from reservations where FORMAT(dateHeureRes,'d') between ? and ? and vehicules_idVehic =? ;" ;
	private final static String SELECT_RESERVATION_BY_DATEDEBUTFIN_INDISPONIBILITE_AND_IDVEHIC_PARTIE2 ="select * from reservations where ? between FORMAT(dateHeureRes,'d') and dateHeureFin and vehicules_idVehic = ? ;";
	//méthode qui permet d'insérer une indisponibilité dans la base de données 
	@Override
	public int insertIndisponibilie(Indisponibilite indisponibilite) throws BusinessException 
	{
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(INSERT_INDISPONIBILITE,PreparedStatement.RETURN_GENERATED_KEYS);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date (indisponibilite.getDateDebut().getTime()));
			stmt.setDate(2,new java.sql.Date(indisponibilite.getDateFin().getTime()));
			stmt.setString(3,indisponibilite.getMotifIndisponibilite());
			stmt.setInt(4,indisponibilite.getVehicule().getIdVehic());
			
			//envoyer la requête 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt(1));
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , l'insertion de la indisponibilité n'a pas été effectuée avec succès. ");
			throw exception;
		}
		return indisponibilite.getIdIndis();
	}


	//méthode pour récupérer toutes les indisponibilit
	@Override
	public List<Indisponibilite> selectToutesLesIndisponibilites() throws BusinessException 
	{
		List<Indisponibilite> listeIndisponibilites = new ArrayList<Indisponibilite>();
		
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_TOUTES_LES_INDISPONIBILITES);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
		     while(rs.next()) 
		     {
		    	 Indisponibilite indisponibilite = new Indisponibilite();
		    	 indisponibilite.setIdIndis(rs.getInt("idIndis"));
		    	 indisponibilite.setDateDebut(rs.getDate("dateDebut"));
		    	 indisponibilite.setDateFin(rs.getDate("dateFin"));
		    	 indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
		    	 Campus campus = new Campus();
		    	 campus.setIdCampus(rs.getInt("idCampus"));
		    	 campus.setLibelle(rs.getString("libelle"));
		    	 Vehicule vehicule = new Vehicule();
		    	 vehicule.setIdVehic(rs.getInt("idVehic"));
		    	 vehicule.setDesignation(rs.getString("designation"));
		    	 vehicule.setImmatriculation(rs.getString("immatriculation"));
		    	 vehicule.setDateAchat(new java.sql.Date(rs.getDate("dateAchat").getTime()));
		    	 vehicule.setCampus(campus);
		    	 indisponibilite.setVehicule(vehicule);
		    	 listeIndisponibilites.add(indisponibilite);
		    	 
		    	 
		     }
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune indisponibilité existe dans la base de données.");
			throw exception;
		}
		return listeIndisponibilites;
	}


	//méthode pour récupérer une indisponibilité par dateDebut et idVehic
	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutAndIdVehic(Date dateDebut, int id) throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		 Campus campus = new Campus();
		 Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUT_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setInt(2, id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
		     while(rs.next()) 
		     {
		    	 
		    	 indisponibilite.setIdIndis(rs.getInt("idIndis"));
		    	 indisponibilite.setDateDebut(rs.getDate("dateDebut"));
		    	 indisponibilite.setDateFin(rs.getDate("dateFin"));
		    	 indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
		    	
		    	 campus.setIdCampus(rs.getInt("idCampus"));
		    	 campus.setLibelle(rs.getString("libelle"));
		    	
		    	 vehicule.setIdVehic(rs.getInt("idVehic"));
		    	 vehicule.setDesignation(rs.getString("designation"));
		    	 vehicule.setImmatriculation(rs.getString("immatriculation"));
		    	 vehicule.setDateAchat(rs.getDate("dateAchat"));
		    	 vehicule.setCampus(campus);
		    	 indisponibilite.setVehicule(vehicule);
		    	
		    	 
		    	 
		     }
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune indisponibilité existe dans la base de données pour cette date et pour ce id.");
			throw exception;
		}
	
		return indisponibilite;
	}


	//méthode pour modifier une indisponibilité 
	@Override
	public void modifierIndisponibilite(Indisponibilite indisponibilite, int id) throws BusinessException 
	{
	
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparer la requête 
			PreparedStatement stmt = cnx.prepareStatement(MODIFIER_INDISPONIBILITE);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(indisponibilite.getDateDebut().getTime()));
			stmt.setDate(2,new java.sql.Date(indisponibilite.getDateFin().getTime()));
			stmt.setString(3,indisponibilite.getMotifIndisponibilite());
			stmt.setInt(4,indisponibilite.getVehicule().getIdVehic());
			stmt.setInt(5,id);
			
			//envoyer la requête 
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , la modification de l'indisponibilité n'a pas été effectuée avec succès. ");
			throw exception ;
		}
	}


	//méthode pour récupérer une indisponibilité par son id 
	@Override
	public Indisponibilite selectIndisponibiliteById(int id) throws BusinessException 
	{
		 Indisponibilite indisponibilite = new Indisponibilite();
		 Campus campus = new Campus();
		 Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_ID);
			//paramétrer la requête 
			stmt.setInt(1,id);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
		     while(rs.next()) 
		     {
		    
		    	 indisponibilite.setIdIndis(rs.getInt("idIndis"));
		    	 indisponibilite.setDateDebut(rs.getDate("dateDebut"));
		    	 indisponibilite.setDateFin(rs.getDate("dateFin"));
		    	 indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
		    	 
		    	 campus.setIdCampus(rs.getInt("idCampus"));
		    	 campus.setLibelle(rs.getString("libelle"));
		    	 
		    	 vehicule.setIdVehic(rs.getInt("idVehic"));
		    	 vehicule.setDesignation(rs.getString("designation"));
		    	 vehicule.setImmatriculation(rs.getString("immatriculation"));
		    	 vehicule.setDateAchat(new java.sql.Date(rs.getDate("dateAchat").getTime()));
		    	 vehicule.setCampus(campus);
		    	 indisponibilite.setVehicule(vehicule);
		    	 }
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune indisponibilite existe dans la base de données pour cet id .");
			throw exception;
		}
		return indisponibilite;
	}


	//méthode pour récupérer une indisponibilité par dateDebut et idVehic quand on modifie une indisponibilité
	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutAndIdVehicModifier(Date dateDebut, int idVehic, int idIndis)
			throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		 Campus campus = new Campus();
		 Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUT_AND_IDVEHIC_MODIFIER);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setInt(2, idVehic);
			stmt.setInt(3,idIndis);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			
		     while(rs.next()) 
		     {
		    	 
		    	 indisponibilite.setIdIndis(rs.getInt("idIndis"));
		    	 indisponibilite.setDateDebut(rs.getDate("dateDebut"));
		    	 indisponibilite.setDateFin(rs.getDate("dateFin"));
		    	 indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
		    	
		    	 campus.setIdCampus(rs.getInt("idCampus"));
		    	 campus.setLibelle(rs.getString("libelle"));
		    	
		    	 vehicule.setIdVehic(rs.getInt("idVehic"));
		    	 vehicule.setDesignation(rs.getString("designation"));
		    	 vehicule.setImmatriculation(rs.getString("immatriculation"));
		    	 vehicule.setDateAchat(rs.getDate("dateAchat"));
		    	 vehicule.setCampus(campus);
		    	 indisponibilite.setVehicule(vehicule);
		    	
		    	 
		    	 
		     }
		} catch (SQLException e) {
			
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération, ou aucune indisponibilité existe dans la base de données pour cette date et pour cet id et pour la condition de l'id de l'indisponibilité.");
			throw exception;
		}
		
		return indisponibilite;
	}


	//méthode pour supprimer une indisponibilité 
	@Override
	public void deleteIndisponibilite(int id) throws BusinessException
	{
	    try(Connection cnx = ConnectionProvider.getConnection())
	    {
	    	//préparation de la requête 
	    	PreparedStatement stmt = cnx.prepareStatement(DELETE_INDISPONIBILITE);
	    	//paramétrer la requête 
	    	stmt.setInt(1, id);
	    	//envoyer la requête 
	    	stmt.executeUpdate();
	    	
	    } catch (SQLException e)
	    {
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , la suppression n'a pas été effectuée avec succès. ");
			throw exception;
		}	
	}


	// 1 méthode qui fait appel au DAL pour récupérer une indisponibilité par rapport à la date de debut et la date de fin et l'id du véhicule 
	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehic(Date dateDebut, int idVehic)
			throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
		
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setInt(2,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setDateDebut(rs.getDate("dateDebut"));
				indisponibilite.setDateFin(rs.getDate("dateFin"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motifIndisponibilite"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				indisponibilite.setVehicule(vehicule);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune indisponibilité existe pour les dates et pour l'id du véhicule choisi. ");
			throw exception;
		}
		return indisponibilite;
	}


	// 1 modifier - méthode qui fait appel au DAL pour récupérer une indisponibilité par rapport à la date de debut et la date de fin et l'id du véhicule quand on la modifie  

	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicModifier(Date dateDebut,int idVehic, int idIndis) throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
		
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_MODIFIER);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setInt(2,idVehic);
			stmt.setInt(3,idIndis);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setDateDebut(rs.getDate("dateDebut"));
				indisponibilite.setDateFin(rs.getDate("dateFin"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motifIndisponibilite"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				indisponibilite.setVehicule(vehicule);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune indisponibilité existe pour les dates et pour l'id du véhicule choisi et pour l'id de l'indisponibilité. ");
			throw exception;
		}

		return indisponibilite;
	}


	// 2 méthode qui fait appel au DAL pour vérifier si la période de l'indisponibilité contient une autre indisponibilité pour la même véhicule 

	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicPartie2(Date dateDebut, Date dateFin,
			int idVehic) throws BusinessException
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
		
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_PARTIE2);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(3,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setDateDebut(rs.getDate("dateDebut"));
				indisponibilite.setDateFin(rs.getDate("dateFin"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motifIndisponibilite"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				indisponibilite.setVehicule(vehicule);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune indisponibilité existe pour les dates et pour l'id du véhicule choisi. ");
			throw exception;
		}

		return indisponibilite;
	}


	// 2 modifier - méthode qui fait appel au DAL pour vérifier si la période de l'indisponibilité contient une autre indisponibilité pour la même véhicule 

	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicPartie2Modifier(Date dateDebut, Date dateFin,
			int idVehic, int idIndis) throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
		
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTFIN_AND_IDVEHIC_PARTIE2_MODIFIER);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(3,idVehic);
			stmt.setInt(4,idIndis);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setDateDebut(rs.getDate("dateDebut"));
				indisponibilite.setDateFin(rs.getDate("dateFin"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motifIndisponibilite"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				indisponibilite.setVehicule(vehicule);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ou aucune indisponibilité existe selon les dates et l'id du véhicule choisi. ");
			throw exception;
		}


		return indisponibilite;
	}


	//méthode pour récupérer les réservation par rapport des date début fin de la disponibilité pour un véhicule 
	@Override
	public Reservation selectReservationByDateDebutFinIndisponibiliteAndIdvehic(Date dateDebut, Date dateFin,
			int idVehic) throws BusinessException 
	{
	
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUTFIN_INDISPONIBILITE_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(3,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureRes1((rs.getTimestamp("dateHeureRes").toLocalDateTime()));
				reservation.setDateHeureFin1((rs.getTimestamp("dateHeureFin").toLocalDateTime()));
				reservation.setMotif(rs.getString("motif"));
				destination.setIdDes(rs.getInt("destinations_idDes"));
				personne.setIdPerso(rs.getInt("personnes_idPerso"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				reservation.setDestination(destination);
				reservation.setPersonne(personne);
				reservation.setVehicule(vehicule);
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon les dates d'indisponibilité choisie et l'id du véhicule. ");
			throw exception;
		}
		return reservation;
	}


	//méthode pour récupérer les réservation par rapport la date début  de la disponibilité pour un véhicule 

	@Override
	public Reservation selectReservationByDateDebutFinIndisponibiliteAndIdvehicPartie2(Date dateDebut, int idVehic)
			throws BusinessException
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUTFIN_INDISPONIBILITE_AND_IDVEHIC_PARTIE2);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateDebut.getTime()));
			stmt.setInt(2,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureRes1((rs.getTimestamp("dateHeureRes").toLocalDateTime()));
				reservation.setDateHeureFin1((rs.getTimestamp("dateHeureFin").toLocalDateTime()));
				reservation.setMotif(rs.getString("motif"));
				destination.setIdDes(rs.getInt("destinations_idDes"));
				personne.setIdPerso(rs.getInt("personnes_idPerso"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				reservation.setDestination(destination);
				reservation.setPersonne(personne);
				reservation.setVehicule(vehicule);
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon la date de Debut de l'indisponibilité choisie et l'id du véhicule. ");
			throw exception;
		}
		return reservation;
	}

}

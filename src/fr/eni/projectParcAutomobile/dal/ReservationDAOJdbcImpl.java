package fr.eni.projectParcAutomobile.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import javafx.util.converter.LocalDateTimeStringConverter;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;
import sun.security.krb5.internal.crypto.Des;

public class ReservationDAOJdbcImpl implements ReservationDAO
{
	private final static String INSERT_RESERVATION = "INSERT INTO reservations (dateHeureRes ,dateHeureFin,motif,destinations_idDes,personnes_idPerso,vehicules_idVehic) values (? , ?,?,?,?,?) ;"; 
    private final static String SELECT_INDISPONIBILITE_BY_DATERES_AND_IDVEHIC =          "select i.idIndis as idIndis ,i.motifIndisponibilite as motif, v.designation as designation ,v.idVehic as idVehic, v.immat as immatriculation  from indisponibilites i join vehicules v  on i.vehicules_idVehic = v.idVehic where  ?  between i.dateDebut and   i.dateFin or ? between i.dateDebut and   i.dateFin  and v.idVehic= ?";
	private final static String SELECT_INDISPONIBILITE_BY_DATERES_AND_IDVEHIC_VERSION2 = "select i.idIndis as idIndis ,i.motifIndisponibilite as motif, v.designation as designation ,v.idVehic as idVehic, v.immat as immatriculation  from indisponibilites i join vehicules v  on i.vehicules_idVehic = v.idVehic where i.dateDebut between ? and ? or i.dateFin between ? and ? and v.idVehic = ? ;";
    private final static String SELECT_RESERVATION_BY_DATERES_AND_IDVEHIC = "select * from reservations where (?>= dateHeureRes and ?<dateHeureFin) or (? >dateHeureRes and ?<= dateHeureFin) and vehicules_idVehic =? ;";
    private final static String SELECT_RESERVATION_BY_DATERES_AND_IDVEHIC_VERSION2 = "select * from reservations where (dateHeureRes>= ? and dateHeureRes < ?) or (dateHeureFin > ? and dateHeureFin<=?) and vehicules_idVehic =? ;";
	
    
    private final static String SELECT_RESERVATION_BY_DATEDEBUTFIN_AND_IDVEHIC =" select * from reservations where dateHeureRes >= ? and dateHeureRes < ? and vehicules_idVehic = ?;";
    private final static String SELECT_RESERVATION_BY_DATEDEBUT_AND_IDVEHIC = " select * from reservations where ? >= dateHeureRes and ? < dateHeureFin and vehicules_idVehic = ? ;" ; 
    private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTRES_ANDIDVEHIC = " select * from indisponibilites where ? between dateDebut and dateFin and vehicules_idVehic = ? ;";
    private final static String SELECT_INDISPONIBILITE_BY_DATEDEBUTDATEFINRES_ANDIDVEHIC = " select* from indisponibilites where dateDebut between ? and ? and vehicules_idVehic = ? ;";
    private final static String SELECT_RESERVATION_BY_DATEDEBUTFIN_AND_IDVEHIC_MODIFIER = " select * from reservations where dateHeureRes> =? and dateHeureRes  < ? and vehicules_idVehic = ? and idRes !=? ;" ;
    private final static String SELECT_RESERVATION_BY_DATEDEBUT_AND_IDVEHIC_MODIFIER = "select * from reservations where ? >= dateHeureRes and ? < dateHeureFin and vehicules_idVehic = ?  and idRes != ? ;";
   
    private final static String SELECT_RESERVATION_BYDATDEBUTRES_AND_IDPERSO = " select * from reservations where  ?  >= dateHeureRes and ? < dateHeureFin and personnes_idPerso =? ;" ;
    private final static String SELECT_RESERVATION_BYDATDEBUTFINRES_AND_IDOERSO = " select * from reservations where dateHeureRes >= ? and dateHeureRes < ? and personnes_idPerso = ?;" ;
    private final static String SELECT_RESERVATION_BY_MOIS = " DECLARE @DATE DATETIME;  " + 
    		                                                 " SET @DATE = ?           ;" + 
    		                                                 " DECLARE @DATE2 DATETIME ;" + 
    		                                                 " DECLARE @num_minutes int;" + 
    		                                                 " SET @num_minutes = ? ;" + 
    		                                                 " set @DATE2 = DATEADD( MINUTE ,@num_minutes ,DATEADD(dd, -DAY(DATEADD(m,1,@DATE)), DATEADD(m,1,@DATE)));" + 
    		                                                 " select r.idRes , r.dateHeureRes , r.dateHeureFin ,r.motif,v.designation , v.immat as immatriculation ,v.dateAchat,p.nom, p.prenom,p.nomStructure,p.email,p.motDePasse,p.administrateur,d.libelle as destinationLibelle,d.codeDes,c.Libelle as campusLibelle from reservations r join vehicules v on r.vehicules_idVehic = v.idVehic  join campus c on v.campus_idCampus = c.idCampus join personnes p  on r.personnes_idPerso = p.idPerso join destinations d on r.destinations_idDes = d.idDes where r.dateHeureRes >= @DATE - DAY(@DATE)+1  and  r.dateHeureRes <= @DATE2 order by v.designation,r.dateHeureRes asc ;" ;
    
    private final static String SELECT_RESERVATION_BY_IDRES = "select * from reservations where idRes =?;";
    private final static String MODIFIER_RESERVATION = "UPDATE reservations set dateHeureRes = ? , dateHeureFin =? , motif = ? , destinations_idDes = ? , personnes_idPerso = ? , vehicules_idVehic = ? where idRes = ?;";
    private final static String SELECT_RESERVATION_BYDATDEBUTRES_AND_IDPERSO_MODIFIER = " select * from reservations where  ? >=  dateHeureRes and ? < dateHeureFin and personnes_idPerso = ?  and idRes != ? ;" ;
    private final static String SELECT_RESERVATION_BYDATDEBUTFINRES_AND_IDOERSO_MODIFIER = " select * from reservations where dateHeureRes >= ? and dateHeureRes < ? and personnes_idPerso = ? and idRes != ? ;";  
    private final static String SUPPRIMER_RESERVATION = "delete from reservations where idRes = ?;";
    private final static String SELECT_RESERVATION_PAR_NOM_PRENOM_PERSONNE = "select r.idRes , r.dateHeureRes , r.dateHeureFin ,r.motif,v.designation , v.immat as immatriculation ,v.dateAchat,p.nom, p.prenom,p.nomStructure,p.email,p.motDePasse,p.administrateur,d.libelle as destinationLibelle,d.codeDes,c.Libelle as campusLibelle from reservations r join vehicules v on r.vehicules_idVehic = v.idVehic  join campus c on v.campus_idCampus = c.idCampus join personnes p  on r.personnes_idPerso = p.idPerso join destinations d on r.destinations_idDes = d.idDes where p.nom like ? and p.prenom like ? ;";
    //méthode pour insérer une réservation à la base de données 
	@Override
	public int insertReservation(Reservation reservation) throws BusinessException
	{

		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(INSERT_RESERVATION,PreparedStatement.RETURN_GENERATED_KEYS);
			//pramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(reservation.getDateHeureRes1()));
			stmt.setTimestamp(2,Timestamp.valueOf(reservation.getDateHeureFin1()));
			stmt.setString(3,reservation.getMotif());
			stmt.setInt(4,reservation.getDestination().getIdDes());
			stmt.setInt(5,reservation.getPersonne().getIdPerso());
		//	if(reservation.getVehicule().getIdVehic()==0) 
			//{
			//	stmt.setNull(6,reservation.getVehicule().getIdVehic());
			//}
		//	else {
			stmt.setInt(6, reservation.getVehicule().getIdVehic());
			   //  }
			
		
			
			//envoyer la requête 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next()) 
			{
				reservation.setIdRes(rs.getInt(1));
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , l'insertion de la Réservation n'a pas été effectuée avec succès. ");
			throw exception;
		}
		return reservation.getIdRes();
	}

	//méthode pour récupérer une indisponibilité par la date début et la date de fin d'une réservation et par l'id d'un véhicule 
	@Override
	public Indisponibilite selectindisponibiliteByDateResAndIdVehic(Date dateRes, Date dateFin, int idVehic)
			throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite(); 
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATERES_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(3,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				indisponibilite.setVehicule(vehicule);
				
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune disponibilité existe selon les dates choisies et l'id du véhicule. ");
			throw exception;
		}
		return indisponibilite;
	}

	//méthode pour récupérer une indisponibilité par la date début et la date de fin d'une réservation et par l'id d'un véhicule Version2 
	@Override
	public Indisponibilite selectindisponibiliteByDateResAndIdVehicVersion2(Date dateRes, Date dateFin, int idVehic)
			throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite(); 
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATERES_AND_IDVEHIC_VERSION2);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setDate(3,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(3,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(5,idVehic);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				indisponibilite.setIdIndis(rs.getInt("idIndis"));
				indisponibilite.setMotifIndisponibilite(rs.getString("motif"));
				vehicule.setIdVehic(rs.getInt("idVehic"));
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				indisponibilite.setVehicule(vehicule);
				
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune disponibilité existe selon les dates choisies et l'id du véhicule. ");
			throw exception;
		}
		return indisponibilite;
	}

	//méthode pour récupérer une réservation par rapport à la période d'une réservation 
	@Override
	public Reservation selectReservationByDateResAndIdVehic(Date dateRes, Date dateFin, int idVehic)
			throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Vehicule vehicule = new Vehicule();
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparatiob de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATERES_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(2,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(3,new java.sql.Date(dateFin.getTime()));
			stmt.setDate(4,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(5,idVehic);
			
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				reservation.setDateHeureRes(rs.getDate("dateHeureRes"));
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureFin(rs.getDate("dateHeureFin"));
				reservation.setMotif(rs.getString("motif"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				personne.setIdPerso(rs.getInt("personnes_idPerso"));
				destination.setIdDes(rs.getInt("destinations_idDes"));
				reservation.setPersonne(personne);
				reservation.setVehicule(vehicule);
				reservation.setDestination(destination);
			}

		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune réservation existe pour les dates et l'id du véhicule utilisés");
			throw exception;
		}
		return reservation;
	}

	@Override
	public Reservation selectReservationByDateResAndIdVehicVersion2(Date dateRes, Date dateFin, int idVehic)
			throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Vehicule vehicule = new Vehicule();
		Personne personne = new Personne();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparatiob de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATERES_AND_IDVEHIC_VERSION2);
			//paramétrer la requête 
			stmt.setDate(1,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(2,new java.sql.Date(dateFin.getTime()));
			stmt.setDate(3,new java.sql.Date(dateRes.getTime()));
			stmt.setDate(4,new java.sql.Date(dateFin.getTime()));
			stmt.setInt(5,idVehic);
			
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				reservation.setDateHeureRes(rs.getDate("dateHeureRes"));
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureFin(rs.getDate("dateHeureFin"));
				reservation.setMotif(rs.getString("motif"));
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				personne.setIdPerso(rs.getInt("personnes_idPerso"));
				destination.setIdDes(rs.getInt("destinations_idDes"));
				reservation.setPersonne(personne);
				reservation.setVehicule(vehicule);
				reservation.setDestination(destination);
			}

		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune réservation existe pour les dates et l'id du véhicule utilisés");
			throw exception;
		}
		return reservation;
	}

	//méthode pour récuperer réservation selon les dateHeureDebut reservation et dateHeurFin reservation et l'id d'un véhicule 
	@Override
	public Reservation selectReservationParDateDebutFinAndIdVehic(LocalDateTime dateHeureRes, LocalDateTime dateHeureFin, int idVehic)
			throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUTFIN_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2, Timestamp.valueOf(dateHeureFin));;
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon les dates de réservation à ajouter et l'id du véhicule. ");
			throw exception;
		}

		return reservation;
	}

	//méthode pour récuperer réservation selon la dateHeureDebut reservation  et l'id d'un véhicule 

	@Override
	public Reservation selectReservationParDateDebutAndIdVehic(LocalDateTime dateHeureRes, int idVehic)
			throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUT_AND_IDVEHIC);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2,Timestamp.valueOf(dateHeureRes));
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon la dateDebut de la réservatoion choisie et l'id du véhicule. ");
			throw exception;
		}
		return reservation;
	}

	//méthode pour récupérer indisponibilté par rapport à la date de réservation à ajouter et l'id du véhicule 
	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutResAndIdVehic(LocalDateTime dateHeureRes, int idVehic)
			throws BusinessException
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparartion de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTRES_ANDIDVEHIC);
			//pramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
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
			BusinessException exception = new BusinessException(" problème pendant l'opération ou aucune indisponibilié existe par rapport à la date de la Réservatoion et l'id du véhicule  ");
			throw exception;
		}
		return indisponibilite;
	}

	//méthode pour récupérer indisponibilté par rapport à la date Début et Date Fin  de réservation à ajouter et l'id du vééhicule 

	@Override
	public Indisponibilite selectIndisponibiliteByDateDebutDateFinResAndIdVehic(LocalDateTime dateHeureRes,
			LocalDateTime dateHeureFin, int idVehic) throws BusinessException 
	{
		Indisponibilite indisponibilite = new Indisponibilite();
		Vehicule vehicule = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparartion de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_INDISPONIBILITE_BY_DATEDEBUTDATEFINRES_ANDIDVEHIC);
			//pramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2,Timestamp.valueOf(dateHeureFin));
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
			BusinessException exception = new BusinessException(" problème pendant l'opération ou aucune indisponibilié existe par rapport à la date de Debut et de la date de Fin de la  Réservatoion et l'id du véhicule  ");
			throw exception;
		}
		return indisponibilite;
	}

	//méthode pour récuperer réservation selon les dateHeureDebut reservation et dateHeurFin reservation et l'id d'un véhicule quand on modifie une Réservation 

	@Override
	public Reservation selectReservationParDateDebutFinAndIdVehicModifier(LocalDateTime dateHeureRes,
			LocalDateTime dateHeureFin, int idVehic, int idRes) throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUTFIN_AND_IDVEHIC_MODIFIER);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2, Timestamp.valueOf(dateHeureFin));;
			stmt.setInt(3,idVehic);
			stmt.setInt(4,idRes);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon les dates de réservation à ajouter et l'id du véhicule en modifiant une réservation. ");
			throw exception;
		}
		return reservation;
	}

	//méthode pour récupérer indisponibilté par rapport à la date de réservation à modifier et l'id du véhicule 
	@Override
	public Reservation selectReservationParDateDebutAndIdVehicModifier(LocalDateTime dateHeureRes, int idVehic,
			int idRes) throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_DATEDEBUT_AND_IDVEHIC_MODIFIER);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2,Timestamp.valueOf(dateHeureRes));
			stmt.setInt(3,idVehic);
			stmt.setInt(4,idRes);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe pour la date de Debut de la réservatoion choisie et l'id du véhicule en modifiant une réservation. ");
			throw exception;
		}
		return reservation;
	}

	//méthode pour récuperer réservation selon la dateHeureDebut reservation  et l'id d'une Personne

	@Override
	public Reservation selectReservationByDateHeurDebutResAndIdPerso(LocalDateTime dateHeureRes, int idPerso)
			throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BYDATDEBUTRES_AND_IDPERSO);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2,Timestamp.valueOf(dateHeureRes));
			stmt.setInt(3,idPerso);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon la datede Debut de la réservatoion choisie et l'id de personne. ");
			throw exception;
		}
		return reservation;
	}

	//méthode pour récuperer réservation selon les dateHeureDebut reservation et dateHeurFin reservation et l'id d'une personne 

	@Override
	public Reservation selectReservationByDateHeureDebutFinReAndIdPerso(LocalDateTime dateHeureRes,
			LocalDateTime dateHeureFin, int idPerso) throws BusinessException 
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BYDATDEBUTFINRES_AND_IDOERSO);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2, Timestamp.valueOf(dateHeureFin));;
			stmt.setInt(3,idPerso);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe selon les dates de réservation à ajouter et l'id de personne. ");
			throw exception;
		}

		return reservation;
	}

	//méthode pour récupérer les Réservation par mois choisi 
	@Override
	public List<Reservation> selectResevationsParMois(Date dateDeJour )
			throws BusinessException
	{
		List<Reservation> listeReservations = new ArrayList<Reservation>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la reuête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_MOIS);
			//pramétrer la requête 
			//stmt.setTimestamp(1,Timestamp.valueOf(dateDebutMois));
			//stmt.setTimestamp(2,Timestamp.valueOf(dateFinMois));
			stmt.setDate(1,new java.sql.Date(dateDeJour.getTime()));
            stmt.setInt(2,1439);
			// envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				Reservation reservation = new Reservation() ;
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureRes1(rs.getTimestamp("dateHeureRes").toLocalDateTime());
				reservation.setDateHeureFin1(rs.getTimestamp("dateHeureFin").toLocalDateTime());
				reservation.setMotif(rs.getString("motif"));
				
				Vehicule vehicule = new Vehicule();
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				
				
				Personne personne = new Personne();
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
				reservation.setPersonne(personne);
				
				Destination destination = new Destination();
				destination.setLibelle(rs.getString("destinationLibelle"));
				destination.setCodeDes(rs.getString("codeDes"));
				reservation.setDestination(destination);
				
				Campus campus = new Campus();
				campus.setLibelle(rs.getString("campusLibelle"));
				vehicule.setCampus(campus);
				reservation.setVehicule(vehicule);
				listeReservations.add(reservation);
				
				
				
				
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException(" problème pendant l'opération ,ou aucune Réservation existe pour le mois choisi. ");
			throw exception;
		}
		
		return listeReservations;
	}

	
	//méthode pour récupérer une Réservation par son id 
	@Override
	public Reservation selectReservationByIdRes(int idRes) throws BusinessException
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne       = new Personne();
		Vehicule vehicule       = new Vehicule();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BY_IDRES);
			//paramétrer la requête 
			stmt.setInt(1,idRes);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureRes1(rs.getTimestamp("dateHeureRes").toLocalDateTime());
				reservation.setDateHeureFin1(rs.getTimestamp("dateHeureFin").toLocalDateTime());
				reservation.setMotif(rs.getString("motif"));
				destination.setIdDes(rs.getInt("destinations_idDes"));
				reservation.setDestination(destination);
				personne.setIdPerso(rs.getInt("personnes_idPerso"));
				reservation.setPersonne(personne);
				vehicule.setIdVehic(rs.getInt("vehicules_idVehic"));
				reservation.setVehicule(vehicule);
				
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException(" problème pendant l'opération ou aucune Réservation existe pour l'id utilisé. ");
			throw exception;
		}
		return reservation;
	}

	//méthode pour modifier une Réservation 
	@Override
	public void modifierReservation(Reservation reservation , int idRes) throws BusinessException
	{
        try(Connection cnx = ConnectionProvider.getConnection())
        {
        	//préparation de la requête 
        	PreparedStatement stmt = cnx.prepareStatement(MODIFIER_RESERVATION);
        	//paramétrer la requête 
        	stmt.setTimestamp(1,Timestamp.valueOf(reservation.getDateHeureRes1()));
        	stmt.setTimestamp(2,Timestamp.valueOf(reservation.getDateHeureFin1()));
        	stmt.setString(3,reservation.getMotif());
        	stmt.setInt(4,reservation.getDestination().getIdDes());
        	stmt.setInt(5,reservation.getPersonne().getIdPerso());
        	stmt.setInt(6,reservation.getVehicule().getIdVehic());
        	stmt.setInt(7,idRes);
        	
        	//envoyer la requête 
        	stmt.executeUpdate();
        } catch (SQLException e) 
        {
        
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération ,la modification n'a pas été effectuée avec succès. ") ;
        	throw exception;
		}		
	}

	@Override
	public Reservation selectReservationByDateHeurDebutResAndIdPersoModifier(LocalDateTime dateHeureRes, int idPerso,
			int idRes) throws BusinessException
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BYDATDEBUTRES_AND_IDPERSO_MODIFIER);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2,Timestamp.valueOf(dateHeureRes));
			stmt.setInt(3,idPerso);
			stmt.setInt(4,idRes);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe pour la date de Debut de la réservatoion choisie et l'id de personne et l'id de Réservation. ");
			throw exception;
		}

		return reservation;
	}

	//méthode pour récuperer réservation selon les dateHeureDebut reservation et dateHeurFin reservation et l'id d'une personne quand on modifie une Réservation 

	@Override
	public Reservation selectReservationByDateHeureDebutFinResAndIdPersoModifier(LocalDateTime dateHeureRes,
			LocalDateTime dateHeureFin, int idPerso, int idRes) throws BusinessException
	{
		Reservation reservation = new Reservation();
		Destination destination = new Destination();
		Personne personne = new Personne();
		Vehicule vehicule = new Vehicule();
		try( Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_BYDATDEBUTFINRES_AND_IDOERSO_MODIFIER);
			//paramétrer la requête 
			stmt.setTimestamp(1,Timestamp.valueOf(dateHeureRes));
			stmt.setTimestamp(2, Timestamp.valueOf(dateHeureFin));;
			stmt.setInt(3,idPerso);
			stmt.setInt(4,idRes);
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
			BusinessException exception = new BusinessException("problème pendant l'opération ,ou aucune Réservation existe pour les dates de réservation à ajouter et l'id de personne et l'id de la Réservation. ");
			throw exception;
		}

		return reservation;
	}

	//méthode pour Supprimer une Réservation 
	@Override
	public void supprimerReservation(int idRes) throws BusinessException 
	{
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SUPPRIMER_RESERVATION);
			//pramétrer la requête
			stmt.setInt(1,idRes);
			//envoyer la requête 
			stmt.executeUpdate();
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException(" problème pendant l'opération, la suppression n'a pas été effectué avec succès. ");
			throw exception;
		}
	}

	@Override
	public List<Reservation> selectListeReservationParPersonne(String nom, String prenom) throws BusinessException 
	{
		List<Reservation>listeReservatios = new ArrayList<Reservation>();
		try(Connection cnx = ConnectionProvider.getConnection())
		{
			//préparation de la requête 
			PreparedStatement stmt = cnx.prepareStatement(SELECT_RESERVATION_PAR_NOM_PRENOM_PERSONNE);
			//paramétrer la requête 
			stmt.setString(1,nom);
			stmt.setString(2,prenom);
			//envoyer la requête 
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) 
			{
				Reservation reservation = new Reservation();
				reservation.setIdRes(rs.getInt("idRes"));
				reservation.setDateHeureRes1(rs.getTimestamp("dateHeureRes").toLocalDateTime());
				reservation.setDateHeureFin1(rs.getTimestamp("dateHeureFin").toLocalDateTime());
				reservation.setMotif(rs.getString("motif"));
				
				Vehicule vehicule = new Vehicule();
				vehicule.setDesignation(rs.getString("designation"));
				vehicule.setImmatriculation(rs.getString("immatriculation"));
				vehicule.setDateAchat(rs.getDate("dateAchat"));
				
				Personne personne = new Personne() ;
				personne.setNom(rs.getString("nom"));
				personne.setPrenom(rs.getString("prenom"));
				personne.setNomStructure(rs.getString("nomStructure"));
				personne.setEmail(rs.getString("email"));
				personne.setMotDePasse(rs.getString("motDePasse"));
				personne.setAdministrateur(rs.getBoolean("administrateur"));
				
				Destination destination = new Destination();
				destination.setLibelle(rs.getString("destinationLibelle"));
				destination.setCodeDes(rs.getString("codeDes"));
				
				Campus campus = new Campus();
				campus.setLibelle(rs.getString("campusLibelle"));
				
				vehicule.setCampus(campus);
				reservation.setVehicule(vehicule);
				reservation.setDestination(destination);
				reservation.setPersonne(personne);
				listeReservatios.add(reservation);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			BusinessException exception = new BusinessException("problème pendant l'opération , ou aucune Réservation existe pour la personne choisie. ");
			throw exception;
		}
		return listeReservatios;
	}

}

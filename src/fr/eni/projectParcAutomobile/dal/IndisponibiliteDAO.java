package fr.eni.projectParcAutomobile.dal;

import java.util.Date;
import java.util.List;

import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

public interface IndisponibiliteDAO 
{

	public int insertIndisponibilie (Indisponibilite indisponibilite) throws BusinessException;
	public List<Indisponibilite> selectToutesLesIndisponibilites () throws BusinessException;
	public Indisponibilite selectIndisponibiliteByDateDebutAndIdVehic (Date dateDebut , int id)throws BusinessException;
	public void modifierIndisponibilite (Indisponibilite indisponibilite, int id )throws BusinessException;
	public Indisponibilite selectIndisponibiliteById (int id ) throws BusinessException;
	public Indisponibilite selectIndisponibiliteByDateDebutAndIdVehicModifier (Date dateDebut ,int idVehic ,int idIndis) throws BusinessException;
	public void deleteIndisponibilite (int id )throws BusinessException ;
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehic (Date dateDebut ,int idVehic ) throws BusinessException; //1
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicModifier (Date dateDebut  ,int idVehic ,int idIndis) throws BusinessException; //1 modifier 
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicPartie2(Date dateDebut ,Date dateFin ,int idVehic) throws BusinessException ;//2
	public Indisponibilite selectIndisponibiliteByDateDebutFinAndIdVehicPartie2Modifier (Date dateDebut ,Date dateFin ,int idVehic,int idIndis) throws BusinessException; // 2 modifier	
	public Reservation selectReservationByDateDebutFinIndisponibiliteAndIdvehic (Date dateDebut ,Date dateFin, int idVehic) throws BusinessException;
	public Reservation selectReservationByDateDebutFinIndisponibiliteAndIdvehicPartie2(Date dateDebut,int idVehic) throws BusinessException;

}

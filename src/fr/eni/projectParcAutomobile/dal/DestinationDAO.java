package fr.eni.projectParcAutomobile.dal;

import java.util.List;

import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public interface DestinationDAO 
{

	public int insertDestination (Destination destination) throws BusinessException;
	public Destination selectDestinationByLibelle (String libelle)throws BusinessException;
	public Destination selectDestinationByCodeDes (String codeDes)throws BusinessException;
	public List<Destination> selectlisteDestinations () throws BusinessException;
	public void modifierDestination (int id , Destination destination) throws BusinessException;
	public Destination selectDestinationById(int id) throws BusinessException;
	public void supprimerDestination (int id )throws BusinessException;
	public Destination selectDestinationByLibelleAndId (String libelle , int id)throws BusinessException;
	public Destination selectDestinationByCodeDesAndId (String codeDes , int id) throws BusinessException;
}

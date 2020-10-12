package fr.eni.projectParcAutomobile.dal;

import java.util.List;

import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public interface CampusDAO 
{

	public int insertCampus(Campus campus) throws BusinessException;
	public Campus selectCampusByLibelle (String libelle)throws BusinessException;
	public Campus selectCampusByLibelleAndId(String libelle,int id) throws BusinessException;
	public List<Campus> selectTousLesCampus() throws BusinessException;
	public void modifierCampus (String libelle , Campus campus) throws BusinessException;
	public void supprimerCampus (int idCampus) throws BusinessException;
}

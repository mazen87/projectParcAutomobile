package fr.eni.projectParcAutomobile.dal;

import java.util.List;

import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

public interface VehiculeDAO 
{
  public int insertVehicule (Vehicule vehicule) throws BusinessException;
  public Vehicule selectVehiculeByImmat (String immatriculation) throws BusinessException ; 
  public Vehicule selectVehiculeByImmatModifier (String immatriculation , int id ) throws BusinessException;
  public List<Vehicule> selectTousLesVehicules ()throws BusinessException;
  public Vehicule selectVehiculeById (int id)throws BusinessException;
  public void modifierVehicule(int id , Vehicule vehicule) throws BusinessException;
  public void supprimerVehicule (int id )throws BusinessException;
}

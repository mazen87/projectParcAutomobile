package fr.eni.projectParcAutomobile.dal;

import java.util.List;

import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

public interface PersonneDAO 
{

	public int insertPersonne (Personne personne) throws BusinessException ;
	public Personne selectPersonneByEmail (String email) throws BusinessException;
	public List<Personne> selectTousLesPersonne ()throws BusinessException;
	public void modifierPersonne (int id , Personne personne) throws BusinessException;
	public Personne selctPersonneById(int id) throws BusinessException ; 
	public void supprimerPersonne(int id)throws BusinessException;
	public Personne selectPersonneByEmailAvecId(String email,int id)throws BusinessException;
	public Personne selectPersonneByEmailAndIdConnexion (String email,String motDePasse)throws BusinessException;
	public Personne selectPersonneByNomAndPrenom (String nom , String prenom) throws BusinessException;
	public Personne selectPersonneByNomAndPrenomModifier(String nom,String prenom,int id) throws BusinessException;
}

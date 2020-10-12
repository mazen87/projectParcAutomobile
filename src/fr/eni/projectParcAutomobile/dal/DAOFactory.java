package fr.eni.projectParcAutomobile.dal;

public abstract class DAOFactory
{

	// méthode qui fait appel à l'implementation de campus 
	public static CampusDAO getCampusDAO() 
	{
		return new CampusDAOJdbcImpl();
	}
	
	// méthode qui fait appel à l'implementation de personne
	public static PersonneDAO getPersonneDAO()
	{
		return new PersonneDAOJdbcImpl();
	}
	// méthode qui fait appel à l'implementation de Destination
	public static DestinationDAO getDestinationDAO() 
	{
		return new DestinationDAOJdbcImpl();
	}
	// méthode qui fait appel à l'implementation de Véhicules
	public static VehiculeDAO getVehiculeDAO() 
	{
		return new VehiculeDAOJdbcImpl();
	}
	// méthode qui fait appel à l'implementation d'Indisponibilité
	public static  IndisponibiliteDAO getIndisponibiliteDAO() 
	{
		return new IndisponibiliteDAOJdbcImpl();
	}

	// méthode qui fait appel à l'implementation de Réservation 
	public static ReservationDAO getReservationDAO() 
	{
		return new ReservationDAOJdbcImpl();
	}
	

}

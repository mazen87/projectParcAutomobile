package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.DestinationManager;
import fr.eni.projectParcAutomobile.bll.PersonnesManager;
import fr.eni.projectParcAutomobile.bll.ReservationManager;
import fr.eni.projectParcAutomobile.bll.VehiculeManager;
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletModifierUneReservation
 */
@WebServlet("/ServletModifierUneReservation")
public class ServletModifierUneReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String idRes = null ;
	
    
	public String getIdRes() {
		return idRes;
	}

	public void setIdRes(String idRes) {
		this.idRes = idRes;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//récupérer l'id de l'objet Réservation à modifier 
		this.setIdRes(request.getParameter("id"));
		
		Reservation reservation = new Reservation();
		ReservationManager manager = new ReservationManager();
		
		try {
			reservation = manager.selctReservationByIdRes(Integer.parseInt(this.getIdRes()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("selectReservationByIdException",e.getMessage());
		}
		
		request.setAttribute("reservation",reservation);
		
		
		//récupérer toutes les personnes 
				List<Personne> listePersonnes = new ArrayList<Personne>();
				PersonnesManager personneManager = new PersonnesManager();
				try {
					listePersonnes = personneManager.selectTousLesPersonnes();
				} catch (BusinessException e) 
				{
					e.printStackTrace();
					request.setAttribute("selectToutesLesPersonnesException",e.getMessage());
				}
				
				//récupérer toutes les véhicule 
				List<Vehicule> listeVehicules = new ArrayList<Vehicule>();
				VehiculeManager vehiculeManager = new VehiculeManager();
				try {
					listeVehicules = vehiculeManager.selectTousLesVehicules();
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("selectToutesLesVehiculeException",e.getMessage());
				}
				
				
				//récupérer toutes les destination 
				List<Destination> listeDestination = new ArrayList<Destination>();
				DestinationManager destinationManager = new DestinationManager();
				try {
					listeDestination = destinationManager.selectToutesLesDestinations();
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("selectToutesLesDestinationException", e.getMessage());
				}
			
				request.setAttribute("listePersonnes", listePersonnes);
				request.setAttribute("listeDestination",listeDestination);
				request.setAttribute("listeVehicules", listeVehicules);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/modifierReservation.jsp");
	rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		//récupérer les données du formulaire 
		Reservation reservation = 	new Reservation() ; 
		Personne personne = new Personne() ; 
		Destination destination = new Destination();
		Vehicule vehicule = new Vehicule();
		reservation.setIdRes(Integer.parseInt(this.getIdRes()));
		reservation.setMotif(request.getParameter("motif"));
		 
		LocalDateTime dateHeureRes = null ;
		LocalDateTime dateHeureFin = null ; 
		try {
		 dateHeureRes = LocalDateTime.parse(request.getParameter("dateRes"));
		 dateHeureFin = LocalDateTime.parse(request.getParameter("dateFin"));
		}catch (Exception e) {
			String fmatDateException = "le format de date n'est pas valide , veuillez respectez le format indiqué dans la zone de saisie. ";
			request.setAttribute("fmatDateException", fmatDateException);
			doGet(request, response);
		}
		reservation.setDateHeureRes1(dateHeureRes);
		reservation.setDateHeureFin1(dateHeureFin);
		personne.setIdPerso(Integer.parseInt(request.getParameter("personne")));
		reservation.setPersonne(personne);
		destination.setIdDes(Integer.parseInt(request.getParameter("destination")));
		reservation.setDestination(destination);
		vehicule.setIdVehic(Integer.parseInt(request.getParameter("vehicule")));
		reservation.setVehicule(vehicule);
		
		ReservationManager manager = new ReservationManager();
		try {
			manager.modifierReservation(reservation,Integer.parseInt(this.getIdRes()));
		} catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} catch (BusinessException e) 
		{
			e.printStackTrace();
			request.setAttribute("modifierReservationException",e.getMessage());
		}
		String messageReussiteModifierReservation = " la modification a été effectuée avec succès. ";
		request.setAttribute("messageReussiteModifierReservation", messageReussiteModifierReservation);
		doGet(request, response);
	}

}

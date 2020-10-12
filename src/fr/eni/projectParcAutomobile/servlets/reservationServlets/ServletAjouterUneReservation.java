package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
 * Servlet implementation class ServletAjouterUneReservation
 */
@WebServlet("/ServletAjouterUneReservation")
public class ServletAjouterUneReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");

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
		
		//récupérer tous les véhicules 
		List<Vehicule> listeVehicules = new ArrayList<Vehicule>();
		VehiculeManager vehiculeManager = new VehiculeManager();
		try {
			listeVehicules = vehiculeManager.selectTousLesVehicules();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("selectToutesLesVehiculeException",e.getMessage());
		}
		
		
		//récupérer toutes les destinations 
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
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/ajouterReservation.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		
		ReservationManager manager = new ReservationManager();
		Reservation reservation = new Reservation();
		Personne personne       = new Personne();
		Vehicule vehicule       = new Vehicule();
		Destination destination = new Destination();
		//récupérer les donées saisies par l'utilisateur 
		String motif = request.getParameter("motif");
		String dateHeureResString = request.getParameter("dateRes");
		String dateHeureFinString = request.getParameter("dateFin");
		/* StringBuilder sb = new StringBuilder();
			sb.append(request.getParameter("dateRes"));
			sb.append(" ");
			sb.append(request.getParameter("heureRes"));
			String dateHeureResString  = sb.toString();
			
			StringBuilder sb1 = new StringBuilder();
			sb1.append(request.getParameter("dateFin"));
			sb1.append(" ");
			sb1.append(request.getParameter("heureFin"));
			String dateHeureFinString  = sb1.toString();*/
		String idPersoString = request.getParameter("personne");
		String idVehicString = request.getParameter("vehicule");
		String idDesString   = request.getParameter("destination");
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//Date dateHeureRes = null;
		//Date dateHeureFin = null;
		LocalDateTime dateHeureRes1 = null ;
		LocalDateTime dateHeureFin1 = null ;
		
		try {
		
		 dateHeureRes1 = LocalDateTime.parse(dateHeureResString);
		 dateHeureFin1 = LocalDateTime.parse(dateHeureFinString);
		}catch (Exception e) {
			String fmatDateException = "le format de date n'est pas valide , veuillez respecter le format indiqué dans la zone de saisie. ";
			request.setAttribute("fmatDateException", fmatDateException);
			doGet(request, response);
		}

		/*
		DateTimeFormatter  dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		try {
			dateHeureRes = sdf.parse(dateHeureResString);
			dateHeureFin = sdf.parse(dateHeureFinString);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}*/
		vehicule.setIdVehic(Integer.parseInt(idVehicString));
		reservation.setVehicule(vehicule);
		
		if(idPersoString.trim().equals("0")) 
		{
			personne = null ;
		}
		else
		{
			personne.setIdPerso(Integer.parseInt(idPersoString));
			
		}
		reservation.setPersonne(personne);
		
		if(idDesString.trim().equals("0")) 
		{
			destination = null ;
		}
		else 
		{
			destination.setIdDes(Integer.parseInt(idDesString));
		}
		reservation.setDestination(destination);
		//reservation.setDateHeureRes(dateHeureRes);
		//reservation.setDateHeureFin(dateHeureFin);
		reservation.setDateHeureRes1(dateHeureRes1);
		reservation.setDateHeureFin1(dateHeureFin1);
		reservation.setMotif(motif);
		
		//envoyer l'objet réservation à la base de données 
		try {
			manager.ajouterReservation(reservation);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("ajouterReservationException",e.getMessage());
			
		}
		String messageReussiteAjouterReservation = "  la réservation a été ajoutée avec succès. ";
		request.setAttribute("messageReussiteAjouterReservation", messageReussiteAjouterReservation);
		doGet(request, response);
	}

}

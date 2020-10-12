package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.ReservationManager;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.exception.BusinessException;



/**
 * Servlet implementation class ServletGestionDesReservationsAccueil
 */
@WebServlet("/ServletGestionDesReservationsAccueil")
public class ServletGestionDesReservationsAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//récupérer la date de jour 
		Date dateDeJour = new Date();
		Date dateChoisi = null ;
		Date dateDeJourParMois = (Date) request.getAttribute("dateDeJourParMois");
		String nom = (String) request.getAttribute("nom");
		String prenom = (String) request.getAttribute("prenom");
		if(nom != null && prenom != null) 
		{
			dateDeJour = null ;
			
			List<Reservation> listeReservationsPersonne = new ArrayList<Reservation>();
			ReservationManager manager = new ReservationManager();
			try {
				listeReservationsPersonne = manager.selectReservationsParNomPrenomPersonne(nom, prenom);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("selectReservationsParNomPrenomPersonne", e.getMessage());
			}
			request.setAttribute("listeReservations", listeReservationsPersonne);
			request.setAttribute("dateDeJour",dateDeJour);
			
		}
		else
		{
		
		if(request.getAttribute("dateDeJourParMois")!= null) 
		{
			request.setAttribute("dateDeJour", dateDeJourParMois);
			 dateChoisi = dateDeJourParMois;
		}
		else 
		{
			request.setAttribute("dateDeJour", dateDeJour);
			dateChoisi = dateDeJour;
			
		}
		ReservationManager manager = new ReservationManager();
		List<Reservation> listereservations = new ArrayList<Reservation>();
		try {
			listereservations = manager.selectlisteReservationsParMois(dateChoisi);
		} catch (BusinessException e)
		{
			e.printStackTrace();
			request.setAttribute("selectReservationsParMoisActuelException",e.getMessage());
		}
		request.setAttribute("listeReservations", listereservations);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/gestionDesReservationsAccueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

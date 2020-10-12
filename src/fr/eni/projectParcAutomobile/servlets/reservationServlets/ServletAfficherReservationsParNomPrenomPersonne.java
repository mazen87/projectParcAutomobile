package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class ServletAfficherReservationParNomPrenomPersonne
 */
@WebServlet("/ServletAfficherReservationsParNomPrenomPersonne")
public class ServletAfficherReservationsParNomPrenomPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String nom    = request.getParameter("nom").trim();
		String prenom = request.getParameter("prenom").trim();
		request.setAttribute("nom",nom);
		request.setAttribute("prenom",prenom);
		
		getServletContext().getRequestDispatcher("/ServletGestionDesReservationsAccueil").forward(request, response);

		
	}

}

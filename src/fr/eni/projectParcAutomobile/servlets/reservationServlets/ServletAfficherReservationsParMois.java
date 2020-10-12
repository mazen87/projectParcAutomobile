package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.ReservationManager;
import fr.eni.projectParcAutomobile.bo.Reservation;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletAfficherReservationsParMois
 */
@WebServlet("/ServletAfficherReservationsParMois")
public class ServletAfficherReservationsParMois extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Date ddj = new Date();
		System.out.println(ddj);
		
		StringBuilder sb = new StringBuilder();
		sb.append(request.getParameter("dateMois"));
		sb.append("-11");
		String dateMois = sb.toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateMoisDate = null;
		try {
			dateMoisDate = sdf.parse(dateMois);
			System.out.println(dateMois);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("dateDeJourParMois",dateMoisDate);
		
		getServletContext().getRequestDispatcher("/ServletGestionDesReservationsAccueil").forward(request, response);
         
		//doGet(request, response);
	}

}

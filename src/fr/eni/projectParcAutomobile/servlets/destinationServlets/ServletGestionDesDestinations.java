package fr.eni.projectParcAutomobile.servlets.destinationServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.DestinationManager;
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletGestionDesDestination
 */
@WebServlet("/ServletGestionDesDestinations")
public class ServletGestionDesDestinations extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		List<Destination> listeDestinations = new ArrayList<Destination>();
		DestinationManager manager = new DestinationManager();
		try {
			listeDestinations = manager.selectToutesLesDestinations();
		} catch (BusinessException e) {
	
			e.printStackTrace();
			request.setAttribute("selectToutesDestinationsException", e.getMessage());
		}
		request.setAttribute("listeDestinations", listeDestinations);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspDestination/gestionDesDestinations.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}

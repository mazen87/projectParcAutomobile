package fr.eni.projectParcAutomobile.servlets.reservationServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.ReservationManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimeUneReservation
 */
@WebServlet("/ServletSupprimerUneReservation")
public class ServletSupprimerUneReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String idRes = null ;
    
	public String getIdRes() {
		return idRes;
	}

	public void setIdRes(String idRes) {
		this.idRes = idRes;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//this.setIdRes(request.getParameter("id"));
		
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/supprimerReservation.jsp");
		//rd.forward(request, response);
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ReservationManager manager = new ReservationManager();
		String id = request.getParameter("id");
		 this.setIdRes(id);
		System.out.println(id);
	
		try {
			 
			manager.supprimerReservation(Integer.parseInt(id));
		
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("supprimerReservationException", e.getMessage());
		}
		String messageReussiteSupprimerReservation = " la suppression a été effectuée avec succès. ";
		request.setAttribute("messageReussiteSupprimerReservation", messageReussiteSupprimerReservation);

		getServletContext().getRequestDispatcher("/ServletGestionDesReservationsAccueil").forward(request, response);

		//doGet(request, response);
	}

}

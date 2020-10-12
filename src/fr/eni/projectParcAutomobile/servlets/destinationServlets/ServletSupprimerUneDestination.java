package fr.eni.projectParcAutomobile.servlets.destinationServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projectParcAutomobile.bll.DestinationManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimerUneDestination
 */
@WebServlet("/ServletSupprimerUneDestination")
public class ServletSupprimerUneDestination extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private String id = null ;
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspDestination/supprimerDestination.jsp");
		//rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String id = request.getParameter("id");
		this.setId(id);
		//appeller le manager pour appller le DAl afin de supprimer la destination 
		DestinationManager manager = new DestinationManager();
		try {
			manager.supprimerDestination(Integer.parseInt(this.getId()));
		} catch (NumberFormatException e) {
		e.printStackTrace();
		} catch (BusinessException e) {
		e.printStackTrace();
		request.setAttribute("supprimerDestinationException",e.getMessage());
		}
		String messageReussiteSupprimerDestination = "la suppression de la destination a été effectuée avec succès";
		request.setAttribute("messageReussiteSupprimerDestination", messageReussiteSupprimerDestination);
		//doGet(request, response);
		getServletContext().getRequestDispatcher("/ServletGestionDesDestinations").forward(request, response);

	}

}

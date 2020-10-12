package fr.eni.projectParcAutomobile.servlets.personneServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projectParcAutomobile.bll.PersonnesManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimerPersonne
 */
@WebServlet("/ServletSupprimerPersonne")
public class ServletSupprimerPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id = null;
	
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspPersonne/supprimerPersonne.jsp");
	    //rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		this.setId(request.getParameter("id"));
		PersonnesManager manager = new PersonnesManager();
		try {
			manager.supprimerPersonne(Integer.parseInt(this.getId()));
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			
		} catch (BusinessException e) {
	
			e.printStackTrace();
			request.setAttribute("messageExceptionSupprimer", e.getMessage());
		}
		String messageSuppressionSucces ="la Suppression a été effectuée avec succès";
		request.setAttribute("messageSuppressionSucces", messageSuppressionSucces);
		//doGet(request, response);
		getServletContext().getRequestDispatcher("/ServletGestionDesPersonnes").forward(request, response);

	}
	
}

package fr.eni.projectParcAutomobile.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import fr.eni.projectParcAutomobile.bll.CampusManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimeUnCampus
 */
@WebServlet("/ServletSupprimerUnCampus")
public class ServletSupprimerUnCampus extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id = null ;
	
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/supprimerUnCampus.jsp");
		//rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		this.setId(request.getParameter("id"));
		CampusManager manager = new CampusManager();
		try {
			
			manager.supprimerCampus(Integer.parseInt(this.getId()));
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("exceptionSupprimer", e.getMessage());
			
		}
		String msgReussoteSuppression = "la suppression a été effectuée avec succès";
		request.setAttribute("msgReussoteSuppression", msgReussoteSuppression);
		//doGet(request, response);
		getServletContext().getRequestDispatcher("/ServletGestionDesCampus").forward(request, response);

	}

}

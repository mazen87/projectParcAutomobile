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
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletModifierDestination
 */
@WebServlet("/ServletModifierUneDestination")
public class ServletModifierUneDestination extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id = null ;
       
  
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//récupérer l'id de la destination à modifier 
		//String id = request.getParameter("id");
		this.setId(request.getParameter("id"));
		//HttpSession session = request.getSession();
		//session.setAttribute("id",id);
		Destination destination = new Destination();
		// appeller manager pour accéder au DAL 
		DestinationManager manager = new DestinationManager();
	
		try {
			destination = manager.selectDestinationById(Integer.parseInt(this.getId()));
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("selctDestinationByIdException",e.getMessage());
		}
		request.setAttribute("destination", destination);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspDestination/modifierDestination.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//récupérer lid de la destination à modifier 
		//HttpSession session = request.getSession();
		//String id = (String) session.getAttribute("id");
		//récupérer les données saisie par l'utilisateur dans un objet de type destination
		Destination destination = new Destination();
		destination.setIdDes(Integer.parseInt(this.getId()));
		destination.setLibelle(request.getParameter("libelle"));
		destination.setCodeDes(request.getParameter("codeDes"));
		//appeller la manager pour accéder au DAL et envoyer les données à la base de données 
		DestinationManager manager = new DestinationManager();
        try 
        {
			manager.modifierDestination(Integer.parseInt(this.getId()), destination);
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("modifierDestinationException", e.getMessage());
		}
		String messageReussiteModifierDestination = "la modification à été effectuée avec succès ";
		request.setAttribute("messageReussiteModifierDestination", messageReussiteModifierDestination);
		doGet(request, response);
	
	}

}

package fr.eni.projectParcAutomobile.servlets.destinationServlets;

import java.io.IOException;

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
 * Servlet implementation class ServletAjouterUneDestination
 */
@WebServlet("/ServletAjouterUneDestination")
public class ServletAjouterUneDestination extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//vers la page ajouter destination 
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspDestination/ajouterDestination.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String libelle = request.getParameter("libelle");
		String codeDes = request.getParameter("codeDes");
		Destination destination = new Destination();
		destination.setLibelle(libelle);
		destination.setCodeDes(codeDes);
		//appller le mananger pour envoyer l'objet destination par DAl à la BDD 
		DestinationManager manager = new DestinationManager();
		try {
			manager.ajouterDestination(destination);
		} catch (BusinessException e) {
		
			e.printStackTrace();
			request.setAttribute("exceptionAjouterDestination", e.getMessage());
		}
		String messageReussiteAjouterDEstination = "la Destination a été ajoutée avec succès ";
		request.setAttribute("messageReussiteAjouterDEstination", messageReussiteAjouterDEstination);
		doGet(request, response);
	}

}

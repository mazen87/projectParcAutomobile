package fr.eni.projectParcAutomobile.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.CampusManager;
import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletAjouterUnCampus
 */
@WebServlet("/ServletAjouterUnCampus")
public class ServletAjouterUnCampus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajouterCampus.jsp");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		
		// récupération la libellé saisie par l'utilisateur
		String libelle = request.getParameter("libelle");
		Campus campus = new Campus();
		CampusManager manager = new CampusManager();
	//	try {
		//	campus = manager.selectCampusByLibelle(libelle);
		//} catch (BusinessException e1) {
			
			//e1.printStackTrace();
		    //request.setAttribute("exception",e1.getMessage());
		//}
		
		Campus campus2 = new Campus(libelle);
		try {
			manager.ajouterCampus(campus2);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("exceptionAjouter",e.getMessage() );
		}
		String msgReussiteAjouter = "l'opération a été exécutée avec succès ";
		request.setAttribute("msgReussiteAjouter", msgReussiteAjouter);
		//response.sendRedirect("ServletGestionDesCampus");
		//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gestionDesCampus.jsp");
		//rd.forward(request, response);
		doGet(request, response);
	}

}

package fr.eni.projectParcAutomobile.servlets.personneServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.PersonnesManager;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletGestionDesPersonnes
 */
@WebServlet("/ServletGestionDesPersonnes")
public class ServletGestionDesPersonnes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		List <Personne> listePersonnes = new ArrayList<Personne>();
		// appeller le prsonneManager pour récupérer tous les personnes depuis la base de données et les envoyer à la jsp 
		PersonnesManager manager = new PersonnesManager();
		try {
			listePersonnes = manager.selectTousLesPersonnes();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("exceptionselectPersonnes", e.getMessage());
		}
		//envoyer la liste des personnes vers l'affichage 
		request.setAttribute("listePersonnes", listePersonnes);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspPersonne/gestionDesPersonnes.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

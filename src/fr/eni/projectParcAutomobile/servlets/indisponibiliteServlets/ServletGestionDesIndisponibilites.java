package fr.eni.projectParcAutomobile.servlets.indisponibiliteServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.IndisponibiliteManager;
import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletGestionDesIndisponibilites
 */
@WebServlet("/ServletGestionDesIndisponibilites")
public class ServletGestionDesIndisponibilites extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		List<Indisponibilite> listeIndisponibilites = new ArrayList<>();
		//Appeller le manager afin de pouvoir accéder au DAL pour récupérer toutes les indisponibilités 
		IndisponibiliteManager manager = new IndisponibiliteManager();
		try {
			listeIndisponibilites = manager.selectToutesLesIndisponibilites();
		} catch (BusinessException e) {
		e.printStackTrace();
		request.setAttribute("selectToutesLesIndisponibilitesException", e.getMessage());
		}
		request.setAttribute("listeIndisponibilites", listeIndisponibilites);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspIndisponibilite/gestionDesIndisponibilites.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

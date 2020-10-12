package fr.eni.projectParcAutomobile.servlets.indisponibiliteServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.IndisponibiliteManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimerUneIndisponibilite
 */
@WebServlet("/ServletSupprimerUneIndisponibilite")
public class ServletSupprimerUneIndisponibilite extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String id = null ;
    
   
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//récupérer l'id de l'indisponibilité à supprimer 
		//this.setId(request.getParameter("id"));
	
	//RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspIndisponibilite/supprimerIndisponibilite.jsp");
	//rd.forward(request, response);
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.setId(request.getParameter("id"));
		//appeller le manager pour accéder au DAL afin de supprimer l'indisponibilité 
		IndisponibiliteManager manager = new IndisponibiliteManager();
		try {
			manager.supprimerIndisponibilite(Integer.parseInt(this.getId()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("supprimerIndisponibiliteException", e.getMessage());
			
		}
		String messageReussiteSupprimerIndisponibilite = "la supression de l'indisponibilité a été effectuée avec succès. ";
		request.setAttribute("messageReussiteSupprimerIndisponibilite", messageReussiteSupprimerIndisponibilite);
		getServletContext().getRequestDispatcher("/ServletGestionDesIndisponibilites").forward(request, response);

		//doGet(request, response);
	}

}

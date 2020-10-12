package fr.eni.projectParcAutomobile.servlets.vehiculeServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.VehiculeManager;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletGestionDesVehicules
 */
@WebServlet("/ServletGestionDesVehicules")
public class ServletGestionDesVehicules extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Vehicule> listeVehicules = new ArrayList<Vehicule>();
		VehiculeManager manager = new VehiculeManager();
		//récupérer tous les véhicule stokés dans la base de données 
		try {
			listeVehicules = manager.selectTousLesVehicules();
			
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("selectTousLesVehiculeException", e.getMessage());
		}
		request.setAttribute("listeVehicules", listeVehicules);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspVehicule/gestionDesVehicules.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}

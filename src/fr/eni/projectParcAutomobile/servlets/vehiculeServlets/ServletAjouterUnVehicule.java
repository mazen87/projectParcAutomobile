package fr.eni.projectParcAutomobile.servlets.vehiculeServlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.CampusManager;
import fr.eni.projectParcAutomobile.bll.VehiculeManager;
import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

/**
 * Servlet implementation class ServletAjouterUnVehicule
 */
@WebServlet("/ServletAjouterUnVehicule")
public class ServletAjouterUnVehicule extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<Campus> listeCampus = new ArrayList<Campus>();
		CampusManager manager = new CampusManager();
		//récupérer tous les campus stockés dans la bas de données pour que l'utilisateur puisse choisir un pour ajouter un nouveau véhicule
		try {
			listeCampus = manager.selectListeCampus();
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("selectListeCampusException", e.getMessage());
		}
		request.setAttribute("listeCampus", listeCampus);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspVehicule/ajouterVehicule.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String dateAchat1 = request.getParameter("dateAchat");
		String immatriculation = request.getParameter("immatriculation").trim();
		String designation = request.getParameter("designation");
		String idCampus = request.getParameter("site");
		Vehicule vehicule = new Vehicule(); 
		VehiculeManager manager = new VehiculeManager();
		//charger l'objet véhicule par les données saisies par l'utilisateur 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateAchat = sdf.parse(dateAchat1);
			vehicule.setDateAchat(dateAchat);
		} catch (ParseException e) {
		  
			e.printStackTrace();
			String fmatDateException = "le format de date n'est pas valide , veuillez respectez le format indiqué dans la zone de saisie. ";
			request.setAttribute("fmatDateException", fmatDateException);
			doGet(request, response);
		}
		vehicule.setDesignation(designation);
		vehicule.setImmatriculation(immatriculation);
		
		Campus campus = new Campus();
		if(idCampus.trim().equals("0")) 
		{
			campus = null;
		}
		else 
		{
		campus.setIdCampus(Integer.parseInt(idCampus));
		}
		vehicule.setCampus(campus);
		//appeller le managet pour accéder à la méthode qui permet d'insérer le véhicule 
		try {
			manager.ajouterVehicule(vehicule);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("ajouterVehiculeException",e.getMessage());
		}
		String messageReussiteAjouterVehicule = "le Véhicule a été ajouté avec succès. ";
		request.setAttribute("messageReussiteAjouterVehicule", messageReussiteAjouterVehicule);
		doGet(request, response);
	}

}

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

/**
 * Servlet implementation class ServletModifierUnVehicule
 */
@WebServlet("/ServletModifierUnVehicule")
public class ServletModifierUnVehicule extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String idVehic = null ;
    
	public String getIdVehic() {
		return idVehic;
	}

	public void setIdVehic(String idVehic) {
		this.idVehic = idVehic;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		request.setCharacterEncoding("UTF-8");

		this.setIdVehic(request.getParameter("id"));
	    VehiculeManager manager = new VehiculeManager();
	    Vehicule vehicule = new Vehicule();
	    //récupérer le véhicule à modifier 
	    try {
			vehicule = manager.selectVehiculeById(Integer.parseInt(this.getIdVehic()));
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("remplirDonneesVehiculeException", e.getMessage());
		}
	    request.setAttribute("vehicule", vehicule);
	    
	    //récupérer les campus pour les afficher dans la liste déroulante de campus 
	    List<Campus> listeCampus = new ArrayList<Campus>();
		CampusManager managercampus = new CampusManager();
		//récupérer tous les campus stockés dans la bas de données pour que l'utilisateur puisse choisir un pour ajouter un nouveau véhicule
		try {
			listeCampus = managercampus.selectListeCampus();
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("selectListeCampusException", e.getMessage());
		}
		request.setAttribute("listeCampus", listeCampus);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspVehicule/modifierVehicule.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		
		//récupérer les données depuis le formulaire de modification 
		String designation = request.getParameter("designation");
		String immatriculation = request.getParameter("immatriculation").trim();
		String dateAchat1 = request.getParameter("dateAchat");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Vehicule vehicule = new Vehicule();
		//chrger l'objet vehicule 
		try {
			Date dateAchat = sdf.parse(dateAchat1);
			vehicule.setDateAchat(dateAchat);
		} catch (ParseException e) {
		
			e.printStackTrace();
			String fmatDateException = "le format de date n'est pas valide , veuillez respecter le format indiqué dans la zone de saisie. ";
			request.setAttribute("fmatDateException", fmatDateException);
			doGet(request, response);
		}
		
		Campus campus = new Campus();
		String idCampus = request.getParameter("site");
		campus.setIdCampus(Integer.parseInt(idCampus));
		vehicule.setCampus(campus);
		vehicule.setDesignation(designation);
		vehicule.setImmatriculation(immatriculation);
		vehicule.setIdVehic(Integer.parseInt(this.getIdVehic()));
		
		//appeller le manager afin d'insérer l'objet vehicule dans la base de données 
		VehiculeManager manager = new VehiculeManager();
		try {
			manager.modifierVehicule(vehicule.getIdVehic(), vehicule);
		} catch (BusinessException e) {
	
			e.printStackTrace();
			request.setAttribute("modifierVehiculeException",e.getMessage());
		}
		String messageReussiteModifier = "la modification a été effectuée avec succès. ";
		request.setAttribute("messageReussiteModifier", messageReussiteModifier);
		doGet(request, response);
	}

}

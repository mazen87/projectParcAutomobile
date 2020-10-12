package fr.eni.projectParcAutomobile.servlets.vehiculeServlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.IndisponibiliteManager;
import fr.eni.projectParcAutomobile.bll.VehiculeManager;
import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletImmobiliserUnVehicule
 */
@WebServlet("/ServletImmobiliserUnVehicule")
public class ServletImmobiliserUnVehicule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String idVehic = null ;
	

	public String getIdVehic() {
		return idVehic;
	}

	public void setIdVehic(String idVehic) {
		this.idVehic = idVehic;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");

		//récupérer l'id du véhicule à immobiliser 
		this.setIdVehic(request.getParameter("id"));
		//appeller le manager pour accéder au DAL pour récupérer les données du véhicule à immobiliser 
		VehiculeManager manager = new VehiculeManager() ;
		Vehicule vehicule = new Vehicule() ; 
		try {
			vehicule = manager.selectVehiculeById(Integer.parseInt(this.getIdVehic()));
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("selectVehiculeByIdException",e.getMessage());
		}
		request.setAttribute("vehicule", vehicule);
		
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspVehicule/immobiliserUnVehicule.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// récupérer les données saisies par l'utilisateur 
		String dateDebut = request.getParameter("dateDebut");
		String dateFin = request.getParameter("dateFin");
		String motif =request.getParameter("motif");
		
		
		Vehicule vehicule = new Vehicule();
		if(this.getIdVehic() !=null)
		{
		vehicule.setIdVehic(Integer.parseInt(this.getIdVehic()));
		}
		Indisponibilite indisponibilite = new Indisponibilite();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dateDebut1 = sdf.parse(dateDebut);
			
			Date dateFin1 = sdf.parse(dateFin);
			indisponibilite.setDateDebut(dateDebut1);
			indisponibilite.setDateFin(dateFin1);
			
		} catch (ParseException e) {
			e.printStackTrace();
			String fmatDateException = "le format de date n'est pas valide , veuillez respectez le format indiqué dans la zone de saisie. ";
			request.setAttribute("fmatDateException", fmatDateException);
			doGet(request, response);
		}
		indisponibilite.setMotifIndisponibilite(motif);
		indisponibilite.setVehicule(vehicule);
	    //appeller le manager pour pouvoir accéder au DAL 
		IndisponibiliteManager manager = new IndisponibiliteManager();
		try {
			manager.ajouterIndisponibilité(indisponibilite);
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("immobiliserVehiculeException", e.getMessage());
		}
		String messageReussiteImmobiliserVehicule = "l'immobilisation a été effectuée avec succès. ";
		request.setAttribute("messageReussiteImmobiliserVehicule", messageReussiteImmobiliserVehicule);
	    
		doGet(request, response);
 	}

}

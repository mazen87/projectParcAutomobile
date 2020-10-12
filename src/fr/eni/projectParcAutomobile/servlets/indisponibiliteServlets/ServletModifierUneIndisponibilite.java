package fr.eni.projectParcAutomobile.servlets.indisponibiliteServlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.IndisponibiliteManager;
import fr.eni.projectParcAutomobile.bo.Indisponibilite;
import fr.eni.projectParcAutomobile.bo.Vehicule;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletModifierUneIndisponibilite
 */
@WebServlet("/ServletModifierUneIndisponibilite")
public class ServletModifierUneIndisponibilite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private String idIndis = null ;
       private int idVehic = 0;
       
       

	public int getIdVehic() {
		return idVehic;
	}

	public void setIdVehic(int idVehic) {
		this.idVehic = idVehic;
	}

	public String getIdIndis() {
		return idIndis;
	}

	public void setIdIndis(String idIndis) {
		this.idIndis = idIndis;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//récupérer l'id de l'indisponibilité à modifier 
		this.setIdIndis(request.getParameter("id"));
		Indisponibilite indisponibilite = new Indisponibilite();//objet pour récupérer l'undisponibilité après un select par ID 
		IndisponibiliteManager manager = new IndisponibiliteManager();//appeller le manager pour accéder au DAL 
		try {
			indisponibilite = manager.selectIndisponibiliteById(Integer.parseInt(this.getIdIndis()));
			this.setIdVehic(indisponibilite.getVehicule().getIdVehic());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
	       e.printStackTrace();
	       request.setAttribute("selectIndisByIdException", e.getMessage());
		}
	
		request.setAttribute("indisponibilite", indisponibilite);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspIndisponibilite/modifierIndisponibilite.jsp");
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
				
				{
				vehicule.setIdVehic(this.getIdVehic());
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
				indisponibilite.setIdIndis(Integer.parseInt(this.getIdIndis()));
				indisponibilite.setMotifIndisponibilite(motif);
				indisponibilite.setVehicule(vehicule);
			    //appeller le manager pour pouvoir accéder au DAL 
				IndisponibiliteManager manager = new IndisponibiliteManager();
				try {
					manager.modifierIndisponibilite(indisponibilite,Integer.parseInt(this.getIdIndis()));;
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("modifierIndisponibiliteExceotion", e.getMessage());
				}
				String messageReussiteModifierIndisponibilite= "la Modification a été effectuée avec succès. ";
				request.setAttribute("messageReussiteModifierIndisponibilite", messageReussiteModifierIndisponibilite);
		        doGet(request, response);
	}

}

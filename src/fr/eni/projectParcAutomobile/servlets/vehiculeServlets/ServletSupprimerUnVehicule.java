package fr.eni.projectParcAutomobile.servlets.vehiculeServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.VehiculeManager;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletSupprimerUnVehicule
 */
@WebServlet("/ServletSupprimerUnVehicule")
public class ServletSupprimerUnVehicule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String idVehic = null ;
	
  
	public String getIdVehic() {
		return idVehic;
	}

	public void setIdVehic(String idVehic) {
		this.idVehic = idVehic;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    
      //this.setIdVehic(request.getParameter("id"));
	  //RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspVehicule/supprimerVehicule.jsp");
	  	//	rd.forward(request, response);
		//doPost(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		this.setIdVehic(request.getParameter("id"));
		//récupérer l'id du véhicule à supprimer 
				String id = this.getIdVehic();
		         //System.out.println(id);
				//appeller le manager pour accéder au dal afin de supprimer le véhicule 
				VehiculeManager manager = new VehiculeManager();
				try {
					manager.supprimerVehicule(Integer.parseInt(id));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (BusinessException e) {
					e.printStackTrace();
					request.setAttribute("supprimerVehiculeException", e.getMessage());
				}
				String messageReussiteSuppressionVehicule = "la supprission a été effectuée avec succès." ;
				request.setAttribute("messageReussiteSuppressionVehicule", messageReussiteSuppressionVehicule);
				//response.sendRedirect("ServletGestionDesVehicules");
				//doGet(request, response);
				getServletContext().getRequestDispatcher("/ServletGestionDesVehicules").forward(request, response);

	}

}

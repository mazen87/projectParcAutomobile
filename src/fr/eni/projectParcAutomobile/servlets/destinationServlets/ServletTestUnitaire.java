package fr.eni.projectParcAutomobile.servlets.destinationServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.CampusManager;
import fr.eni.projectParcAutomobile.bll.DestinationManager;
import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.bo.Destination;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletTestUnitaire
 */
@WebServlet("/ServletTestUnitaire")
public class ServletTestUnitaire extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public boolean validerCampus (Campus campus) 
	{
		boolean campusValide = true ; 
	
		return campusValide;
	}
	
	public void testUnitaireValiderCampus(Campus campus) 
	{
		StringBuilder sb = new StringBuilder();
		boolean codeValide = true ; 

		
		if( validerCampus(campus)) 
		{
			if(campus.getLibelle() == null || campus.getLibelle().trim().length()==0 || campus.getLibelle().trim().equals("")) 
			{
				codeValide = false;
				sb.append(" votre validation  concernant l'existence du libellé de campus n'est pas correcte  ");
			}
		   if(campus.getLibelle() != null && campus.getLibelle().trim().length()>50) 
			{
			   codeValide = false;
				sb.append(" votre validation  concernant le longeur  du libellé de campus n'est pas correcte  ");
			}
			if(!codeValide) 
			{
				System.out.println(sb.toString());
			}
			if(codeValide) 
			{
				System.out.println(" votre code est valide");
			}
		}
		
		
	}
		
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		
		Campus campus = new Campus();
		campus.setLibelle("snnzoznozngognoggiegheogihgegheghigheoghgogheoghgheghgehgeogheogheogehogheogheogheogheogheogheogheogheogh");
		testUnitaireValiderCampus(campus);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/testUnitaire.jsp");
		rd.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

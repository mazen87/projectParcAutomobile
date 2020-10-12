package fr.eni.projectParcAutomobile.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projectParcAutomobile.bll.CampusManager;
import fr.eni.projectParcAutomobile.bo.Campus;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletGestionDesCampus
 */
@WebServlet("/ServletGestionDesCampus")
public class ServletGestionDesCampus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		CampusManager manager = new CampusManager();
		List<Campus> listeCampus = new ArrayList<Campus>();
		try {
			listeCampus = manager.selectListeCampus();
			//HttpSession session = request.getSession();
			
			request.setAttribute("listeCampus", listeCampus);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("exception1",e.getMessage());
		}
		//String msgReussiteModifier = null;
		//msgReussiteModifier = 	(String) request.getAttribute("msgReussiteModifier");
		//request.setAttribute("msgReussiteModifier", msgReussiteModifier);
		//String exceptionModifier = null;
		//exceptionModifier= (String) request.getAttribute("exceptionModifier");
		//request.setAttribute("exceptionModifier", exceptionModifier);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/gestionDesCampus.jsp");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		doGet(request, response);
	}

}

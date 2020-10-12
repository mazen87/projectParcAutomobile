package fr.eni.projectParcAutomobile.servlets;

import java.io.IOException;

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
 * Servlet implementation class ServletModifierUnCampus
 */
@WebServlet("/ServletModifierUnCampus")
public class ServletModifierUnCampus extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    private String libelle = null ;
    
    private int id = 0 ; 
    
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");

	    //String libelle = request.getParameter("libelle").trim();
	  
		this.setLibelle(request.getParameter("libelle").trim());
		Campus campus = new Campus();
		CampusManager manager = new CampusManager();
		try {
			campus = manager.selectCampusByLibelle(this.getLibelle());
		} catch (BusinessException e) {
			e.printStackTrace();
			request.setAttribute("selectCampudByIdException", e.getMessage());
		}
		this.setId(campus.getIdCampus());
	    
	    request.setAttribute("libelle", libelle);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/modifierCampus.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String libelle = request.getParameter("libelle").trim();
		Campus campus = new Campus();
		campus.setIdCampus(this.getId());
		campus.setLibelle(this.getLibelle());
		CampusManager manager = new CampusManager();
		try {
			manager.modifierCampus(libelle , campus);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("exceptionModifier",e.getMessage());
		} 
		String msgReussiteModifier = "la modification a été effectuée avec succès ";
		request.setAttribute("msgReussiteModifier", msgReussiteModifier);
		doGet(request, response);
	}

}

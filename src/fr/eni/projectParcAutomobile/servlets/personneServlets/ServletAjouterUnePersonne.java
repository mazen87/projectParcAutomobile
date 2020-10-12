package fr.eni.projectParcAutomobile.servlets.personneServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.projectParcAutomobile.bll.PersonnesManager;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletAjouterUnePersonne
 */
@WebServlet("/ServletAjouterUnePersonne")
public class ServletAjouterUnePersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspPersonne/ajouterPersonne.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");

		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String nomStructure = request.getParameter("nomStructure");
	
		String email = request.getParameter("email");
		String motDePasse = request.getParameter("motDePasse");
		String administrateur = request.getParameter("administrateur");
		
		
		PersonnesManager manager = new PersonnesManager();
	
		/*if (email.trim().length() >0 ) 
		{
			try {
				manager.verifierEmailPersonne(email);
			} catch (BusinessException e) {
				
				e.printStackTrace();
				request.setAttribute("emailUniqueException", e.getMessage());
			}
		}
		*/
		
		Personne personne = new Personne() ; 
		
		// récupérer l'information de la cas à cocher 
		if(administrateur != null ) 
		{
			personne.setAdministrateur(true);
		
		}
		else
		{
		personne.setAdministrateur(false);	
		}
		
		personne.setNom(nom);
		personne.setPrenom(prenom);
		if(nomStructure.length()>0 && nomStructure.trim().equals("") )
		{
			nomStructure = "";
			personne.setNomStructure(nomStructure);
		}
		else 
		{
		personne.setNomStructure(nomStructure);
		}
		personne.setEmail(email);
		if(motDePasse.length()>0 && motDePasse.trim().equals("") )
		{
			motDePasse = "";
			personne.setMotDePasse(motDePasse);
		}
		else 
		{
			personne.setMotDePasse(motDePasse);

		}
		try {
			manager.ajouterUnePersonne(personne);
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("AjouterPersonneException", e.getMessage());  
		}
		
		String messageReussiteAjouterPersonne ="la personne a été ajoutée avec succès";
		request.setAttribute("messageReussiteAjouterPersonne", messageReussiteAjouterPersonne);
		
		
		doGet(request, response);
	}

}

package fr.eni.projectParcAutomobile.servlets.personneServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.projectParcAutomobile.bll.PersonnesManager;
import fr.eni.projectParcAutomobile.bo.Personne;
import fr.eni.projectParcAutomobile.exception.BusinessException;

/**
 * Servlet implementation class ServletModifierUnePersonne
 */
@WebServlet("/ServletModifierUnePersonne")
public class ServletModifierUnePersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String id = null ;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

	
		// récupérer l'id de la personne à modifier 
		String idPersonne = request.getParameter("id");
		this.setId(idPersonne);
		//HttpSession session = request.getSession();
		//session.setAttribute("idModifier",idPersonne);
		PersonnesManager manager = new PersonnesManager();
		//objet de personne pour remplir les zone de saisies par les dennés actuelles de la personne à modifier 
		Personne personne = new Personne();
		try {
			personne = manager.selectPersonneById(Integer.parseInt(idPersonne));
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (BusinessException e) {
			
			e.printStackTrace();
			request.setAttribute("exceptionPersonneById", e.getMessage());
		}
		
		request.setAttribute("personneAModifier",personne);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspPersonne/modifierPersonne.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// récupérer l'id de personne à modifier depuis la session 
		//HttpSession session = request.getSession();
		//String idModifier =   (String) session.getAttribute("idModifier");
		//récupérer les données saisies par l'utilisateur dand la formulaire de modifier une personne
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String nomStructure = request.getParameter("nomStructure");
		String email = request.getParameter("email");
		String motDePasse = request.getParameter("motDePasse");
		String administrateur = request.getParameter("administrateur");
		
		// appel le manager pour appel le DAl 
		PersonnesManager manager = new PersonnesManager();
		/*try {
			manager.verifierEmailPersonne(email);
		} catch (BusinessException e) {
		
			e.printStackTrace();
			request.setAttribute("emailUniqueException", e.getMessage());
		}*/
		
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
		personne.setIdPerso(Integer.parseInt(this.getId()));
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
			manager.modifierPersonne(Integer.parseInt(this.getId()), personne);
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
			//envoyer le message d'exception à l'affichage en cas de sa existence 
			request.setAttribute("messageExceptionModifierPersonne", e.getMessage());
		}
		
		String messageSuccsesModifier = "La modification a été effectuée avec succès";
		request.setAttribute("messageSuccsesModifier", messageSuccsesModifier);
		
		doGet(request, response);
	}

}

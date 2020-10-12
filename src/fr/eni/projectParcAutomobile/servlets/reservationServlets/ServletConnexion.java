package fr.eni.projectParcAutomobile.servlets.reservationServlets;

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
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/connexion.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//récupérer l'email et le mot de passe depuis le formulaire de connexion 
		String messageException = null ;
		String email = request.getParameter("email");
		String motDePasse = request.getParameter("motDePasse");
		//appeller le manager pour appeller le Dal afin de vérifier les données saisie par l'utilisateur 
		Personne personne = new Personne();
		PersonnesManager manager = new PersonnesManager();
	    try {
			personne = manager.selectPersonneConnexion(email, motDePasse);
			
		} catch (BusinessException e) {
			
			e.printStackTrace();
			messageException = e.getMessage();
			request.setAttribute("mesageException", messageException);
			
			
		}
		
	    // vérification le retour depuis la base de données 
	    if(personne.getNom()!= null) // personne connectée
	    {
	    String connexionReussite = "Vous êtes Connecté";
	    HttpSession session = request.getSession();
	    session.setAttribute("personne",personne);
	    request.setAttribute("connexionReussite", connexionReussite);
	    //RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/jspReservation/gestionDesReservationsAccueil.jsp");
	    //rd.forward(request, response);
	    //response.sendRedirect("ServletGestionDesReservationsAccueil");
		getServletContext().getRequestDispatcher("/ServletGestionDesReservationsAccueil").forward(request, response);

	    
	    }
	    else // personne non connectée
	    {
	    	String connexioneErrone = "vos identifiants sont incorrects !! .... ";
	    	request.setAttribute("connexioneErrone", connexioneErrone);
	    	doGet(request, response);	
	    }
		
		
	}

}

package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Model.*;
import Config.HibernateUtil;
import DAO.UtilisateurDao;

/**
 * Servlet implementation class connexionUtilisateur
 */
@WebServlet("/ConnexionUtilisateur")
public class ConnexionUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnexionUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("password") == null || request.getParameter("email") == null) {
			
			String errormessage = "Veuillez saisir votre adresse email et votre mot de passe !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/index.jsp").
			forward(request, response);
			
		}
		
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    
	    UtilisateurDao utilisateurDao = new UtilisateurDao(session);
		
		Utilisateur utilisateur = utilisateurDao.connectUser(email, password);
		
		session.close();
			
		if(utilisateur == null) {
			
			String errormessage = "Adresse e-mail ou mot de passe incorrect !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/index.jsp").
			forward(request, response);
			
		}
			
		Utilisateur connectedUser = new Utilisateur();
			
		connectedUser.setIdentifiant(utilisateur.getIdentifiant());
		connectedUser.setNom(utilisateur.getNom());
		connectedUser.setPrenom(utilisateur.getPrenom());
		connectedUser.setEmail(utilisateur.getEmail());
		connectedUser.setPrivileges(utilisateur.getPrivileges());
		connectedUser.setAdresses(utilisateur.getAdresses());
		
		HttpSession userSession = request.getSession();
		
		ListePanier listepanier = new ListePanier();
		userSession.setAttribute("listepanier", listepanier);
		userSession.setAttribute("user", connectedUser);
		
		Cookie user = new Cookie("useremail", connectedUser.getEmail());
		user.setHttpOnly(true);
		user.setMaxAge(31536000);
		
		response.addCookie(user);
		this.getServletContext().getRequestDispatcher("/AfficherListe").forward(request, response);
		
	}

}

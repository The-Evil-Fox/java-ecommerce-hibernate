package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import Model.*;

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
		
		getServletContext().getRequestDispatcher("/AfficherListe").
		forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.
		buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Utilisateur.class);
		List<Utilisateur> listeUtilisateurs = (List<Utilisateur>) criteria.list();
		
		boolean found = false;
		
		Utilisateur connectedUser = new Utilisateur();
		
		for (int i = 0; i < listeUtilisateurs.size(); i++) {
			
			if(listeUtilisateurs.get(i).getEmail().equals(email) && listeUtilisateurs.get(i).getPassword().equals(password)) {
				
				connectedUser.setNom(listeUtilisateurs.get(i).getNom());
				connectedUser.setPrenom(listeUtilisateurs.get(i).getPrenom());
				connectedUser.setEmail(listeUtilisateurs.get(i).getEmail());
				connectedUser.setPrivileges(listeUtilisateurs.get(i).getPrivileges());
				found = true;
				break;
				
			}
			
		}
		
		if(found == true) {
			
			HttpSession userSession = request.getSession();
			userSession.setAttribute("user", connectedUser);
			Cookie user = new Cookie("useremail", connectedUser.getEmail());
			user.setHttpOnly(true);
			user.setMaxAge(31536000);
			response.addCookie(user);
			
			this.getServletContext().getRequestDispatcher("/AfficherListe").
			forward(request, response);
			
		} else {
			
			String errormessage = "Adresse e-mail ou mot de passe incorrect !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/connexion.jsp").
			forward(request, response);
			
		}
		
	}

}

package Controller;

import java.io.IOException;
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
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

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
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.
		buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(Utilisateur.class);
		criteria = criteria.add(Restrictions.eq("email", email));
		criteria = criteria.add(Restrictions.eq("password", password));
		@SuppressWarnings("unchecked")
		List<Utilisateur> listeUtilisateurs = (List<Utilisateur>) criteria.list();
		
		session.close();
		sessionFactory.close();
			
		if(listeUtilisateurs.isEmpty()) {
			
			String errormessage = "Adresse e-mail ou mot de passe incorrect !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/index.jsp").
			forward(request, response);
			
		}
			
		Utilisateur connectedUser = new Utilisateur();
		
		for(Utilisateur u : listeUtilisateurs) {
			
			connectedUser.setIdentifiant(u.getIdentifiant());
			connectedUser.setNom(u.getNom());
			connectedUser.setPrenom(u.getPrenom());
			connectedUser.setEmail(u.getEmail());
			connectedUser.setPrivileges(u.getPrivileges());
			connectedUser.setAdresses(u.getAdresses());
			break;
			
		}
		
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

package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Model.Utilisateur;

/**
 * Servlet implementation class ModifierNomUtilisateur
 */
public class ModifierEmailUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierEmailUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("email") == null) {
			
	        this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		} else {
		
			String nouvelleEmail = request.getParameter("email");
			
			Configuration configuration = new Configuration().configure();
			SessionFactory sessionFactory = configuration.
			buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			HttpSession userSession = request.getSession();
			
			Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
			
			Utilisateur user = session.get(Utilisateur.class, connectedUser.getIdentifiant());
			
			connectedUser.setEmail(nouvelleEmail);
			user.setEmail(nouvelleEmail);
			
			// Update dans la bdd
			session.persist(user);
			
			// Update de la session de l'utilisateur
			userSession.setAttribute("user", connectedUser);
			
			transaction.commit();
	        session.close();
	        sessionFactory.close();
	        
	        this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
		
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

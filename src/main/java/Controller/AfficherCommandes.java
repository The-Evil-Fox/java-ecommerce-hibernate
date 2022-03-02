package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.CommandeDao;
import Model.Commande;
import Model.Utilisateur;

/**
 * Servlet implementation class AfficherCommandes
 */
public class AfficherCommandes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherCommandes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession userSession = request.getSession();
		
		Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
	    
	    CommandeDao commandeDao = new CommandeDao(session);
		
		List<Commande> commandes = commandeDao.getCommandesFromUser(connectedUser.getIdentifiant());
		
		session.close();
		
		request.setAttribute("commandes", commandes);
		this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-commandes.jsp").
		forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

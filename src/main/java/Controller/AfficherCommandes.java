package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
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
		
		Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
		
		Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
		
		Criteria criteria = session.createCriteria(Commande.class);
		criteria = criteria.add(Restrictions.eq("utilisateur.identifiant", connectedUser.getIdentifiant()));
		@SuppressWarnings("unchecked")
		List<Commande> commandes = (List<Commande>) criteria.list();
		
        transaction.commit();
        session.close();
        sessionFactory.close();
		
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

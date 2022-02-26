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
import Model.ArticleCommande;
import Model.Commande;
import Model.ListePanier;
import Model.Panier;
import Model.Utilisateur;

/**
 * Servlet implementation class FinalisationCommande
 */
public class FinalisationCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalisationCommande() {
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
		ListePanier listepanier = (ListePanier) userSession.getAttribute("listepanier");
		
		Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        Commande commande = new Commande();
        commande.setPrixTotal(listepanier.getMontantTotal());
        commande.setQuantite(listepanier.getQuantiteTotale());
        
		for(Panier p : listepanier.getListe()) {
			
			ArticleCommande articleCommande = new ArticleCommande(commande, p.getProduit(), p.getQuantite(), p.getProduit().getPrix() * p.getQuantite());
			commande.addArticlesCommande(articleCommande);
			session.persist(articleCommande);
			
		}
		
		Utilisateur user = session.get(Utilisateur.class, connectedUser.getIdentifiant());
		
		connectedUser.addCommande(commande);
        
		session.persist(commande);
        session.persist(user);
        
        transaction.commit();
        session.close();
        sessionFactory.close();
		
		ListePanier emptyPanier = new ListePanier();
		userSession.setAttribute("listepanier", emptyPanier);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/validation-commande.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

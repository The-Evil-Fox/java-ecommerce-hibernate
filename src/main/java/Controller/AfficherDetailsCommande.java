package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Model.ArticleCommande;
import Model.Produit;
import Model.Utilisateur;

/**
 * Servlet implementation class AfficherDetailsCommande
 */
public class AfficherDetailsCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherDetailsCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("idcommande") == null) {
			
			this.getServletContext().getRequestDispatcher("/AfficherCommandes").
			forward(request, response);
			
		}
		
		int idcommande = Integer.parseInt(request.getParameter("idcommande"));
		
		HttpSession userSession = request.getSession();
		
		Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
		
		Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
		
		String SQLQuery = "SELECT p.libelle, p.prix, p.cheminimage, ac.prixtotal, ac.quantite FROM produit p"
				+ "	LEFT JOIN articlecommande ac on p.identifiant = ac.produit_identifiant"
				+ " LEFT JOIN commande c on ac.commande_identifiant = c.identifiant"
				+ " WHERE ac.commande_identifiant = :idcommande AND c.utilisateur_identifiant = :iduser";

		SQLQuery query = session.createSQLQuery(SQLQuery);
		query.setParameter("idcommande", idcommande);
		query.setParameter("iduser", connectedUser.getIdentifiant());
		@SuppressWarnings("unchecked")
		List<Object[]> donnees = (List<Object[]>) query.list();
		
		transaction.commit();
		session.close();
		sessionFactory.close();
		
		List<Produit> produits = new ArrayList<Produit>();
		List<ArticleCommande> articles = new ArrayList<ArticleCommande>();
		
		for (Object[] row : donnees) {
			
			Produit produit = new Produit();
			produit.setLibelle(row[0].toString());
			produit.setPrix(Double.parseDouble(row[1].toString()));
			produit.setCheminimage(row[2].toString());
			produits.add(produit);
			
			ArticleCommande articleCommande = new ArticleCommande();
			articleCommande.setPrixtotal(Double.parseDouble(row[3].toString()));
			articleCommande.setQuantite(Integer.parseInt(row[4].toString()));
			articles.add(articleCommande);
			
		}
		
		if(articles.isEmpty()) {
			
			this.getServletContext().getRequestDispatcher("/AfficherCommandes").
			forward(request, response);
			
		}
		
		request.setAttribute("produits", produits);
		request.setAttribute("articles", articles);
		this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-details-commande.jsp").
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

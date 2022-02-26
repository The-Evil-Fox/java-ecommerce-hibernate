package Controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Model.ListePanier;
import Model.Panier;
import Model.Produit;

/**
 * Servlet implementation class AjoutPanier
 */
@WebServlet("/AjoutPanier")
public class AjoutPanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutPanier() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("id") == null) {
			
			this.getServletContext().getRequestDispatcher("/AfficherListe").
			forward(request, response);
			
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		int quantite = 1;
		
		Produit produit = new Produit();
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.
		buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Produit.class);
		@SuppressWarnings("unchecked")
		List<Produit> liste = (List<Produit>) criteria.list();
		
		transaction.commit();
		session.close();
		sessionFactory.close();
		
		HttpSession userSession = request.getSession();
		
		for (int i = 0; i < liste.size(); i++) {
			
			if(liste.get(i).getIdentifiant() == id) {
				
				produit.setIdentifiant(liste.get(i).getIdentifiant());
				produit.setLibelle(liste.get(i).getLibelle());
				produit.setCheminimage(liste.get(i).getCheminimage());
				produit.setPrix(liste.get(i).getPrix());
				
			}
			
		}
		
		Panier panier = new Panier(produit, quantite);
		
		ListePanier listepanier = new ListePanier();
		
		if(userSession.getAttribute("listepanier") != null) {
		
			listepanier = (ListePanier) userSession.getAttribute("listepanier");
			
		} else {
			
			userSession.setAttribute("listepanier", listepanier);
			
		}
		
		boolean alreadyExist = false;
		
		for(Panier p : listepanier.getListe()) {
			
			if(p.getProduit().getLibelle().equals(panier.getProduit().getLibelle())) {
				
				p.setQuantite(p.getQuantite() + 1);
				alreadyExist = true;
				
			}
			
		}
		
		if(alreadyExist == false) {
			
			listepanier.getListe().add(panier);
			
		}
		
		double montantTotal = 0;
		int quantiteTotale = 0;
		
		for(Panier p : listepanier.getListe()) {
			
			montantTotal += p.getProduit().getPrix() * p.getQuantite();
			quantiteTotale += p.getQuantite();
			
		}
		
		listepanier.setMontantTotal(montantTotal);
		listepanier.setQuantiteTotale(quantiteTotale);
		
		request.setAttribute("listepanier", listepanier);
        request.setAttribute("produits", liste);
        this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-produits.jsp").
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

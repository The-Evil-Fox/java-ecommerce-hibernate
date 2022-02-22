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

import Model.ListePanier;
import Model.Produit;

/**
 * Servlet implementation class ModifierProduit
 */
public class ModifierProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierProduit() {
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
			
		} else {
		
			int idproduit = Integer.parseInt(request.getParameter("id"));
			
			Produit produitToUpdate = new Produit();
			
			Configuration configuration = new Configuration().configure();
			SessionFactory sessionFactory = configuration.
			buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Produit.class);
			criteria = criteria.add(Restrictions.eq("identifiant", idproduit));
			List<Produit> produits = (List<Produit>) criteria.list();
			
			for(Produit p : produits) {
				
				produitToUpdate.setIdentifiant(p.getIdentifiant());
				produitToUpdate.setLibelle(p.getLibelle());
				produitToUpdate.setCheminimage(p.getCheminimage());
				produitToUpdate.setPrix(p.getPrix());
			}
			
			request.setAttribute("produitToUpdate", produitToUpdate);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/admin/modifier-produit.jsp").
			forward(request, response);
			
			transaction.commit();
			session.close();
			sessionFactory.close();
			
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

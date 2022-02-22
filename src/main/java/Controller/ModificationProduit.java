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

import Model.ListePanier;
import Model.Produit;

/**
 * Servlet implementation class ModificationProduit
 */
public class ModificationProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificationProduit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("id") == null || request.getParameter("libelle") == null 
				|| request.getParameter("cheminimage") == null || request.getParameter("prix") == null) {
			
	        this.getServletContext().getRequestDispatcher("/AfficherListe").
			forward(request, response);
			
		} else {
		
			int idproduit = Integer.parseInt(request.getParameter("id"));
			String libelle = request.getParameter("libelle");
			String cheminimage = request.getParameter("cheminimage");
			double prix = Double.parseDouble(request.getParameter("prix"));
			
			
			Configuration configuration = new Configuration().configure();
			SessionFactory sessionFactory = configuration.
			buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Produit produitToUpdate = session.get(Produit.class, idproduit);
			
			produitToUpdate.setLibelle(libelle);
			produitToUpdate.setCheminimage(cheminimage);
			produitToUpdate.setPrix(prix);
			
			session.flush();
			transaction.commit();
			session.close();
			sessionFactory.close();
			
			this.getServletContext().getRequestDispatcher("/AfficherListe").
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

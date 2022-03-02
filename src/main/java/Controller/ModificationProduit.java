package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.ProduitDao;
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
				|| request.getParameter("cheminimage") == null || request.getParameter("prix") == null 
				|| request.getParameter("quantite") == null) {
			
	        this.getServletContext().getRequestDispatcher("/AfficherListe").
			forward(request, response);
			
		}
		
		int idproduit = Integer.parseInt(request.getParameter("id"));
		String libelle = request.getParameter("libelle");
		String cheminimage = request.getParameter("cheminimage");
		double prix = Double.parseDouble(request.getParameter("prix"));
		int quantite = Integer.parseInt(request.getParameter("quantite"));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		ProduitDao produitDao = new ProduitDao(session);
		
		Produit produitToUpdate = null;
		
		try {
			
			produitToUpdate = produitDao.findById(idproduit);
			
			if(produitToUpdate == null) {
				
				session.close();
				this.getServletContext().getRequestDispatcher("/afficherListe").
				forward(request, response);
				
			}
			
			produitToUpdate.setLibelle(libelle);
			produitToUpdate.setCheminimage(cheminimage);
			produitToUpdate.setPrix(prix);
			produitToUpdate.setQuantitestock(quantite);
			
			produitDao.save(produitToUpdate);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
		
		session.close();
		
		this.getServletContext().getRequestDispatcher("/AfficherListe").
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

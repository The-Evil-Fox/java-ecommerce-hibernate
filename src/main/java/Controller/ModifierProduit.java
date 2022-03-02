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
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			ProduitDao produitDao = new ProduitDao(session);
			
			Produit produitInDb = null;
			
			try {
				
				produitInDb = produitDao.findById(idproduit);
				
				if(produitInDb == null) {
					
					session.close();
					this.getServletContext().getRequestDispatcher("/AfficherProfil").
					forward(request, response);
					
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			
			}
			
			session.close();
				
			produitToUpdate.setIdentifiant(produitInDb.getIdentifiant());
			produitToUpdate.setLibelle(produitInDb.getLibelle());
			produitToUpdate.setCheminimage(produitInDb.getCheminimage());
			produitToUpdate.setPrix(produitInDb.getPrix());
			produitToUpdate.setQuantitestock(produitInDb.getQuantitestock());
			
			request.setAttribute("produitToUpdate", produitToUpdate);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/admin/modifier-produit.jsp").
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

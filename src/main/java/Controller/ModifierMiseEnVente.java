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
 * Servlet implementation class ModifierMiseEnVente
 */
public class ModifierMiseEnVente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierMiseEnVente() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("id")  == null && request.getParameter("method") == null) {
			
			this.getServletContext().getRequestDispatcher("/AfficherListe").
			forward(request, response);
			
		}
		
		int idproduit = Integer.parseInt(request.getParameter("id"));
		String method = request.getParameter("method");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		ProduitDao produitDao = new ProduitDao(session);
		
		Produit produit = produitDao.findById(idproduit);
		
		if(method.equals("ajout")) {
			
			produit.setEnvente(true);
			
		} else if(method.equals("retrait")) {
			
			produit.setEnvente(false);
			
		}
		
		try {
			
			produitDao.save(produit);
			
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

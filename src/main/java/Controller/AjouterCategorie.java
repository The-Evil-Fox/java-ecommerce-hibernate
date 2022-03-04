package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import Config.HibernateUtil;
import DAO.AdresseDao;
import DAO.CategorieProduitDao;
import Model.CategorieProduit;

/**
 * Servlet implementation class AjouterCategorie
 */
public class AjouterCategorie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjouterCategorie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ajout-categorie.jsp").
		forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
Session session = HibernateUtil.getSessionFactory().openSession();
		
		CategorieProduitDao categorieDao = new CategorieProduitDao(session);
		
		String categorie = request.getParameter("categorie");
		
		CategorieProduit categorieProduit = new CategorieProduit();
		
		categorieProduit.setCategorie(categorie);
		
		try {
			
			categorieDao.save(categorieProduit);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		session.close();
		
		this.getServletContext().getRequestDispatcher("/AfficherListe").
		forward(request, response);
		
	}

}

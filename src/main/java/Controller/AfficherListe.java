package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.CategorieProduitDao;
import DAO.ProduitDao;
import Model.CategorieProduit;
import Model.Produit;

/**
 * Servlet implementation class AfficherListe
 */
@WebServlet("/AfficherListe")
public class AfficherListe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfficherListe() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		ProduitDao produitDao = new ProduitDao(session);
		CategorieProduitDao categorieProduitDao = new CategorieProduitDao(session);
		
		List<Produit> liste = produitDao.findAll();
		List<CategorieProduit> categories = categorieProduitDao.findAll();
		
		session.close();
		
		request.setAttribute("categories", categories);
        request.setAttribute("produits", liste);
        this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-produits.jsp").
		forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("categorie").equals("") && request.getParameter("minimum").equals("") 
				&& request.getParameter("maximum").equals("")) {
			
			doGet(request, response);
			
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		ProduitDao produitDao = new ProduitDao(session);
		CategorieProduitDao categorieProduitDao = new CategorieProduitDao(session);
		
		List<CategorieProduit> categories = categorieProduitDao.findAll();
		List<Produit> liste = new ArrayList<Produit>();
		
		double minimum = 0;
		double maximum = 999999999;
		int categorie = 0;
		
		if(!request.getParameter("categorie").equals("")) {
			
			categorie = Integer.parseInt(request.getParameter("categorie"));
			
		}
		
		if(!request.getParameter("minimum").equals("")) {
			
			minimum = Double.parseDouble(request.getParameter("minimum"));
			
		}
		
		if(!request.getParameter("maximum").equals("")) {
			
			maximum = Double.parseDouble(request.getParameter("maximum"));
			
		}
		
		if(categorie != 0) {
			
			List<Object[]> donnees = produitDao.findByCategorie(categorie, minimum, maximum);
			
			for(Object[] row : donnees) {
				
				Produit p = new Produit();
				p.setIdentifiant(Integer.parseInt(row[0].toString()));
				p.setPrix(Double.parseDouble(row[1].toString()));
				p.setQuantitestock(Integer.parseInt(row[2].toString()));
				p.setCheminimage(row[3].toString());
				
				if(row[4].toString().equals("F")) {
					
					p.setEnvente(false);
					
				} else {
					
					p.setEnvente(true);
					
				}
				
				p.setLibelle(row[5].toString());
				
				liste.add(p);
				
			}
			
		} else if(categorie == 0) {
			
			liste = produitDao.findByPrice(minimum, maximum);
			
		}
		
		session.close();
		
		request.setAttribute("produits", liste);
		request.setAttribute("categories", categories);
        this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-produits.jsp").
		forward(request, response);
		
	}

}

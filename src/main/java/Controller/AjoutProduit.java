package Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
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
 * Servlet implementation class ProduitControlleur
 */
public class AjoutProduit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AjoutProduit() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	
    	Session session = HibernateUtil.getSessionFactory().openSession();
        
        CategorieProduitDao categorieProduitDao = new CategorieProduitDao(session);
        
        List<CategorieProduit> categories = categorieProduitDao.findAll();
        
		request.setAttribute("categories", categories);
		this.getServletContext().getRequestDispatcher("/WEB-INF/admin/ajout-produit.jsp").
		forward(request, response);
        
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    	
    	if(request.getParameter("libelle") == null || request.getParameter("cheminimage") == null || 
    			request.getParameter("prix") == null || request.getParameter("quantite") == null || 
    			request.getParameter("miseenvente") == null || request.getParameter("categorie") == null || 
    			request.getParameter("categorie").equals("") ) {
    		
    		String erreur = "Veuillez remplir touts les champs !";
    		request.setAttribute("erreur", erreur);
    		this.getServletContext().getRequestDispatcher("/admin/ajout-produit.jsp").
    		forward(request, response);
    		
    	}
        
    	String libelle = request.getParameter("libelle");
        String cheminimage = request.getParameter("cheminimage");
        double prix = Double.parseDouble(request.getParameter("prix"));
        int quantite = Integer.parseInt(request.getParameter("quantite"));
        boolean miseenvente = false;
        int categorieProduit = Integer.parseInt(request.getParameter("categorie"));
        
        if(request.getParameter("miseenvente") != null) {
        	
        	miseenvente = true;
        	
        }
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        CategorieProduitDao categorieProduitDao = new CategorieProduitDao(session);
        
        CategorieProduit categorie = null;
        
        Produit nouveauProduit = new Produit();
        nouveauProduit.setLibelle(libelle);
        nouveauProduit.setCheminimage(cheminimage);
        nouveauProduit.setPrix(prix);
        nouveauProduit.setQuantitestock(quantite);
        nouveauProduit.setEnvente(miseenvente);
        
        try {
			
			categorie = categorieProduitDao.findById(categorieProduit);
			
			if(categorie == null) {
				
				session.close();
				this.getServletContext().getRequestDispatcher("/afficherListe").
				forward(request, response);
				
			}
			
			categorie.addProduit(nouveauProduit);
			nouveauProduit.setCategorie(categorie);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		
		}
        
        ProduitDao produitDao = new ProduitDao(session);
        
		try {
			
			produitDao.save(nouveauProduit);
			categorieProduitDao.save(categorie);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		session.close();
        
        this.getServletContext().getRequestDispatcher("/AfficherListe").
		forward(request, response);
        
    }

}
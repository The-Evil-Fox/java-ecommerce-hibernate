package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
    	
    	if(request.getParameter("libelle") == null || request.getParameter("cheminimage") == null || request.getParameter("prix") == null) {
    		
    		String erreur = "Veuillez remplir touts les champs !";
    		request.setAttribute("erreur", erreur);
    		this.getServletContext().getRequestDispatcher("/admin/ajout-produit.jsp").
    		forward(request, response);
    		
    	}
        
    	String libelle = request.getParameter("libelle");
        String cheminimage = request.getParameter("cheminimage");
        double prix = Double.parseDouble(request.getParameter("prix"));
        
        Produit nouveauProduit = new Produit();
        nouveauProduit.setLibelle(libelle);
        nouveauProduit.setCheminimage(cheminimage);
        nouveauProduit.setPrix(prix);
        
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(nouveauProduit);
        
        session.flush();
        transaction.commit();
        session.close();
        sessionFactory.close();
        
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
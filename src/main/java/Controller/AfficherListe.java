package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.
		buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Produit.class);
		List<Produit> liste = (List<Produit>) criteria.list();
		
		HttpSession userSession = request.getSession();
		
		ListePanier listepanier = new ListePanier();
		
		if(userSession.getAttribute("listepanier") != null) {
		
			listepanier = (ListePanier) userSession.getAttribute("listepanier");
			
		} else {
			
			userSession.setAttribute("listepanier", listepanier);
			
		}
		
		session.flush();
		transaction.commit();
		session.close();
		sessionFactory.close();
        
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
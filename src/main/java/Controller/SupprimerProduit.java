package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

import java.util.*;
import Model.*;

/**
 * Servlet implementation class SupprimerProduit
 */
@WebServlet("/SupprimerProduit")
public class SupprimerProduit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupprimerProduit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int idproduit = Integer.parseInt(request.getParameter("id"));
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sessionFactory = configuration.
		buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
		Produit produit = session.get(Produit.class, idproduit);
		session.delete(produit);
		transaction.commit();
		session.close();
		sessionFactory.close();

		
		HttpSession userSession = request.getSession();
		
		ListePanier listepanier = new ListePanier();
		
		if(userSession.getAttribute("listepanier") != null) {
		
			listepanier = (ListePanier) userSession.getAttribute("listepanier");
			
		} else {
			
			userSession.setAttribute("listepanier", listepanier);
			
		}
        
		request.setAttribute("listepanier", listepanier);
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

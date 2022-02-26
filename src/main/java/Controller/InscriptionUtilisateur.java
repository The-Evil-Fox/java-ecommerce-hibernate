package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Model.Adresse;
import Model.ListePanier;
import Model.Utilisateur;

/**
 * Servlet implementation class ClientControlleur
 */
//@WebServlet("/ClientControlleur")
public class InscriptionUtilisateur extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public InscriptionUtilisateur() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    	
    	if(request.getParameter("nom") == null || request.getParameter("prenom") == null
    			|| request.getParameter("email") == null || request.getParameter("password") == null || request.getParameter("password2") == null) {
    		
    		String errormessage = "Veuillez remplir touts les champs obligatoires !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/inscription.jsp").
			forward(request, response);
    		
    	}
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        
        Utilisateur connectedUser = new Utilisateur();
        
        connectedUser.setNom(nom);
        connectedUser.setPrenom(prenom);
        connectedUser.setEmail(email);
        connectedUser.setPassword(password);
        connectedUser.setPrivileges(1);
        
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        
        if(!request.getParameter("rue").equals("") && (request.getParameter("ville").equals("") || request.getParameter("cp").equals(""))) {
        	
        	String errormessage = "Veuillez saisir votre adresse de livraison complète !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/inscription.jsp").
			forward(request, response);
        	
        } else if(!request.getParameter("rue").equals("") && !request.getParameter("ville").equals("") || !request.getParameter("cp").equals("")) {
        	
        	String rue = request.getParameter("rue");
            String ville = request.getParameter("ville");
            String cp = request.getParameter("cp");
            
            Adresse userAdresse = new Adresse(rue, cp, ville, true);
            
            connectedUser.addAdresse(userAdresse);
            
            session.persist(userAdresse);
        	
        }
        
        if(!password.equals(password2)) {
        	
        	String errormessage = "Les mots de passes entrés ne correspondent pas !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/inscription.jsp").
			forward(request, response);
        	
        }
        
        session.persist(connectedUser);
        transaction.commit();
        session.close();
        sessionFactory.close();
        
        HttpSession userSession = request.getSession();
        
        ListePanier listepanier = new ListePanier();
		userSession.setAttribute("listepanier", listepanier);
		userSession.setAttribute("user", connectedUser);
		
		Cookie user = new Cookie("useremail", connectedUser.getEmail());
		user.setHttpOnly(true);
		user.setMaxAge(31536000);
		response.addCookie(user);
		
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

package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
        
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Utilisateur connectedUser = new Utilisateur();
        connectedUser.setNom(nom);
        connectedUser.setPrenom(prenom);
        connectedUser.setEmail(email);
        connectedUser.setPassword(password);
        connectedUser.setPrivileges(0);
        
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Integer cle = (Integer) session.save(connectedUser);
        transaction.commit();
        session.close();
        sessionFactory.close();
        
        HttpSession userSession = request.getSession();
		
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

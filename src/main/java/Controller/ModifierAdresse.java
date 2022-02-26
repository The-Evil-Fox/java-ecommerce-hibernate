package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import Model.Adresse;
import Model.Utilisateur;

/**
 * Servlet implementation class ModifierAdresse
 */
public class ModifierAdresse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierAdresse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if((request.getParameter("adresse") == null && request.getParameter("action") == null) || request.getParameter("adresse").equals("") ) {
			
			this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		} else {
			
			String adresse = request.getParameter("adresse");
			String action = request.getParameter("action");
			
			Configuration configuration = new Configuration().configure();
			SessionFactory sessionFactory = configuration.
			buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			HttpSession userSession = request.getSession();
			
			Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
			
			if(action.equals("delete")) {
				
				Adresse adresseToDelete = session.get(Adresse.class, adresse);
				session.delete(adresseToDelete);
				
				for(Adresse a : connectedUser.getAdresses()) {
					
					if(a.getRue().equals(adresse)) {
						
						connectedUser.getAdresses().remove(a);
						break;
						
					}
					
				}
				
				userSession.setAttribute("user", connectedUser);
				
				
			} else if(action.equals("setAdresseLivraison")) {
				
				
				// Modification base de données
				
				
				Utilisateur user = session.get(Utilisateur.class, connectedUser.getIdentifiant());
				
				Adresse adresseToSet = session.get(Adresse.class, adresse);
				adresseToSet.setAdresselivraison(true);
				
				for(Adresse a : user.getAdresses()) {
					
					if(a.isAdresselivraison() == true && !a.getRue().equals(adresse)) {
						
						a.setAdresselivraison(false);
						
					}
					
					if(a.getRue().equals(adresse)) {
						
						a.setAdresselivraison(true);
						
					}
					
				}
				
				session.persist(user);
				session.persist(adresseToSet);
				
				// Modification session utilisateur
				
				for(Adresse a : connectedUser.getAdresses()) {
					
					if(a.isAdresselivraison() == true && !a.getRue().equals(adresse)) {
						
						a.setAdresselivraison(false);
						
					}
					
					if(a.getRue().equals(adresse)) {
						
						a.setAdresselivraison(true);
						
					}
					
				}
				
				
				
			}
			
			transaction.commit();
	        session.close();
	        sessionFactory.close();
			
			this.getServletContext().getRequestDispatcher("/AfficherProfil").
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

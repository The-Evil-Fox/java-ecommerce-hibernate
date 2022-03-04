package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.AdresseDao;
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
		
		if((request.getParameter("adresse") == null && request.getParameter("action") == null) || 
				request.getParameter("adresse").equals("") ) {
			
			this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		} else {
			
			String adresse = request.getParameter("adresse");
			String action = request.getParameter("action");
			
			HttpSession userSession = request.getSession();
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
			
			if(action.equals("delete")) {
				
				AdresseDao adresseDao = new AdresseDao(session);
				
				Adresse adresseInDb = null;
				
				try {
					
					adresseInDb = adresseDao.findByRue(adresse);
					
					if(adresseInDb == null) {
						
						session.close();
						this.getServletContext().getRequestDispatcher("/AfficherProfil").
						forward(request, response);
						
					}
					
					adresseDao.delete(adresseInDb);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				
				}
				
				for(Adresse a : connectedUser.getAdresses()) {
					
					if(a.getRue().equals(adresse)) {
						
						connectedUser.getAdresses().remove(a);
						break;
						
					}
					
				}
				
				userSession.setAttribute("user", connectedUser);
				
				
			} else if(action.equals("setAdresseLivraison")) {
				
				// Modification données db
				
				AdresseDao adresseDao = new AdresseDao(session);
				
				Adresse adresseInDb = null;
				
				try {
					
					adresseInDb = adresseDao.findByRue(adresse);
					
					if(adresseInDb == null) {
						
						session.close();
						this.getServletContext().getRequestDispatcher("/AfficherProfil").
						forward(request, response);
						
					}
					
					adresseInDb.setAdresselivraison(true);
					adresseDao.save(adresseInDb);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				
				}
				
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
			
			session.close();
			
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

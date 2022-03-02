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
import DAO.UtilisateurDao;
import Model.Adresse;
import Model.Utilisateur;

/**
 * Servlet implementation class AjoutAdresse
 */
public class AjoutAdresse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutAdresse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("rue") == null || request.getParameter("cp") == null
				|| request.getParameter("ville") == null) {
			
			String errormessage = "Veuillez remplir touts les champs obligatoires !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		}
		
		String rue = request.getParameter("rue");
		String cp = request.getParameter("cp");
		String ville = request.getParameter("ville");
		boolean adresselivraison;
		
		HttpSession userSession = request.getSession();
		
		Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
		
		if(request.getParameter("livraisonpardefaut") != null) {
			
			adresselivraison = true;
			
		} else {
			
			adresselivraison = false;
		}
		
		Adresse nouvelleAdresse = new Adresse(rue, cp, ville, adresselivraison);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		AdresseDao adresseDao = new AdresseDao(session);
		
		UtilisateurDao utilisateurDao = new UtilisateurDao(session);
		
		Utilisateur user = null;
		
		user = utilisateurDao.findById(connectedUser.getIdentifiant());
		
		if(user == null) {
			
			this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		}
		
		if(adresselivraison == true) {
		
			for(Adresse a : user.getAdresses()) {
				
				if(a.isAdresselivraison() == true) {
					
					a.setAdresselivraison(false);
					
				}
				
			}
			
			for(Adresse a : connectedUser.getAdresses()) {
				
				if(a.isAdresselivraison() == true) {
					
					a.setAdresselivraison(false);
					
				}
				
			}
			
		}
		
		try {
			
			connectedUser.addAdresse(nouvelleAdresse);
			adresseDao.save(nouvelleAdresse);
			utilisateurDao.save(user);
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		session.close();
        
        this.getServletContext().getRequestDispatcher("/AfficherProfil").
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

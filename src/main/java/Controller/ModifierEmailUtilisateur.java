package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.UtilisateurDao;
import Model.Utilisateur;

/**
 * Servlet implementation class ModifierNomUtilisateur
 */
public class ModifierEmailUtilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierEmailUtilisateur() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("email") == null) {
			
	        this.getServletContext().getRequestDispatcher("/AfficherProfil").
			forward(request, response);
			
		} else {
		
			String nouvelleEmail = request.getParameter("email");
			
			HttpSession userSession = request.getSession();
			
			Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
			
			Session session = HibernateUtil.getSessionFactory().openSession();
			
			UtilisateurDao utilisateurDao = new UtilisateurDao(session);
			
			Utilisateur user = null;
			
			try {
				
				user = utilisateurDao.findById(connectedUser.getIdentifiant());
				
				if(user == null) {
					
					session.close();
					this.getServletContext().getRequestDispatcher("/AfficherProfil").
					forward(request, response);
					
				}
				
				connectedUser.setEmail(nouvelleEmail);
				user.setEmail(nouvelleEmail);
				utilisateurDao.save(user);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			
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

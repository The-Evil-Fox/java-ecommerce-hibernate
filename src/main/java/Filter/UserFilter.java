package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.UtilisateurDao;
import Model.*;

/**
 * Servlet Filter implementation class UserFilter
 */
@SuppressWarnings("serial")
@WebFilter("/*")
public class UserFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public UserFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession userSession = req.getSession();
		
		Utilisateur connectedUser = new Utilisateur();
		
		// on r�ecup`ere le nom de la session
		if(userSession.getAttribute("user") != null) {
			
			connectedUser = (Utilisateur) userSession.getAttribute("user");
			
		}
		
		String useremail = "";
		
		if(userSession.getAttribute("user") == null) {
			
			Cookie cookies [] = ((HttpServletRequest) request).getCookies();
			
			if(cookies != null) {
			
				for(int i= 0 ; i < cookies.length; i++) {
					
					if(cookies[i].getName().equals("useremail")) {
						
						useremail = cookies[i].getValue();
						break;
						
					}
				
				}
				
			}
			
		}
		
		if(!useremail.equals("")) {
			
			
			Session session = HibernateUtil.getSessionFactory().openSession();
		    
		    UtilisateurDao utilisateurDao = new UtilisateurDao(session);
			
			Utilisateur utilisateur = utilisateurDao.ConnectUserWithCookie(useremail);
			
			session.close();
			
			connectedUser.setIdentifiant(utilisateur.getIdentifiant());
			connectedUser.setNom(utilisateur.getNom());
			connectedUser.setPrenom(utilisateur.getPrenom());
			connectedUser.setEmail(utilisateur.getEmail());
			connectedUser.setPrivileges(utilisateur.getPrivileges());
			connectedUser.setAdresses(utilisateur.getAdresses());
			
			ListePanier listepanier = new ListePanier();
			userSession.setAttribute("listepanier", listepanier);
			userSession.setAttribute("user", connectedUser);
			
		}
		
		// on r�ecup`ere le chemin demand�e par l�utilisateur
		String chemin = req.getServletPath();
		// on r�ecup`ere la m�ethode HTTP utilis�ee (GET ou POST)
		String methode = req.getMethod();
		
		if(connectedUser.getNom() == null && (
				chemin.equals("/index.jsp")
				|| chemin.equals("/AfficherListe")
				|| chemin.equals("/CSS/style.css")
				|| chemin.equals("/inscription.jsp") 
				|| chemin.equals("/ConnexionUtilisateur") && methode.equals("POST")
				|| chemin.equals("/InscriptionUtilisateur") && methode.equals("POST"))) {
			
			chain.doFilter(request, response);
			
		} else if (connectedUser.getNom() != null && (!chemin.equals("/index.jsp") 
				&& !chemin.equals("/ConnexionUtilisateur") 
				&& !chemin.equals("/inscription.jsp") 
				&& !chemin.equals("/InscriptionUtilisateur"))) {
			
			chain.doFilter(request, response);
			
		} else {
			
			res.sendRedirect(req.getContextPath() + "/AfficherListe");
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

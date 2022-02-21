package Filter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import Model.*;

/**
 * Servlet Filter implementation class UserFilter
 */
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
			
			
			Configuration configuration = new Configuration().configure();
			SessionFactory sessionFactory = configuration.
			buildSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Utilisateur.class);
			List<Utilisateur> listeUtilisateurs = (List<Utilisateur>) criteria.list();
			
			for (int i = 0; i < listeUtilisateurs.size(); i++) {
				
				if(listeUtilisateurs.get(i).getEmail().equals(useremail)) {
					
					connectedUser.setNom(listeUtilisateurs.get(i).getNom());
					connectedUser.setPrenom(listeUtilisateurs.get(i).getPrenom());
					connectedUser.setEmail(listeUtilisateurs.get(i).getEmail());
					connectedUser.setPrivileges(listeUtilisateurs.get(i).getPrivileges());
					break;
					
				}
				
			}
			
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

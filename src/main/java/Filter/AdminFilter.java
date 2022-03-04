package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Model.Utilisateur;

/**
 * Servlet Filter implementation class AdminFilter
 */
@SuppressWarnings("serial")
@WebFilter("/*")
public class AdminFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AdminFilter() {
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
		// TODO Auto-generated method stub
		
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		// on r�ecup`ere le nom de la session
		
		
		// on r�ecup`ere le chemin demand�e par l�utilisateur
		String chemin = req.getServletPath();
		
		Utilisateur connectedUser = (Utilisateur) session.getAttribute("user");
		
		if(chemin.equals("/AjoutProduit") || chemin.equals("/ModifierProduit") 
				|| chemin.equals("/ModificationProduit") || chemin.equals("/ModifierMiseEnVente") 
				|| chemin.equals("AjouterCategorie")) {
		
			if(connectedUser == null) {
				
				res.sendRedirect(req.getContextPath());
				
			} else {
			
				if(connectedUser.getPrivileges() == 1) {
					
					chain.doFilter(request, response);
					
				} else {
					
					res.sendRedirect(req.getContextPath());
					
				}
				
			}
			
		} else {
			
			chain.doFilter(request, response);
			
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

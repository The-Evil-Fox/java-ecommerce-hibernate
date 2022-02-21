package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.ListePanier;
import Model.Panier;

/**
 * Servlet implementation class AffichePanier
 */
@WebServlet("/AffichePanier")
public class AffichePanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AffichePanier() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession userSession = request.getSession();
		
		ListePanier listepanier = new ListePanier();
		
		if(userSession.getAttribute("listepanier") != null) {
		
			listepanier = (ListePanier) userSession.getAttribute("listepanier");
			
		} else {
			
			userSession.setAttribute("listepanier", listepanier);
			
		}
        
		request.setAttribute("listepanier", listepanier);
        this.getServletContext().getRequestDispatcher("/WEB-INF/affichage-panier.jsp").
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

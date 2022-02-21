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
 * Servlet implementation class ModifierQuantitePanier
 */
@WebServlet("/ModifierQuantitePanier")
public class ModifierQuantitePanier extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierQuantitePanier() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int idPanier = Integer.parseInt(request.getParameter("id"));
		String operation = request.getParameter("operation");
		
		HttpSession userSession = request.getSession();
		
		ListePanier listepanier = new ListePanier();
		
		if(userSession.getAttribute("listepanier") != null) {
			
			listepanier = (ListePanier) userSession.getAttribute("listepanier");
			
		} else {
			
			userSession.setAttribute("listepanier", listepanier);
			
		}
		
		if(operation.equals("increment")) {
			
			for(Panier p : listepanier.getListe()) {
				
				if(p.getProduit().getIdentifiant() == idPanier) {
					
					p.setQuantite(p.getQuantite() +1);
					listepanier.setQuantiteTotale(listepanier.getQuantiteTotale() +1);
					listepanier.setMontantTotal(listepanier.getMontantTotal() + p.getProduit().getPrix());
					break;
					
				}
				
			}
			
		} else if(operation.equals("decrement")) {
			
			int compteur = 0;
			
			for(Panier p : listepanier.getListe()) {
				
				if(p.getProduit().getIdentifiant() == idPanier) {
					
					if(p.getQuantite() == 1) {
						
						listepanier.getListe().remove(compteur);
						listepanier.setQuantiteTotale(listepanier.getQuantiteTotale() -1);
						listepanier.setMontantTotal(listepanier.getMontantTotal() - p.getProduit().getPrix());
						break;
						
					} else {
					
						p.setQuantite(p.getQuantite() - 1);
						listepanier.setQuantiteTotale(listepanier.getQuantiteTotale() -1);
						listepanier.setMontantTotal(listepanier.getMontantTotal() - p.getProduit().getPrix());
						break;
						
					}
					
				}
				
				compteur++;
				
			}
			
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

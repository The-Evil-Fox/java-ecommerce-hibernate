package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import Config.HibernateUtil;
import DAO.*;
import Model.*;

/**
 * Servlet implementation class FinalisationCommande
 */
public class FinalisationCommande extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalisationCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		HttpSession userSession = request.getSession();
		
		Utilisateur connectedUser = (Utilisateur) userSession.getAttribute("user");
		ListePanier listepanier = (ListePanier) userSession.getAttribute("listepanier");
		
		if(listepanier.getListe().isEmpty()) {
			
			String errormessage = "Vous n'avez aucun article dans votre panier. Création d'une commande impossible !";
			request.setAttribute("erreur", (String) errormessage);
			this.getServletContext().getRequestDispatcher("/AffichePanier").
			forward(request, response);
			
		} else {
		
			if(connectedUser.getAdresses().isEmpty()) {
				
				String errormessage = "Veuillez ajouter une adresse de livraison avant de finaliser votre commande !";
				request.setAttribute("erreur", (String) errormessage);
				this.getServletContext().getRequestDispatcher("/AffichePanier").
				forward(request, response);
				
			}
			
			boolean adresseLivraisonIsSet = false;
			
			for(Adresse a : connectedUser.getAdresses()) {
				
				if(a.isAdresselivraison() == true) {
					
					adresseLivraisonIsSet = true;
					break;
					
				}
				
			}
			
			if(adresseLivraisonIsSet == false) {
				
				String errormessage = "Veuillez définir une adresse de livraison avant de finaliser votre commande !";
				request.setAttribute("erreur", (String) errormessage);
				this.getServletContext().getRequestDispatcher("/AffichePanier").
				forward(request, response);
				
			}
	        
	        Commande commande = new Commande();
	        commande.setPrixTotal(listepanier.getMontantTotal());
	        commande.setQuantite(listepanier.getQuantiteTotale());
	        
	        Session session = HibernateUtil.getSessionFactory().openSession();
			
			ArticleCommandeDao articleCommandeDao = new ArticleCommandeDao(session);
			UtilisateurDao utilisateurDao = new UtilisateurDao(session);
			ProduitDao produitDao = new ProduitDao(session);
			CommandeDao commandeDao = new CommandeDao(session);
			
			Utilisateur user = utilisateurDao.findById(connectedUser.getIdentifiant());
			
			connectedUser.addCommande(commande);
			
			try {
				
				commandeDao.save(commande);
				utilisateurDao.save(user);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
			Produit produitToUpdate = null;
	        
			for(Panier p : listepanier.getListe()) {
				
				try {
					
					ArticleCommande articleCommande = new ArticleCommande(commande, p.getProduit(), p.getQuantite(), p.getProduit().getPrix() * p.getQuantite());
					
					System.out.println(p.getProduit().getIdentifiant());
					
					produitToUpdate = produitDao.findById(p.getProduit().getIdentifiant());
					
					if(produitToUpdate == null) {
						
						session.close();
						this.getServletContext().getRequestDispatcher("/AfficherProfil").
						forward(request, response);
						
					}
					
					produitToUpdate.setQuantitestock(produitToUpdate.getQuantitestock() - p.getQuantite());
					
					System.out.println(produitToUpdate.toString());
					
					commande.addArticlesCommande(articleCommande);
					
					articleCommandeDao.save(articleCommande);
					produitDao.save(produitToUpdate);
					
				} catch (Exception e) {
					
					e.printStackTrace();
				
				}
				
			}
			
	        session.close();
			
			ListePanier emptyPanier = new ListePanier();
			userSession.setAttribute("listepanier", emptyPanier);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/validation-commande.jsp").forward(request, response);
			
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

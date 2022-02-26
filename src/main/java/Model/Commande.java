package Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Commande {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int identifiant;
	@OneToMany
	private List<ArticleCommande> articles = new ArrayList<ArticleCommande>();
	private int quantite;
	private double prixTotal;
	
	@ManyToOne
	Utilisateur utilisateur;
	
	public Commande() {
		
		super();
		
	}
	
	public Commande(int produit_identifiant, int quantite, double prixTotal) {
		super();
		this.quantite = quantite;
		this.prixTotal = prixTotal;
	}
	
	public Commande(Utilisateur utilisateur, int quantite, double prixTotal) {
		super();
		this.utilisateur = utilisateur;
		this.quantite = quantite;
		this.prixTotal = prixTotal;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public void setArticles(List<ArticleCommande> articles) {
		this.articles = articles;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(double prixTotal) {
		this.prixTotal = prixTotal;
	}

	public List<ArticleCommande> getArticles() {
		return articles;
	}

	public void addArticlesCommande(ArticleCommande articleCommande) {
		articles.add(articleCommande);
	}
	
	public void removeArticlesCommande(ArticleCommande articleCommande) {
		articles.remove(articleCommande);
	}

	@Override
	public String toString() {
		return "Commande [quantite=" + quantite
				+ ", prixTotal=" + prixTotal + "]";
	}
	
	

}

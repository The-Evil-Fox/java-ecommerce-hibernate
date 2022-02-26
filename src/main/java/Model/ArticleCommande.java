package Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ArticleCommande {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private int identifiant;
	@ManyToOne
	private Commande commande;
	@ManyToOne
	private Produit produit;
	private int quantite;
	private double prixtotal;
	
	public ArticleCommande() {
		
		
		
	}

	public ArticleCommande(Commande commande, Produit produit, int quantite, double prixtotal) {
		super();
		this.commande = commande;
		this.produit = produit;
		this.quantite = quantite;
		this.prixtotal = prixtotal;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public double getPrixtotal() {
		return prixtotal;
	}

	public void setPrixtotal(double prixtotal) {
		this.prixtotal = prixtotal;
	}

	@Override
	public String toString() {
		return "ArticlesCommande [commande=" + commande + ", produit=" + produit + ", quantite=" + quantite
				+ ", prixtotal=" + prixtotal + "]";
	}
	
	

}

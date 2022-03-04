package Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(
	name="findByPrice",
	query="SELECT p FROM Produit p WHERE p.prix > :minimum AND p.prix < :maximum"
)
public class Produit {   //classe public et non final
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
    private Integer        identifiant;
    private String    libelle;
    private String 	  cheminimage;
    private double    prix; // tous les champs sont private
    private int quantitestock;
    private boolean envente;
    
    @ManyToOne
    private CategorieProduit categorie;
    
    @OneToMany
    private List<ArticleCommande> articlesCommande;
    
	public Produit(Integer identifiant, String libelle, String cheminimage, double prix, int quantitestock, boolean envente) {
		super();
		this.identifiant = identifiant;
		this.libelle = libelle;
		this.cheminimage = cheminimage;
		this.prix = prix;
		this.quantitestock = quantitestock;
		this.envente = envente;
	}
    
    public Produit() {
    	super();
    }
    
    public Integer getIdentifiant() {
    	return this.identifiant;
    }
    
    public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCheminimage() {
		return cheminimage;
	}

	public void setCheminimage(String cheminimage) {
		this.cheminimage = cheminimage;
	}

	public double getPrix() {
		return prix;
	}

	public int getQuantitestock() {
		return quantitestock;
	}

	public void setQuantitestock(int quantitestock) {
		this.quantitestock = quantitestock;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public boolean isEnvente() {
		return envente;
	}

	public void setEnvente(boolean envente) {
		this.envente = envente;
	}
	
	public void setCategorie(CategorieProduit c) {
		this.categorie = c;
	}

	@Override
	public String toString() {
		return "Produit [identifiant=" + identifiant + ", libelle=" + libelle + ", cheminimage=" + cheminimage
				+ ", prix=" + prix + ", quantitestock=" + quantitestock + ", envente=" + envente + ", articlesCommande="
				+ articlesCommande + "]";
	}

}

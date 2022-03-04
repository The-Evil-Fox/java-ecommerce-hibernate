package Model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(
	name="findByCategorie",
	query="SELECT c FROM CategorieProduit c where c.categorie = :categorie"
)
public class CategorieProduit {
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer identifiant;
	private String categorie;
	@OneToMany
    private List<Produit> produits = new ArrayList<Produit>();
	
	public CategorieProduit() {
		
	}

	public CategorieProduit(Integer identifiant, String categorie) {
		super();
		this.identifiant = identifiant;
		this.categorie = categorie;
	}

	public Integer getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(Integer identifiant) {
		this.identifiant = identifiant;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	
	public void addProduit(Produit p) {
		produits.add(p);
		p.setCategorie(this);
	}

	@Override
	public String toString() {
		return "CategorieProduit [identifiant=" + identifiant + ", categorie=" + categorie
				+ "]";
	}

}

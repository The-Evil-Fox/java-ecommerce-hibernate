package Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(
	name="findByRue",
	query="SELECT a FROM Adresse a WHERE a.rue = :rue"
)
public class Adresse {
	
	@Id
	private String rue;
	private String codePostal;
	private String ville;
	private boolean adresselivraison;
	
	@ManyToOne
	Utilisateur utilisateur;
	
	public Adresse() {
		
		super();
		
	}
	
	public Adresse(String rue, String codePostal, String ville, boolean adresselivraison) {
		
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.setAdresselivraison(adresselivraison);
		
	}

	public Adresse(String rue, String codePostal, String ville, Utilisateur utilisateur, boolean adresselivraison) {
		super();
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.utilisateur = utilisateur;
		this.adresselivraison = adresselivraison;
		
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public boolean isAdresselivraison() {
		return adresselivraison;
	}

	public void setAdresselivraison(boolean adresselivraison) {
		this.adresselivraison = adresselivraison;
	}

	@Override
	public String toString() {
		return "Adresse [rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + ", utilisateur="
				+ utilisateur + "]";
	}

}

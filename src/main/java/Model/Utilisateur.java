package Model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(
		name="findByEmailAndPassword",
		query="SELECT u FROM Utilisateur u WHERE u.email = :email and u.password = :password"
	),
	@NamedQuery(
		name="findByEmail",
		query="SELECT u FROM Utilisateur u WHERE u.email = :email"
	)
})
public class Utilisateur {
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	private Integer identifiant;
	private String nom;
	private String prenom;
	private String password;
	private String email;
	private int privileges;
	@OneToMany(cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER, mappedBy="utilisateur")
	private List<Adresse> adresses = new ArrayList<Adresse>();
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy="utilisateur")
	private List<Commande> commandes = new ArrayList<Commande>();
	
	public Utilisateur() {
		
		super();
		
	}

	public Utilisateur(int identifiant, String nom, String prenom, String password, String email, int privileges,
			List<Adresse> adresses, List<Commande> commandes) {
		super();
		this.identifiant = identifiant;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.email = email;
		this.privileges = privileges;
		this.adresses = adresses;
		this.commandes = commandes;
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPrivileges() {
		return privileges;
	}

	public void setPrivileges(int privileges) {
		this.privileges = privileges;
	}

	public List<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}
	
	public void addAdresse(Adresse adresse) {
		adresses.add(adresse);
		adresse.setUtilisateur(this);
	}
	
	public void removeAdresse(Adresse adresse) {
		adresses.remove(adresse);
	}

	public List<Commande> getCommandes() {
		return commandes;
	}

	public void setCommandes(List<Commande> commandes) {
		this.commandes = commandes;
	}
	
	public void addCommande(Commande commande) {
		commandes.add(commande);
		commande.setUtilisateur(this);
	}
	
	public void removeCommande(Commande commande) {
		commandes.remove(commande);
	}

	@Override
	public String toString() {
		return "Utilisateur [identifiant=" + identifiant + ", nom=" + nom + ", prenom=" + prenom + ", password="
				+ password + ", email=" + email + ", privileges=" + privileges + ", adresses=" + adresses
				+ ", commandes=" + commandes + "]";
	}
	
	
	
}

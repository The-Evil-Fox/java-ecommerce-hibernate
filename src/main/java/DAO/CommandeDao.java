package DAO;

import Model.Commande;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CommandeDao extends GenericDao<Commande,Integer> {
	
	public CommandeDao(Session session) {
	
		super(Commande.class, session);
		
	}

	@SuppressWarnings("unchecked")
	public List<Commande> getCommandesFromUser(int id) {
		
		Query query = session.getNamedQuery("findByUserId");
		query.setParameter("id", id);
		List<Commande> commandes = new ArrayList<Commande>();
		commandes = (List<Commande>) query.list();
		
		return commandes;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getDetailsCommande(int idcommande, int iduser) {
		
		String SQLQuery = "SELECT p.libelle, p.prix, p.cheminimage, ac.prixtotal, ac.quantite FROM produit p"
				+ "	LEFT JOIN articlecommande ac on p.identifiant = ac.produit_identifiant"
				+ " LEFT JOIN commande c on ac.commande_identifiant = c.identifiant"
				+ " WHERE ac.commande_identifiant = :idcommande AND c.utilisateur_identifiant = :iduser";
		
		Query query = session.createSQLQuery(SQLQuery);
		query.setParameter("idcommande", idcommande);
		query.setParameter("iduser", iduser);
		List<Object[]> donnees = new ArrayList<Object[]>();
		donnees = (List<Object[]>) query.list();
		
		return donnees;
		
	}

}
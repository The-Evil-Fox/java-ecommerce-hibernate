package DAO;

import Model.Produit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class ProduitDao extends GenericDao<Produit,Integer> {
	
	public ProduitDao(Session session) {
	
		super(Produit.class, session);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> findByCategorie(int categorie, double minimum, double maximum) {
		
		String sqlQuery = "SELECT p.identifiant, p.prix, p.quantitestock, p.cheminimage, p.envente, p.libelle "
				+ "FROM categorieproduit_produit cpp LEFT JOIN produit p ON cpp.produits_identifiant = p.identifiant "
				+ "WHERE cpp.categorieproduit_identifiant = :categorie and p.prix >= :minimum and p.prix <= :maximum";
		
		Query query = session.createSQLQuery(sqlQuery);
		query.setParameter("categorie", categorie);
		query.setParameter("minimum", minimum);
		query.setParameter("maximum", maximum);
		List<Object[]> produits = new ArrayList<Object[]>();
		produits = (List<Object[]>) query.list();
		
		return produits;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Produit> findByPrice(double minimum, double maximum) {
		
		Query query = session.getNamedQuery("findByPrice");
		query.setParameter("minimum", minimum);
		query.setParameter("maximum", maximum);
		List<Produit> produits = new ArrayList<Produit>();
		produits = (List<Produit>) query.list();
		
		return produits;
		
	}

	

}
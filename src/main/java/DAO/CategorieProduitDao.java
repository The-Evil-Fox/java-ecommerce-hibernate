package DAO;

import Model.CategorieProduit;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class CategorieProduitDao extends GenericDao<CategorieProduit,Integer> {
	
public CategorieProduitDao(Session session) {
	
		super(CategorieProduit.class, session);
		
	}

	@SuppressWarnings("unchecked")
	public CategorieProduit findByCategorie(String categorie) {
		
		Query query = session.getNamedQuery("findByCategorie");
		query.setParameter("categorie", categorie);
		List<CategorieProduit> categories = new ArrayList<CategorieProduit>();
		categories = (List<CategorieProduit>) query.list();
		
		if(categories.isEmpty()) {
			
			return null;
			
		}
		
		return categories.get(0);
		
	}

}
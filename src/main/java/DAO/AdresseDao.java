package DAO;

import Model.Adresse;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class AdresseDao extends GenericDao<Adresse, String> {
	
public AdresseDao(Session session) {
	
		super(Adresse.class, session);
	
	}

	@SuppressWarnings("unchecked")
	public Adresse findByRue(String rue) {
		
		Query query = session.getNamedQuery("findByRue");
		query.setParameter("rue", rue);
		List<Adresse> adresse = new ArrayList<Adresse>();
		adresse = (List<Adresse>) query.list();
		
		if(adresse.isEmpty()) {
			
			return null;
			
		}
		
		return adresse.get(0);
		
	}

}
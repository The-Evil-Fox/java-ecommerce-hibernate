package DAO;

import Model.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

public class UtilisateurDao extends GenericDao<Utilisateur,Integer> {
	
public UtilisateurDao(Session session) {
	
		super(Utilisateur.class, session);
		
	}

	@SuppressWarnings("unchecked")
	public Utilisateur connectUser(String email, String password) {
		
		Query query = session.getNamedQuery("findByEmailAndPassword");
		query.setParameter("email", email);
		query.setParameter("password", password);
		List<Utilisateur> utilisateur = new ArrayList<Utilisateur>();
		utilisateur = (List<Utilisateur>) query.list();
		
		if(utilisateur.isEmpty()) {
			
			return null;
			
		}
		
		return utilisateur.get(0);
		
	}
	
	@SuppressWarnings("unchecked")
	public Utilisateur ConnectUserWithCookie(String email) {
		
		Query query = session.getNamedQuery("findByEmail");
		query.setParameter("email", email);
		List<Utilisateur> utilisateur = new ArrayList<Utilisateur>();
		utilisateur = (List<Utilisateur>) query.list();
		
		if(utilisateur.isEmpty()) {
			
			return null;
			
		}
		
		return utilisateur.get(0);
		
	}

}
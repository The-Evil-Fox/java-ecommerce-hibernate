package DAO;

import Model.ArticleCommande;
import org.hibernate.Session;

public class ArticleCommandeDao extends GenericDao<ArticleCommande, String> {
	
public ArticleCommandeDao(Session session) {
	
		super(ArticleCommande.class, session);
	
	}

}
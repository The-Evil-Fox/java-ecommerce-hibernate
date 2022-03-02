package Config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtil {
	
	@SuppressWarnings("unused")
	private static SessionFactory sessionFactory;
	public static SessionFactory getSessionFactory() {
	Configuration configuration = new Configuration().configure();
	
	return configuration.buildSessionFactory();
	
	}
	
}
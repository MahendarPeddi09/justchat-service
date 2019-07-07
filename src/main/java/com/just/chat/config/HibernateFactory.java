package com.just.chat.config;



import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class HibernateFactory {
	
	private static SessionFactory sessionFactory = null;
	
	private HibernateFactory() {
		//sessionFactory = buildSessionFactory();
	}

	
	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		// TODO Auto-generated method stub
		if (sessionFactory == null) {
		try {
            // Create the SessionFactory from hibernate.cfg.xml
			
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
 
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
		
		}
		else return sessionFactory;
    }
 
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
        	sessionFactory = buildSessionFactory();
        }
        	return sessionFactory;
    }
    
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
	
}

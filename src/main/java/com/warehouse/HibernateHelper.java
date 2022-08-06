package com.warehouse;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateHelper {

	private static SessionFactory sessionFactory = null;
	
	private static SessionFactory buildSessionFactory() 
	{
		try 
		{
			if (sessionFactory == null) 
			{
				StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					//.configure("hibernate.cfg.xml").build();
					.configure().build();
				
				Metadata metaData = new MetadataSources(standardRegistry)
						.getMetadataBuilder()
						.build();
				
				sessionFactory = metaData.getSessionFactoryBuilder().build();
			}
			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {

		if(sessionFactory == null)
			sessionFactory = buildSessionFactory();

		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
		sessionFactory = null;
	}
}
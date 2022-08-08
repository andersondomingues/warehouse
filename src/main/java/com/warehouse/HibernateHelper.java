package com.warehouse;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateHelper {

	private static SessionFactory sessionFactory = null;

	private static SessionFactory buildSessionFactory()
	{
		try
		{
			if (sessionFactory == null)
			{
				StringBuilder builder = new StringBuilder();
				builder.append("jdbc:postgresql://")
					.append(System.getProperty("hibernate_db_host"))
					.append("/")
					.append(System.getProperty("hibernate_db_name"));

				Configuration cfg = new Configuration();
				cfg.setProperty("hibernate.connection.url", builder.toString());
				
				cfg.setProperty("hibernate.connection.username", 
					System.getProperty("hibernate_db_user"));
				
				cfg.setProperty("hibernate.connection.password", 
					System.getProperty("hibernate_db_pass"));

				StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure()
					.applySettings(cfg.getProperties())
					.build();

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
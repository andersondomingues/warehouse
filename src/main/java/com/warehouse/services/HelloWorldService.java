package com.warehouse.services;

import org.hibernate.Session;
import com.warehouse.models.HelloWorld;
import com.warehouse.HibernateHelper;

public class HelloWorldService {

	public HelloWorldService(){

  }

  public HelloWorld addHelloWorld(String hello, String world){

    Session session = HibernateHelper.getSessionFactory().openSession();
    session.getTransaction().begin();

		HelloWorld hwmodel = new HelloWorld(hello, world);
    session.persist(hwmodel);

		session.getTransaction().commit();
    HibernateHelper.shutdown();
    
    return hwmodel;
  }
}
package com.warehouse.services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.warehouse.models.StorageItem;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.warehouse.HibernateHelper;

public class StorageItemService {

	public StorageItemService(){

  }

  public List<StorageItem> getStorageItems(){

    Session session = HibernateHelper.getSessionFactory().openSession();


    System.out.println(">>>>>>>" + session.toString());

    CriteriaBuilder cb = session.getCriteriaBuilder();
    CriteriaQuery<StorageItem> cr = cb.createQuery(StorageItem.class);
    Root<StorageItem> root = cr.from(StorageItem.class);
    cr.select(root);
    
    Query<StorageItem> query = session.createQuery(cr);
    List<StorageItem> results = query.getResultList();

    HibernateHelper.shutdown();
    
    return results;
  }

  public void putStorageItems(StorageItem item){
		
    Session session = HibernateHelper.getSessionFactory().openSession();
    session.getTransaction().begin();

    session.persist(item);

		session.getTransaction().commit();
    HibernateHelper.shutdown();
	}
}
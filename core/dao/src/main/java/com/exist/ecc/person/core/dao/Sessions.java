package com.exist.ecc.person.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exist.ecc.person.infra.persistence.DefaultHibernateConfiguration;

public class Sessions {

  private static final SessionFactory sessionFactory = buildSessionFactory();
  
  private static SessionFactory buildSessionFactory() {
    try {
      DefaultHibernateConfiguration config = new DefaultHibernateConfiguration();
      
      return config.getSessionFactory();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static Session getSession() {
    return getSessionFactory().openSession();
  }

}
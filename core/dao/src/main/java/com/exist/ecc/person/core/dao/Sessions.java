package com.exist.ecc.person.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Sessions {

  private static final SessionFactory sessionFactory = buildSessionFactory();
  
  private static SessionFactory buildSessionFactory() {
    try {
      return HibernateSetup.getConfiguration()
             .buildSessionFactory(HibernateSetup.getServiceRegistry());
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
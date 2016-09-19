package com.exist.ecc.person.core.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.exist.ecc.person.infra.persistence.DefaultHibernateConfiguration;

public class Sessions {

  private SessionFactory sessionFactory;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  public Session getSession() {
    return sessionFactory.openSession();
  }

}
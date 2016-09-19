package com.exist.ecc.person.infra.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DefaultHibernateConfiguration extends Configuration {
  private SessionFactory sessionFactory;

  public DefaultHibernateConfiguration() {
    this.setProperty("hibernate.hikari.dataSource.serverName", System.getenv("HIBERNATE_HIKARI_DS_SERVER"));
    this.setProperty("hibernate.hikari.dataSource.databaseName", System.getenv("HIBERNATE_HIKARI_DS_DB"));
    this.setProperty("hibernate.hikari.dataSource.portNumber", System.getenv("HIBERNATE_HIKARI_DS_PORT"));
    this.setProperty("hibernate.hikari.dataSource.user", System.getenv("HIBERNATE_HIKARI_DS_USER"));
    this.setProperty("hibernate.hikari.dataSource.password", System.getenv("HIBERNATE_HIKARI_DS_PASS"));

    this.configure();

    try {
      setSessionFactory(buildSessionFactory(getServiceRegistry()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  private ServiceRegistry getServiceRegistry() {
    ServiceRegistry serviceRegistry =
      new StandardServiceRegistryBuilder().applySettings(getProperties())
                                          .build();

    return serviceRegistry;
  }

}
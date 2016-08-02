package com.exist.ecc.person.util;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final SessionFactory sessionFactory = buildSessionFactory();
  
  private static SessionFactory buildSessionFactory() {
    try {
      Configuration configuration = 
        new Configuration()
            .setProperty("hibernate.connection.url", System.getenv("DB_URL"))
            .setProperty("hibernate.connection.username", System.getenv("DB_USER"))
            .setProperty("hibernate.connection.password", System.getenv("DB_PASS"));
      
      configuration.configure();

      ServiceRegistry serviceRegistry = 
        new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();

      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("there was an error building the factory");
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
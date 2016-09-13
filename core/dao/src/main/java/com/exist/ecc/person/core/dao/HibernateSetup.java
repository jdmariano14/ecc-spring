package com.exist.ecc.person.core.dao;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSetup {

  public static Configuration getConfiguration() {
    Configuration configuration = 
      new Configuration()
      .setProperty("hibernate.connection.url", System.getenv("DB_URL"))
      .setProperty("hibernate.connection.username", System.getenv("DB_USER"))
      .setProperty("hibernate.connection.password", System.getenv("DB_PASS"))
      .setProperty("hibernate.enable_lazy_load_no_trans", "true");

    configuration.configure();

    return configuration;
  }

  public static ServiceRegistry getServiceRegistry() {
    ServiceRegistry serviceRegistry =
      new StandardServiceRegistryBuilder()
      .applySettings(getConfiguration().getProperties())
      .build();

    return serviceRegistry;
  }

}
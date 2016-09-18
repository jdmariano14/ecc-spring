package com.exist.ecc.person.core.dao;

import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.exist.ecc.person.infra.persistence.HibernateConfig;

public class HibernateSetup {

  public static Configuration getConfiguration() {
    return HibernateConfig.get();
  }

  public static ServiceRegistry getServiceRegistry() {
    ServiceRegistry serviceRegistry =
      new StandardServiceRegistryBuilder()
      .applySettings(getConfiguration().getProperties())
      .build();

    return serviceRegistry;
  }

}
package com.exist.ecc.person.infra.persistence;

import org.hibernate.cfg.Configuration;

public class HibernateConfig {

  public static Configuration get() {
    Configuration configuration = 
      new Configuration()
      .setProperty("hibernate.hikari.dataSource.serverName", System.getenv("HIBERNATE_HIKARI_DS_SERVER"))
      .setProperty("hibernate.hikari.dataSource.databaseName", System.getenv("HIBERNATE_HIKARI_DS_DB"))
      .setProperty("hibernate.hikari.dataSource.portNumber", System.getenv("HIBERNATE_HIKARI_DS_PORT"))
      .setProperty("hibernate.hikari.dataSource.user", System.getenv("HIBERNATE_HIKARI_DS_USER"))
      .setProperty("hibernate.hikari.dataSource.password", System.getenv("HIBERNATE_HIKARI_DS_PASS"));

    configuration.configure();

    return configuration;
  }

}
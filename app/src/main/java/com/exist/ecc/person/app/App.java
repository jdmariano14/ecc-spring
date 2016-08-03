package com.exist.ecc.person.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.util.HibernateUtil;
import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.PromptUtil;
import com.exist.ecc.person.util.InvalidInputStrategy;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.RoleHibernateDao;
import com.exist.ecc.person.core.service.validation.RoleValidator;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;

public class App {
  private static Session session;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    PromptUtil.setScanner(scanner);
    
    String[] options = {
      "add role", "exit"
    };

    try {
      session = HibernateUtil.getSessionFactory().openSession();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Database connection failed.");
    }

    try {
      while (appLoop(options)) {
        continue;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      HibernateUtil.getSessionFactory().close();
    }

  }

  private static boolean appLoop(String[] options) {
    System.out.println("Select an action:");
    System.out.println(MenuUtil.getMenu(options));

    int choice = PromptUtil.promptForInt("");

    if (choice < 1 || choice > options.length) {
      return true;
    }

    switch (options[choice - 1]) {
      case "add role":
        addRole();
        break;
      case "exit":
        return false;
    }

    return true;
  }

  private static void addRole() {
    Transaction transaction;
    List<String> errors = new ArrayList();

    Role role = new Role();
    
    String name = PromptUtil.promptForValidField("role name", RoleValidator::validateName, InvalidInputStrategy.THROW_EXCEPTION);
    
    role.setName(name);

    transaction = session.beginTransaction();
    session.save(role);
    transaction.commit();
  }

  private static void diene() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    PersonDao personDao = new PersonHibernateDao(session);

    try {
      Transaction transaction = session.beginTransaction();
      
      Person diene = new Person();
      Name name = new Name();
      Address address = new Address();

      diene.setBirthDate(new Date());
      diene.setGwa(new BigDecimal("1.77"));
      diene.setDateHired(new Date());
      diene.setEmployed(false);

      name.setFirstName("Diene");
      name.setTitle("Dr.");

      address.setStreetAddress("33 N, SF");
      address.setBarangay("DM");
      address.setMunicipality("QC");

      diene.setName(name);
      diene.setAddress(address);

      personDao.save(diene);
      
      transaction.commit();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      session.close();
      HibernateUtil.getSessionFactory().close();
    }
  }

}

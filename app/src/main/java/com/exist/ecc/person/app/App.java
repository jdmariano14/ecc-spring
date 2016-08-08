package com.exist.ecc.person.app;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import java.lang.reflect.Method;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExtractor;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.RoleInputWizard;
import com.exist.ecc.person.core.service.input.impl.ConsoleInputExtractor;
import com.exist.ecc.person.core.service.input.impl.RepeatExtractionExceptionHandler;

import com.exist.ecc.person.util.SessionUtil;
import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

import com.exist.ecc.person.core.dao.Transactions;

import com.exist.ecc.person.core.service.validation.Validations;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.RoleHibernateDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;

public class App {
  private static boolean exit;
  private static Scanner scanner;
  private static InputExtractor extractor;
  private static InputExceptionHandler handler;

  static {
    scanner = new Scanner(System.in);
    extractor = new ConsoleInputExtractor(scanner);
    handler = new RepeatExtractionExceptionHandler();
  }

  public static void main(String[] args) {

    String[] options = {
      "createPerson",
      "addRole", "updateRole", "deleteRole", "listRole",
      "exit"
    };


    try {
      while (!exit) {
        appLoop(options);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      SessionUtil.getSessionFactory().close();
    }

  }

  private static void appLoop(String[] options) {
    Consumer<Integer> validation = 
      i -> {
        if (i < 1 || i > options.length) {
          throw new IllegalArgumentException(
            "Input does not correspond to an option");
        }
      };

    String menuPrompt = 
      new StringBuilder(System.lineSeparator())
          .append("Select an action:")
          .append(System.lineSeparator())
          .append(MenuUtil.getMenu(options, uncamelcase()))
          .append(System.lineSeparator())
          .toString();

    int choice = 
      new InputService.Builder<Integer>(extractor, handler)
          .message(menuPrompt)
          .conversion(Integer::parseInt)
          .validation(validation)
          .build().getInput();

    try {
      App.class.getDeclaredMethod(options[choice - 1]).invoke(App.class);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
    
  }

  private static void exit() {
    exit = true;
  }

  private static void createPerson() {

  }

  private static void addRole() {
    final RoleDao roleDao = new RoleHibernateDao();
    final Role role = new Role();
    RoleInputWizard wizard = new RoleInputWizard(extractor, handler);

    wizard.batchProcessMessages(standardPropertyMessage());
    wizard.setProperties(role);

    Transactions.conduct(roleDao, () -> { 
      roleDao.save(role);
    });
  }

  private static void updateRole() {
    final RoleDao roleDao = new RoleHibernateDao();

    long id =
      new InputService.Builder<Long>(extractor, handler)
          .message("Enter role ID: ")
          .conversion(Long::parseLong)
          .build().getInput();

    Transactions.conduct(roleDao, () -> {
      final Role role = roleDao.get(id);
      RoleInputWizard wizard = new RoleInputWizard(extractor, handler);
      
      wizard.batchProcessMessages(standardPropertyMessage());
      wizard.setProperties(role);

      roleDao.save(role);
    });
  }

  private static void deleteRole() {
    RoleDao roleDao = new RoleHibernateDao();

    long id = 
      new InputService.Builder<Long>(extractor, handler)
          .message("Enter role ID: ")
          .conversion(Long::parseLong)
          .build().getInput();

    Transactions.conduct(roleDao, () -> { 
      Role role = roleDao.get(id);
      String confirmMsg = 
        new StringBuilder("Confirm delete of role '")
            .append(role.getName())
            .append("' (y/n): ")
            .toString();
      String choice = 
        new InputService.Builder<String>(extractor, handler)
            .message(confirmMsg)
            .build().getInput();

      if (choice.equalsIgnoreCase("Y")) {
        roleDao.delete(role);
      }
    });
  }

  private static void listRole() {
    Role role = new Role();
    RoleDao roleDao = new RoleHibernateDao();

    Transactions.conduct(roleDao, () -> { 
      roleDao.getAll().forEach(System.out::println); 
    });
  }

  private static UnaryOperator<String> uncamelcase() {
    return s -> {
      return StringUtil.capitalize(s);
    };
  }

  private static UnaryOperator<String> standardPropertyMessage() {
    return s -> {
      StringBuilder sb = new StringBuilder()
                         .append(uncamelcase().apply(s))
                         .append(": ");

      return sb.toString();
    };
  }

}

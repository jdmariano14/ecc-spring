package com.exist.ecc.person.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;


import com.exist.ecc.person.core.service.io.InputService;
import com.exist.ecc.person.core.service.io.api.InputExtractor;
import com.exist.ecc.person.core.service.io.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.io.impl.ConsoleInputExtractor;
import com.exist.ecc.person.core.service.io.impl.RepeatExtractionExceptionHandler;

import com.exist.ecc.person.util.SessionUtil;
import com.exist.ecc.person.util.MenuUtil;

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
      "add role", "update role", "delete role", "list role", "exit"
    };

    try {
      while (appLoop(options)) {
        continue;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      SessionUtil.getSessionFactory().close();
    }

  }

  private static boolean appLoop(String[] options) {
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
          .append(MenuUtil.getMenu(options))
          .append(System.lineSeparator())
          .toString();
    int choice = 
      new InputService.Builder<Integer>(extractor, handler)
          .message(menuPrompt)
          .conversion(Integer::parseInt)
          .validation(validation)
          .build().getInput();

    switch (options[choice - 1]) {
      case "add role":
        addRole();
        break;
      case "update role":
        updateRole();
        break;
      case "delete role":
        deleteRole();
        break;
      case "list role":
        listRole();
        break;
      case "exit":
        return false;
    }

    return true;
  }

  private static void addRole() {
    Role role = new Role();
    RoleDao roleDao = new RoleHibernateDao();

    String name =
      new InputService.Builder<String>(extractor, handler)
          .message("Enter role name: ")
          .validation(Validations.get(Role.class, "name"))
          .build().getInput();

    role.setName(name);

    Transactions.conduct(roleDao, () -> { 
      roleDao.save(role);
    });
  }

  private static void updateRole() {
    RoleDao roleDao = new RoleHibernateDao();

    long id =
      new InputService.Builder<Long>(extractor, handler)
          .message("Enter role ID: ")
          .conversion(Long::parseLong)
          .build().getInput();

    Transactions.conduct(roleDao, () -> { 
      Role role = roleDao.get(id);
      String defaultValue = role.getName();

      String name = 
        new InputService.Builder<String>(extractor, handler)
            .message("Enter role name: ")
            .validation(Validations.get(Role.class, "name"))
            .build().getInput();
      
      role.setName(name);
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

}

package com.exist.ecc.person.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.exist.ecc.person.app.impl.AbstractEntityManager;
import com.exist.ecc.person.app.impl.ContactManager;
import com.exist.ecc.person.app.impl.PersonManager;
import com.exist.ecc.person.app.impl.PersonRoleManager;
import com.exist.ecc.person.app.impl.RoleManager;

import com.exist.ecc.person.core.dao.Sessions;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.ConsoleReader;
import com.exist.ecc.person.core.service.input.impl.RepeatReadHandler;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.ConsoleWriter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

public class App {

  private static boolean exit;
  private static Scanner scanner;
  private static InputReader reader;
  private static InputExceptionHandler handler;
  private static OutputWriter writer;
  private static Map<String, AbstractEntityManager> entityManagers;

  private static String[] options = {
    "createPerson", "updatePerson", "deletePerson", "listPerson",
    "createRole", "updateRole", "deleteRole", "listRole",
    "createContact", "updateContact", "deleteContact",
    "createPersonRole", "deletePersonRole",
    "exit"
  };

  static {
    scanner = new Scanner(System.in);
    reader = new ConsoleReader(scanner);
    writer = new ConsoleWriter();
    handler = new RepeatReadHandler();
    entityManagers = new HashMap();

    entityManagers.put("Person", new PersonManager(reader, writer, handler));
    entityManagers.put("Role", new RoleManager(reader, writer, handler));
    entityManagers.put(
      "Contact", new ContactManager(reader, writer, handler));
    entityManagers.put(
      "PersonRole", new PersonRoleManager(reader, writer, handler));
  }

  public static void main(String[] args) {
    try {
      while (!exit) {
        appLoop(options);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      Sessions.getSessionFactory().close();
    }
  }

  private static void appLoop(String[] options) {
    String menuPrompt = 
      new StringBuilder(System.lineSeparator())
          .append("Select an action:")
          .append(System.lineSeparator())
          .append(MenuUtil.getMenu(options, AppUtil::defaultTransform))
          .append(System.lineSeparator())
          .toString();

    int choice = 
      new InputService.Builder<Integer>(reader, handler)
          .message(menuPrompt)
          .conversion(Integer::parseInt)
          .validation(AppUtil.optionValidation(options.length))
          .build().getInput();

    String action = options[choice - 1];

    try {
      if (action.equals("exit")) {
        exit = true;
      } else {
        String entityMethod = 
          action.substring(0, StringUtil.indexOfPattern(action, "[A-Z]"));
        String entityClass = 
          action.substring(StringUtil.indexOfPattern(action, "[A-Z]"));
        AbstractEntityManager entityManager = 
          entityManagers.get(entityClass);

        entityManager.getClass().getDeclaredMethod(entityMethod)
                     .invoke(entityManager);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
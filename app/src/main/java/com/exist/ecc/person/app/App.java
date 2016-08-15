package com.exist.ecc.person.app;

import java.util.Scanner;

import com.exist.ecc.person.app.impl.AbstractEntityManager;
import com.exist.ecc.person.app.impl.ContactManager;
import com.exist.ecc.person.app.impl.PersonManager;
import com.exist.ecc.person.app.impl.PersonRoleManager;
import com.exist.ecc.person.app.impl.RoleManager;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.ConsoleReader;
import com.exist.ecc.person.core.service.input.impl.RepeatReadHandler;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.ConsoleWriter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.SessionUtil;
import com.exist.ecc.person.util.StringUtil;

public class App {
  private static boolean exit;
  private static Scanner scanner;
  private static InputReader reader;
  private static InputExceptionHandler handler;
  private static OutputWriter writer;

  private static PersonManager persons;
  private static RoleManager roles;
  private static ContactManager contacts;
  private static PersonRoleManager personRoles;

  static {
    scanner = new Scanner(System.in);
    reader = new ConsoleReader(scanner);
    handler = new RepeatReadHandler();
    writer = new ConsoleWriter();

    persons = new PersonManager();
    roles = new RoleManager();
    contacts = new ContactManager();
    personRoles = new PersonRoleManager();

    assignIoClasses(persons);
    assignIoClasses(roles);
    assignIoClasses(contacts);
    assignIoClasses(personRoles);
  }

  public static void main(String[] args) {

    String[] options = {
      "createPerson", "updatePerson", "deletePerson", "listPerson",
      "createRole", "updateRole", "deleteRole", "listRole",
      "createContact", "updateContact", "deleteContact",
      "createPersonRole", "deletePersonRole",
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

    try {
      App.class.getDeclaredMethod(options[choice - 1]).invoke(App.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } 
  }

  private static void exit() {
    exit = true;
  }

  private static void createPerson() {
    persons.create();
  }

  private static void updatePerson() {
    persons.update();
  }

  private static void deletePerson() {
    persons.delete();
  }

  private static void listPerson() {
    persons.list();
  }

  private static void createRole() {
    roles.create();
  }

  private static void updateRole() {
    roles.update();
  }

  private static void deleteRole() {
    roles.delete();
  }

  private static void listRole() {
    roles.list();
  }

  private static void createContact() {
    contacts.create();
  }

  private static void updateContact() {
    contacts.update();
  }

  private static void deleteContact() {
    contacts.delete();
  }

  private static void createPersonRole() {
    personRoles.create();
  }

  private static void deletePersonRole() {
    personRoles.delete();
  }

  private static void assignIoClasses(AbstractEntityManager manager) {
    manager.setReader(reader);
    manager.setHandler(handler);
    manager.setWriter(writer);
  }

}
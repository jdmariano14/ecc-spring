package com.exist.ecc.person.app;

import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;
import com.exist.ecc.person.core.dao.impl.RoleHibernateDao;

import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.AddressInputWizard;
import com.exist.ecc.person.core.service.input.impl.ConsoleInputReader;
import com.exist.ecc.person.core.service.input.impl.NameInputWizard;
import com.exist.ecc.person.core.service.input.impl.PersonInputWizard;
import com.exist.ecc.person.core.service.input.impl.RepeatExtractionExceptionHandler;
import com.exist.ecc.person.core.service.input.impl.RoleInputWizard;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.ConsoleOutputWriter;
import com.exist.ecc.person.core.service.output.impl.PersonOutputFormatter;
import com.exist.ecc.person.core.service.validation.Validations;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.SessionUtil;
import com.exist.ecc.person.util.StringUtil;

public class App {
  private static boolean exit;
  private static Scanner scanner;
  private static InputReader reader;
  private static InputExceptionHandler handler;

  static {
    scanner = new Scanner(System.in);
    reader = new ConsoleInputReader(scanner);
    handler = new RepeatExtractionExceptionHandler();
  }

  public static void main(String[] args) {

    String[] options = {
      "createPerson", "updatePerson", "deletePerson", "listPerson",
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
          .append(MenuUtil.getMenu(options, App::defaultTransform))
          .append(System.lineSeparator())
          .toString();

    int choice = 
      new InputService.Builder<Integer>(reader, handler)
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
    final PersonDao personDao = new PersonHibernateDao();
    final Person person = new Person();
    final Name name = new Name();
    final Address address = new Address();
    
    setPersonFields(person, name, address);

    Transactions.conduct(personDao, () -> { 
      personDao.save(person);
    });
  }

  private static void updatePerson() {
    final PersonDao personDao = new PersonHibernateDao();

    long id = getId("person");

    Transactions.conduct(personDao, () -> {
      final Person person = personDao.get(id);
      final Name name = person.getName();
      final Address address = person.getAddress();
      
      setPersonFields(person, name, address);
      personDao.save(person);
    });
  }

  private static void deletePerson() {
    final PersonDao personDao = new PersonHibernateDao();

    long id = getId("person");

    Transactions.conduct(personDao, () -> { 
      final Person person = personDao.get(id);
      
      if (getDeleteConfirmation("person", person.toString())) {
        personDao.delete(person);
      }
    });
  }

  private static void listPerson() {
    final PersonDao personDao = new PersonHibernateDao();

    Transactions.conduct(personDao, () -> { 
      OutputWriter writer = new ConsoleOutputWriter();
      OutputFormatter formatter = new PersonOutputFormatter();

      personDao.getAll().forEach(p -> {
        writer.write(formatter.format(p));
      }); 
    });
  }

  private static void addRole() {
    final RoleDao roleDao = new RoleHibernateDao();
    final Role role = new Role();
    
    setRoleFields(role);

    Transactions.conduct(roleDao, () -> { 
      roleDao.save(role);
    });
  }

  private static void updateRole() {
    final RoleDao roleDao = new RoleHibernateDao();

    long id = getId("role");

    Transactions.conduct(roleDao, () -> {
      final Role role = roleDao.get(id);
      
      setRoleFields(role);
      roleDao.save(role);
    });
  }

  private static void deleteRole() {
    final RoleDao roleDao = new RoleHibernateDao();
    
    long id = getId("role");

    Transactions.conduct(roleDao, () -> { 
      final Role role = roleDao.get(id);
      
      if (getDeleteConfirmation("role", role.toString())) {
        roleDao.delete(role);
      }
    });
  }

  private static void listRole() {
    final RoleDao roleDao = new RoleHibernateDao();

    Transactions.conduct(roleDao, () -> { 
      roleDao.getAll().forEach(System.out::println); 
    });
  }

  private static long getId(String entityClass) {
    StringBuilder msg = new StringBuilder("Enter ")
                        .append(entityClass)
                        .append(" ID: ");

    long id = new InputService.Builder<Long>(reader, handler)
              .message(msg.toString())
              .conversion(Long::parseLong)
              .build().getInput();

    return id;
  }

  private static boolean getDeleteConfirmation(String entityClass,
    String entityString)
  {
    String confirmMsg = 
      new StringBuilder("Confirm delete of ")
          .append(entityClass)
          .append(" '")
          .append(entityString)
          .append("' (y/n): ")
          .toString();

    String choice = 
      new InputService.Builder<String>(reader, handler)
          .message(confirmMsg)
          .build().getInput();

    return choice.equalsIgnoreCase("y");
  }

  private static void setPersonFields(Person person, Name name,
    Address address) 
  {
    PersonInputWizard personWizard = 
      new PersonInputWizard(reader, handler);
    NameInputWizard nameWizard = new NameInputWizard(reader, handler);
    AddressInputWizard addressWizard = 
      new AddressInputWizard(reader, handler);

    BiFunction<String, Object, String> defaultFormat = (s, o) -> {
      return o == null
             ? String.format("%s: ", defaultTransform(s))
             : String.format("%s (%s): ", defaultTransform(s), o);
    };

    BiFunction<String, Object, String> nestedFormat = (s, o) -> {
      return o == null
             ? String.format("  %s: ", defaultTransform(s))
             : String.format("  %s (%s): ", defaultTransform(s), o);
    };

    BiFunction<String, Object, String> dateFormat = (s, o) -> {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

      return o == null
             ? String.format("%s (yyyy-mm-dd): ", defaultTransform(s))
             : String.format("%s (%s): ", defaultTransform(s), df.format(o));
    };

    BiFunction<String, Object, String> booleanFormat = (s, o) -> {
      String o2 = (Boolean) o ? "y" : "n";

      return o == null
             ? String.format("%s (y/n): ", defaultTransform(s))
             : String.format("%s (%s) (y/n): ", defaultTransform(s), o2);
    };

    System.out.println("Name: ");
    nameWizard.setDefaultFormat(nestedFormat);
    nameWizard.setProperties(name);
    person.setName(name);

    System.out.println("Address: ");
    addressWizard.setDefaultFormat(nestedFormat);
    addressWizard.setProperties(address);
    person.setAddress(address);

    personWizard.setDefaultFormat(defaultFormat);
    personWizard.setFormat("birthDate", dateFormat);
    personWizard.setFormat("dateHired", dateFormat);
    personWizard.setFormat("employed", booleanFormat);
    personWizard.setProperties(person);
  }

  private static void setRoleFields(Role role) {    
    RoleInputWizard roleWizard = new RoleInputWizard(reader, handler);
    
    roleWizard.setDefaultFormat((s, o) -> {
      return o == null
             ? String.format("%s: ", defaultTransform(s))
             : String.format("%s (%s): ", defaultTransform(s), o);
    });
    roleWizard.setProperties(role);
  }

  private static String defaultTransform(String str) {
    return StringUtil.capitalize(
      StringUtil.camelCaseToSpaces(str).toLowerCase());
  }

}

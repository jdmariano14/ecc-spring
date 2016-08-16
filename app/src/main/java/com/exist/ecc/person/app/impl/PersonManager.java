package com.exist.ecc.person.app.impl;

import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;

import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.db.Transactions;
import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.AddressWizard;
import com.exist.ecc.person.core.service.input.impl.NameWizard;
import com.exist.ecc.person.core.service.input.impl.PersonWizard;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.PersonFormatter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

public class PersonManager extends AbstractEntityManager {

  private final PersonDao personDao;

  public PersonManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    personDao = new PersonHibernateDao();
  }

  public void create() {
    final Person person = new Person();
    final Name name = new Name();
    final Address address = new Address();
    
    getWriter().write("");
    setPersonFields(person, name, address);

    Transactions.conduct(() -> { 
      personDao.save(person);
    }, personDao);
  }

  public void list() {
    String[] sortFields = {"lastName", "dateHired", "gwa"};
    Consumer<Integer> validation = AppUtil.optionValidation(sortFields.length);

    String sortFieldMessage = 
      new StringBuilder(System.lineSeparator())
          .append("Sort by:")
          .append(System.lineSeparator())
          .append(MenuUtil.getMenu(sortFields, AppUtil::defaultTransform))
          .append(System.lineSeparator())
          .toString();

    int sortField = 
      new InputService.Builder<Integer>(getReader(), getHandler())
          .message(sortFieldMessage)
          .conversion(Integer::parseInt)
          .validation(validation)
          .build().getInput();

    String sortOrderMessage = "Ascending/descending order (a/d): ";

    boolean sortOrder = 
      new InputService.Builder<Boolean>(getReader(), getHandler())
          .message(sortOrderMessage)
          .conversion(s -> s.equalsIgnoreCase("d"))
          .build().getInput();

    String listMethod =
      new StringBuilder()
        .append("listBy")
        .append(StringUtil.capitalize(sortFields[sortField - 1]))
        .toString();

    try {
      this.getClass().getDeclaredMethod(listMethod, Boolean.class)
                     .invoke(this, sortOrder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void update() {
    long id = getId("person");

    Transactions.conduct(() -> {
      final Person person = personDao.get(id);
      final Name name = person.getName();
      final Address address = person.getAddress();

      getWriter().write("");
      getWriter().write("Enter '\\null' to clear a field");
      setPersonFields(person, name, address);
      personDao.save(person);
    }, personDao);
  }

  public void delete() {
    long id = getId("person");

    Transactions.conduct(() -> { 
      final Person person = personDao.get(id);
      String entityString;
      OutputFormatter<Person> formatter = new BasicPersonFormatter();

      entityString = formatter.format(person);

      getWriter().write("");
      
      if (getDeleteConfirmation("person", entityString)) {
        personDao.delete(person);
      }
    }, personDao);
  }
  
  private void listByLastName(Boolean desc) {
    listByProperty("name.lastName", desc);
  }

  private void listByDateHired(Boolean desc) {
    listByProperty("dateHired", desc);
  }

  private void listByGwa(Boolean desc) {
    final PersonDao personDao = new PersonHibernateDao();

    getWriter().write("");

    Transactions.conduct(() -> { 
      OutputFormatter<Person> formatter = new PersonFormatter();

      List<Person> results = personDao.getAll();

      Collections.sort(results, (p1, p2) -> {
        return desc ? p2.getGwa().compareTo(p1.getGwa())
                    : p1.getGwa().compareTo(p2.getGwa());
      });

      results.forEach(p -> {
        getWriter().write(formatter.format(p));
      }); 
    }, personDao);
  }

  private void listByProperty(String property, boolean desc) {
    final PersonDao personDao = new PersonHibernateDao();

    getWriter().write("");

    Transactions.conduct(() -> { 
      OutputFormatter<Person> formatter = new PersonFormatter();
      UnaryOperator<Criteria> query = 
        desc ? c -> c.addOrder(Order.desc(property))
             : c -> c.addOrder(Order.asc(property));

      personDao.query(query).forEach(p -> {
        getWriter().write(formatter.format(p));
      }); 
    }, personDao);
  }


  private void setPersonFields(Person person, Name name,
    Address address) 
  {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    PersonWizard personWizard = new PersonWizard(getReader(), getHandler());
    NameWizard nameWizard = new NameWizard(getReader(), getHandler());
    AddressWizard addressWizard = new AddressWizard(getReader(), getHandler());

    Function<String, String> defaultBlankFormat = 
      s -> String.format("%s: ", AppUtil.defaultTransform(s));

    BiFunction<String, Object, String> defaultFilledFormat = 
      (s, o) -> String.format("%s (%s): ", AppUtil.defaultTransform(s), o);

    Function<String, String> nestedBlankFormat = 
      s -> String.format("  %s: ", AppUtil.defaultTransform(s));

    BiFunction<String, Object, String> nestedFilledFormat = 
      (s, o) -> String.format("  %s (%s): ", AppUtil.defaultTransform(s), o);

    Function<String, String> nestedRequiredBlankFormat = 
      s -> String.format("  %s*: ", AppUtil.defaultTransform(s));

    BiFunction<String, Object, String> nestedRequiredFilledFormat = 
      (s, o) -> String.format("  %s (%s)*: ", AppUtil.defaultTransform(s), o);

    Function<String, String> dateBlankFormat =
      s -> String.format("%s (yyyy-mm-dd): ", AppUtil.defaultTransform(s));

    BiFunction<String, Object, String> dateFilledFormat = 
      (s, o) -> String.format("%s (%s): ", 
        AppUtil.defaultTransform(s), df.format(o));

    Function<String, String> boolBlankFormat =
      s -> String.format("%s (y/n): ", AppUtil.defaultTransform(s));
    
    BiFunction<String, Object, String> boolFilledFormat = 
      (s, o) -> String.format("%s (%s) (y/n): ", 
        AppUtil.defaultTransform(s), (Boolean) o ? "y" : "n");

    getWriter().write("Name: ");
    nameWizard.setDefaultFormat(nestedBlankFormat, nestedFilledFormat);
    nameWizard.setFormat(
      "lastName", nestedRequiredBlankFormat, nestedRequiredFilledFormat);
    nameWizard.setFormat(
      "firstName", nestedRequiredBlankFormat, nestedRequiredFilledFormat);
    nameWizard.setProperties(name);
    person.setName(name);

    getWriter().write("Address: ");
    addressWizard.setDefaultFormat(nestedBlankFormat, nestedFilledFormat);
    addressWizard.setProperties(address);
    person.setAddress(address);

    personWizard.setDefaultFormat(defaultBlankFormat, defaultFilledFormat);
    personWizard.setFormat("birthDate", dateBlankFormat, dateFilledFormat);
    personWizard.setFormat("dateHired", dateBlankFormat, dateFilledFormat);
    personWizard.setFormat("employed", boolBlankFormat, boolFilledFormat);
    personWizard.setProperties(person);
  }

}
package com.exist.ecc.person.app.impl;

import java.lang.reflect.Method;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonHqlDao;

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
import com.exist.ecc.person.core.service.input.impl.ReturnNullHandler;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.PersonFormatter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

public class PersonManager extends AbstractEntityManager {

  public static final DateFormat DATE_FORMAT 
    = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

  private final PersonCriteriaDao personDao;

  public PersonManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    personDao = new PersonCriteriaDao();
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

      if (getDeleteConfirmation("person", entityString)) {
        personDao.delete(person);
      }
    }, personDao);
  }
  
  private void listByLastName(Boolean desc) {
    getWriter().write("");

    String min = 
      new InputService.Builder<String>(getReader(), getHandler())
      .message("Min value (e.g. 'abc'): ")
      .build().getInput();

    String max = 
      new InputService.Builder<String>(getReader(), getHandler())
      .message("Max value (e.g. 'xyz'): ")
      .build().getInput();

    String like =
      new InputService.Builder<String>(getReader(), getHandler())
      .message("Like format (e.g. '%ine'): ")
      .build().getInput();

    getWriter().write("");
    getWriter().write("NOTICE: Using Criteria for sorting & filtering.");
    getWriter().write("");

    Transactions.conduct(() -> { 
      OutputFormatter<Person> formatter = new PersonFormatter();
      UnaryOperator<Criteria> query = c -> {
        if (!min.isEmpty()) {
          c.add(Restrictions.ge("name.lastName", min));
        }

        if (!max.isEmpty()) {
          c.add(Restrictions.le("name.lastName", max));
        }

        if (!like.isEmpty()) {
          c.add(Restrictions.like("name.lastName", like));
        }

        c.addOrder(desc ? Order.desc("name.lastName")
                        : Order.asc("name.lastName"));

        return c;
      };

      personDao.query(query).forEach(p -> {
        getWriter().write(formatter.format(p));
      }); 
    }, personDao);
  }

  private void listByDateHired(Boolean desc) {
    final PersonHqlDao personHqlDao = new PersonHqlDao();

    Function<String, Date> conversion = s -> { 
      try {
        return DATE_FORMAT.parse(s);
      } catch(ParseException e) {
        throw new RuntimeException(e);
      }
    };

    Date min = 
      new InputService.Builder<Date>(getReader(), new ReturnNullHandler())
      .message("Min (earliest) value (yyyy-mm-dd): ")
      .conversion(conversion)
      .build().getInput();

    Date max = 
      new InputService.Builder<Date>(getReader(), new ReturnNullHandler())
      .message("Max (latest) value: ")
      .conversion(conversion)
      .build().getInput();

    getWriter().write("");
    getWriter().write("NOTICE: Using HQL for sorting & filtering.");
    getWriter().write("");

    StringBuilder hql = new StringBuilder();

    if (!(min == null && max == null)) {
      hql.append("WHERE date_hired");

      if (min != null) {
        hql.append(" >= '")
           .append(DATE_FORMAT.format(min))
           .append("'");

        if (max != null) {
          hql.append(" AND date_hired ");
        }
      } 

      if (max != null) {
        hql.append(" <= '")
           .append(DATE_FORMAT.format(max))
           .append("'");
      }

      hql.append(" ");
    }

    hql.append("ORDER BY date_hired ");

    if (desc) {
      hql.append("desc");
    } else {
      hql.append("asc");
    }

    Transactions.conduct(() -> { 
      OutputFormatter<Person> formatter = new PersonFormatter();

      List<Person> results = personHqlDao.query(hql.toString());

      results.forEach(p -> {
        getWriter().write(formatter.format(p));
      }); 
    }, personHqlDao);
  }

  private void listByGwa(Boolean desc) {
    getWriter().write("");

    BigDecimal min = 
      new InputService.Builder<BigDecimal>(getReader(), getHandler())
      .message("Min value (e.g. 1.00): ")
      .conversion(s -> new BigDecimal(s))
      .defaultValue(new BigDecimal("1.00"))
      .build().getInput();

    BigDecimal max = 
      new InputService.Builder<BigDecimal>(getReader(), getHandler())
      .message("Max value (e.g. 5.00): ")
      .conversion(s -> new BigDecimal(s))
      .defaultValue(new BigDecimal("5.00"))
      .build().getInput();

    getWriter().write("");
    getWriter().write("NOTICE: Using Java streams for sorting & filtering.");
    getWriter().write("");

    Transactions.conduct(() -> { 
      OutputFormatter<Person> formatter = new PersonFormatter();

      List<Person> results = personDao.getAll();

      Predicate<Person> filter = 
        p -> p.getGwa().compareTo(min) >= 0 && p.getGwa().compareTo(max) <= 0;

      Comparator<Person> sort = 
        (p1, p2) -> p1.getGwa().compareTo(p2.getGwa());

      results.stream()
      .filter(filter)
      .sorted(desc ? sort.reversed() : sort)
      .forEach(p -> {
        getWriter().write(formatter.format(p));
      }); 
    }, personDao);
  }


  private void setPersonFields(Person person, Name name,
    Address address) 
  {
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
        AppUtil.defaultTransform(s), DATE_FORMAT.format(o));

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
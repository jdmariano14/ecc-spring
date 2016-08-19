package com.exist.ecc.person.app.impl;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Email;
import com.exist.ecc.person.core.model.Landline;
import com.exist.ecc.person.core.model.Mobile;
import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.api.InputExceptionHandler;
import com.exist.ecc.person.core.service.input.api.InputReader;
import com.exist.ecc.person.core.service.input.impl.ContactWizard;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.api.OutputWriter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.ComposedContactFormatter;
import com.exist.ecc.person.core.service.output.impl.ContactFormatter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

public class ContactManager extends AbstractEntityManager {

  public final String[] CONTACT_TYPES = { "Landline", "Mobile", "Email" };

  private final ContactCriteriaDao contactDao;
  private final PersonCriteriaDao personDao;

  public ContactManager(InputReader reader, OutputWriter writer,
    InputExceptionHandler handler)
  {
    super(reader, writer, handler);
    contactDao = new ContactCriteriaDao();
    personDao = new PersonCriteriaDao();
  }

  public void create() {
    Session session = Sessions.getSession();

    try {
      final Contact contact;

      long personId = getId("person");
      
      String contactTypeMessage = 
        new StringBuilder(System.lineSeparator())
            .append("Contact type:")
            .append(System.lineSeparator())
            .append(MenuUtil.getMenu(CONTACT_TYPES, StringUtil::capitalize))
            .append(System.lineSeparator())
            .toString();

      int contactType = 
        new InputService.Builder<Integer>(getReader(), getHandler())
            .message(contactTypeMessage)
            .conversion(Integer::parseInt)
            .validation(AppUtil.optionValidation(CONTACT_TYPES.length))
            .build().getInput();

      switch (CONTACT_TYPES[contactType - 1]) {
        case "Landline":
          contact = new Landline();
          break;
        case "Email":
          contact = new Email();
          break;
        default:
          contact = new Mobile();
      }

      final Person person = Transactions.get(session, personDao, () -> 
        personDao.get(personId));

      System.out.println();
      setContactFields(contact);

      contact.setPerson(person);
      person.getContacts().add(contact);

      Transactions.conduct(session, contactDao, () -> 
        contactDao.save(contact));

      Transactions.conduct(session, personDao, () -> 
        personDao.save(person));

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }

  }

  public void list() {
    throw new UnsupportedOperationException();
  }

  public void update() {
    Session session = Sessions.getSession();

    try {
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Contact> contactFormatter = new ContactFormatter();
      long personId = getId("person");

      final Person person = Transactions.get(session, personDao, () -> 
        personDao.get(personId));

      final Collection<Contact> contacts = person.getContacts();
      
      getWriter().write("");

      if (contacts.isEmpty()) {
        getWriter().write(
          personFormatter.format(person) + " has no contacts");
      } else {
        getWriter().write(personFormatter.format(person) + "'s contacts:");
        contacts.forEach(c -> getWriter().write(contactFormatter.format(c)));

        getWriter().write("");

        long contactId = getId("contact");

        final Contact contact = Transactions.get(session, contactDao, () ->
          contactDao.get(contactId));

        getWriter().write("");

        setContactFields(contact);
        
        Transactions.conduct(session, contactDao, () ->
          contactDao.save(contact));

        Transactions.conduct(session, personDao, () ->
          personDao.save(person));
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }

  public void delete() {
    Session session = Sessions.getSession();

    try {
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Contact> contactFormatter = new ContactFormatter();
      long personId = getId("person");
      long contactId;
      
      final Person person = Transactions.get(session, personDao, () ->
        personDao.get(personId));

      final Collection<Contact> contacts = person.getContacts();

      getWriter().write("");

      if (contacts.isEmpty()) {
        getWriter().write(personFormatter.format(person) + " has no contacts");
      } else {
        OutputFormatter<Contact> formatter = new ComposedContactFormatter(-2);
        String entityString;

        getWriter().write(personFormatter.format(person) + "'s contacts:");
        contacts.forEach(c -> getWriter().write(contactFormatter.format(c)));

        getWriter().write("");

        contactId = getId("contact");

        final Contact contact = Transactions.get(session, contactDao, () ->
          contactDao.get(contactId));

        entityString =
          new StringBuilder()
          .append(personFormatter.format(person))
          .append(": ")
          .append(formatter.format(contact))
          .toString();

        if (getDeleteConfirmation("contact", entityString)) {
          person.getContacts().remove(contact);

          Transactions.conduct(session, personDao, () -> 
            personDao.save(person));

          Transactions.conduct(session, contactDao, () -> 
            contactDao.delete(contact));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      session.close();
    }
  }
  
  private void setContactFields(Contact contact) {
    final String contactType = contact.getClass().getSimpleName();

    ContactWizard contactWizard =
      new ContactWizard(contact.getClass(), getReader(), getHandler());

    Function<String, String> infoBlankFormat = 
      s -> String.format("%s: ", contactType);

    BiFunction<String, Object, String> infoFilledFormat = 
      (s, o) -> String.format("%s (%s): ", contactType, o);

    contactWizard.setFormat("info", infoBlankFormat, infoFilledFormat);
    contactWizard.setProperties(contact);
  }

}
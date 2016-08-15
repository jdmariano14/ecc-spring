package com.exist.ecc.person.app.impl;

import java.util.Collection;
import java.util.function.BiFunction;

import org.hibernate.criterion.Order;

import com.exist.ecc.person.app.AppUtil;

import com.exist.ecc.person.core.dao.api.ContactDao;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.ContactHibernateDao;
import com.exist.ecc.person.core.dao.impl.PersonHibernateDao;

import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Email;
import com.exist.ecc.person.core.model.Landline;
import com.exist.ecc.person.core.model.Mobile;
import com.exist.ecc.person.core.model.Person;

import com.exist.ecc.person.core.service.db.Transactions;
import com.exist.ecc.person.core.service.input.InputService;
import com.exist.ecc.person.core.service.input.impl.ContactWizard;
import com.exist.ecc.person.core.service.output.api.OutputFormatter;
import com.exist.ecc.person.core.service.output.impl.BasicPersonFormatter;
import com.exist.ecc.person.core.service.output.impl.ContactFormatter;

import com.exist.ecc.person.util.MenuUtil;
import com.exist.ecc.person.util.StringUtil;

public class ContactManager extends AbstractEntityManager {

  public final String[] CONTACT_TYPES = { "Landline", "Mobile", "Email" };

  private final ContactDao contactDao;
  private final PersonDao personDao;

  public ContactManager() {
    super();
    contactDao = new ContactHibernateDao();
    personDao = new PersonHibernateDao();
  }

  public void create() {
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

    Transactions.conduct(() -> { 
      final Person person = personDao.get(personId);

      System.out.println();
      setContactFields(contact);

      contact.setPerson(person);
      person.getContacts().add(contact);

      contactDao.save(contact);
      personDao.save(person);
    }, personDao, contactDao);
  }

  public void list() {
    throw new UnsupportedOperationException();
  }

  public void update() {
    final PersonDao personDao = new PersonHibernateDao();
    final ContactDao contactDao = new ContactHibernateDao();

    long personId = getId("person");

    Transactions.conduct(() -> { 
      final Person person = personDao.get(personId);
      final Collection<Contact> contacts = person.getContacts();
      final Contact contact;
      long contactId;
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Contact> contactFormatter = new ContactFormatter();

      if (contacts.isEmpty()) {
        getWriter().write(personFormatter.format(person) + " has no contacts");
      } else {
        getWriter().write(personFormatter.format(person) + "'s contacts:");
        contacts.forEach(c -> getWriter().write(contactFormatter.format(c)));

        contactId = getId("contact");
        contact = contactDao.get(contactId);

        System.out.println();
        setContactFields(contact);

        contactDao.save(contact);
        personDao.save(person);
      }
    }, personDao, contactDao);
  }

  public void delete() {
    final PersonDao personDao = new PersonHibernateDao();
    final ContactDao contactDao = new ContactHibernateDao();

    long personId = getId("person");

    Transactions.conduct(() -> { 
      final Person person = personDao.get(personId);
      final Collection<Contact> contacts = person.getContacts();
      final Contact contact;
      long contactId;
      OutputFormatter<Person> personFormatter = new BasicPersonFormatter();
      OutputFormatter<Contact> contactFormatter = new ContactFormatter();

      if (contacts.isEmpty()) {
        getWriter().write(personFormatter.format(person) + " has no contacts");
      } else {
        getWriter().write(personFormatter.format(person) + "'s contacts:");
        contacts.forEach(c -> getWriter().write(contactFormatter.format(c)));

        contactId = getId("contact");
        contact = contactDao.get(contactId);

        if (getDeleteConfirmation("contact", contact.getInfo())) {
          person.getContacts().remove(contact);
          personDao.save(person);
          contactDao.delete(contact);
        }
      }
    }, personDao, contactDao);
  }

  private void setContactFields(Contact contact) {    
    ContactWizard contactWizard =
      new ContactWizard(contact.getClass(), getReader(), getHandler());
    
    BiFunction<String, Object, String> infoFormat = (s, o) -> {
      String contactType = contact.getClass().getSimpleName();

      return o == null
             ? String.format("%s: ", contactType)
             : String.format("%s (%s): ", contactType, o);
    };

    contactWizard.setFormat("info", infoFormat);
    contactWizard.setProperties(contact);
  }

}
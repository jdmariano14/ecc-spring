package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.exist.ecc.person.app.util.FlashUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.ContactDao;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.Email;
import com.exist.ecc.person.core.model.Landline;
import com.exist.ecc.person.core.model.Mobile;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.ContactWrapper;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

public class ContactController extends AppController {
  
  private final Map<String, Contact> contactTypes = getContactTypes();
  private final PersonDao personDao = new PersonCriteriaDao();
  private final ContactDao contactDao = new ContactCriteriaDao();

  public void _new(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());
    
    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      PersonWrapper personWrapper = new PersonWrapper(person);

      req.setAttribute("person", personWrapper);
      req.setAttribute("types", contactTypes.keySet());
      req.getRequestDispatcher("/WEB-INF/views/contacts/form.jsp")
         .forward(req, res);
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect(String.format("/persons/%d", personId));
    } finally {
      dbSession.close();
    }
  }

  public void create(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      Contact contact = getNewContact(req);
      setContactFields(req, contact);

      person.getContacts().add(contact);
      contact.setPerson(person);

      Transactions.conduct(dbSession, contactDao, () ->
        contactDao.save(contact));

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));

      FlashUtil.setNotice(req, "Contact created");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }

  public void edit(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long contactId = getContactId(req.getRequestURI());
    long personId = -1;
    
    try {
      Contact contact = Transactions.get(dbSession, contactDao, () ->
        contactDao.get(contactId));

      ContactWrapper contactWrapper = new ContactWrapper(contact);

      personId = contact.getPerson().getPersonId();

      req.setAttribute("contact", contactWrapper);
      req.getRequestDispatcher("/WEB-INF/views/contacts/form.jsp")
         .forward(req, res);
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect(String.format("/persons/%d", personId));
    } finally {
      dbSession.close();
    }
  }

  public void update(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long contactId = getContactId(req.getRequestURI());
    long personId = -1;

    try {
      Contact contact = Transactions.get(dbSession, contactDao, () ->
        contactDao.get(contactId));

      personId = contact.getPerson().getPersonId();

      setContactFields(req, contact);

      Transactions.conduct(dbSession, contactDao, () ->
        contactDao.save(contact));

      FlashUtil.setNotice(req, "Contact updated");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }
 
  public void delete(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = -1;
    long contactId = getContactId(req.getRequestURI());

    try {
      Contact contact = Transactions.get(dbSession, contactDao, () ->
        contactDao.get(contactId));

      Person person = contact.getPerson();
      
      personId = person.getPersonId();
      person.getContacts().remove(contact);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));

      Transactions.conduct(dbSession, contactDao, () ->
        contactDao.delete(contact));

      FlashUtil.setNotice(req, "Contact deleted");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }

  private long getPersonId(String uri) {
    long id = -1;
    
    try {
      String idString = uri.replaceAll("/contacts.*", "")  
                           .replaceAll("[^0-9]", "");

      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {

    }

    return id;
  }

  private void setContactFields(HttpServletRequest req, Contact contact) {
    contact.setInfo(req.getParameter("contact[info]"));
  }

  private Contact getNewContact(HttpServletRequest req) {
    Contact contact = null;

    try {
      contact = contactTypes.get(req.getParameter("contact[type]"))
                            .getClass()
                            .newInstance();
    } catch (IllegalAccessException | InstantiationException e) {

    }

    return contact;
  }

  private Map<String, Contact> getContactTypes() {
    Map<String, Contact> contactTypes = new LinkedHashMap();

    List<Contact> contacts = new ArrayList(3);
    contacts.add(new Email());
    contacts.add(new Landline());
    contacts.add(new Mobile());

    for (Contact contact : contacts) {
      contactTypes.put(contact.getClass().getSimpleName(), contact);
    }

    return contactTypes;
  }

  private long getContactId(String uri) {
    long id = -1;
    
    try {
      String idString = uri.replaceAll("persons/[0-9]+/", "")  
                           .replaceAll("[^0-9]", "");

      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {

    }

    return id;
  }
}
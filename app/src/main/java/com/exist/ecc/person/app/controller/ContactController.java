package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.util.List;

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
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.ContactWrapper;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

public class ContactController extends AppController {
  
  private final PersonDao personDao = new PersonCriteriaDao();
  private final ContactDao contactDao = new ContactCriteriaDao();
 
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

  protected long getPersonId(String uri) {
    long id = -1;
    
    try {
      String idString = uri.replaceAll("/contacts.*", "")  
                           .replaceAll("[^0-9]", "");

      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {

    }

    return id;
  }

  protected long getContactId(String uri) {
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
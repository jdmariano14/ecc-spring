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
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;  

public class PersonController extends AppController {

  private final PersonCriteriaDao personDao = new PersonCriteriaDao();

  public void index(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();

    try {
      List<Person> persons = Transactions.get(dbSession, personDao, () ->
        personDao.getAll());

      List<PersonWrapper> personWrappers = 
        PersonWrapper.wrapCollection(persons);

      req.setAttribute("persons", personWrappers);
      req.getRequestDispatcher("/WEB-INF/views/persons/index.jsp")
         .forward(req, res);
    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/");
    } finally {
      dbSession.close();
    }
  }

  public void show(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    long personId = getPersonId(req.getRequestURI());

    if (personId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        final Person person = Transactions.get(dbSession, personDao, () ->
          personDao.get(personId));

        PersonWrapper personWrapper = new PersonWrapper(person);

        req.setAttribute("person", personWrapper);
        req.getRequestDispatcher("/WEB-INF/views/persons/show.jsp")
           .forward(req, res);     
      } catch (Exception e) {
        e.printStackTrace();
        FlashUtil.setError(req, e.getMessage());
        res.sendRedirect("/persons");
      } finally {
        dbSession.close();
      }
    }
  }

  protected long getPersonId(String uri) {
    long id = -1;
    
    try {
      id = Long.parseLong(uri.replaceAll("[^0-9]", ""));
    } catch (NumberFormatException e) {

    }

    return id;
  }
}
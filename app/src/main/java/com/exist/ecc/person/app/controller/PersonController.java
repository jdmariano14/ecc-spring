package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        FlashUtil.clear(req);
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

  public void edit(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    long personId = getPersonId(req.getRequestURI());

    if (personId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        final Person person = Transactions.get(dbSession, personDao, () ->
          personDao.get(personId));

        req.setAttribute("person", person);
        req.getRequestDispatcher("/WEB-INF/views/persons/form.jsp")
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

  public void update(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    long personId = getPersonId(req.getRequestURI());

    if (personId > 0) {
      Session dbSession = Sessions.getSession();
    
      try {
        final Person person = Transactions.get(dbSession, personDao, () ->
          personDao.get(personId));

        setPersonFields(req, person);

        Transactions.conduct(dbSession, personDao, () ->
          personDao.save(person));
        FlashUtil.setNotice(req, "Person updated");
      } catch (Exception e) {
        FlashUtil.setError(req, e.getMessage());
      } finally {
        dbSession.close();
        res.sendRedirect("/persons");
      }
    }

  }

  private void setPersonFields(HttpServletRequest req, Person person) {
    Date birthDate = null;
    Date dateHired = null;
    BigDecimal gwa = null;
    boolean employed = false;

    DateFormat dateFormat =
      new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    try {
      birthDate = dateFormat.parse(
        req.getParameter("person[birth_date]"));
      dateHired = dateFormat.parse(
        req.getParameter("person[date_hired]"));
      gwa = new BigDecimal(req.getParameter("person[gwa]"));
      employed = req.getParameter("person[employed]").equals("true");
    } catch (ParseException
             | NumberFormatException
             | NullPointerException e) 
    {

    }

    person.getName().setFirstName(
      req.getParameter("person[name[first_name]]"));
    person.getName().setMiddleName(
      req.getParameter("person[name[middle_name]]"));
    person.getName().setLastName(
      req.getParameter("person[name[last_name]]"));
    person.getName().setTitle(
      req.getParameter("person[name[title]]"));
    person.getName().setSuffix(
      req.getParameter("person[name[suffix]]"));

    person.getAddress().setStreetAddress(
      req.getParameter("person[address[street_address]]"));
    person.getAddress().setBarangay(
      req.getParameter("person[address[barangay]]"));
    person.getAddress().setMunicipality(
      req.getParameter("person[address[municipality]]"));
    person.getAddress().setZipCode(
      req.getParameter("person[address[zip_code]]"));

    person.setBirthDate(birthDate);
    person.setDateHired(dateHired);
    person.setGwa(gwa);
    person.setEmployed(employed);
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
package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Date;
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

import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

import com.exist.ecc.person.util.DateUtil;

public class PersonController extends AppController {

  private final PersonCriteriaDao personDao = new PersonCriteriaDao();

  private final List<String> queryProperties = getQueryProperties();

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
      req.setAttribute("properties", queryProperties);
      req.getRequestDispatcher("/WEB-INF/views/persons/index.jsp")
         .forward(req, res);
    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/");
    } finally {
      dbSession.close();
    }
  }


  public void _new(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    try {
      Person person = getNewPerson();

      req.setAttribute("person", person);
      req.getRequestDispatcher("/WEB-INF/views/persons/form.jsp")
         .forward(req, res);
    } catch(Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/persons");
    }
  }

  public void create(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = -1;
  
    try {
      Person person = getNewPerson();

      setPersonFields(req, person);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));

      dbSession.refresh(person);
      personId = person.getPersonId();

      FlashUtil.setNotice(req, "Person created");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }

  public void show(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
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

  public void edit(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());

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

  public void update(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      setPersonFields(req, person);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));
      FlashUtil.setNotice(req, "Person updated");
    } catch (Exception e) {
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
    long personId = getPersonId(req.getRequestURI());

    try {
      Transactions.conduct(dbSession, personDao, () -> {
        personDao.delete(personDao.get(personId));
      });

      FlashUtil.setNotice(req, "Person deleted");
    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect("/persons");
    }    
  }

  public void query(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException
  {
    try {
      String property = req.getParameter("person_query[property]");

      switch (property) {
        case "Last name":
          req.setAttribute("minString", "abc");
          req.setAttribute("maxString", "xyz");
          req.setAttribute("likeString", "%ine");
          break;
        case "Date hired":
          req.setAttribute("minDate", "2000-01-15");
          req.setAttribute("maxDate", "2015-12-31");
          break;
        case "GWA":
          req.setAttribute("minBigDecimal", "1.0");
          req.setAttribute("maxBigDecimal", "5.0");
          break;
        default:
          throw new RuntimeException("Invalid query property");
      }

      req.setAttribute("property", property);
      req.getRequestDispatcher("/WEB-INF/views/persons/query.jsp")
         .forward(req, res);
    } catch(Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/persons");
    }
  }

  private Person getNewPerson() {
    Person person = new Person();
    Name name = new Name();
    Address address = new Address();

    person.setName(name);
    person.setAddress(address);

    return person;
  }

  private void setPersonFields(HttpServletRequest req, Person person) {
    Date birthDate = null;
    Date dateHired = null;
    BigDecimal gwa = null;
    boolean employed = false;

    DateFormat dateFormat = DateUtil.getDateFormat();

    try {
      birthDate = 
        dateFormat.parse(req.getParameter("person[birth_date]"));
    } catch (ParseException e) {
      
    }

    try {
      dateHired = 
        dateFormat.parse(req.getParameter("person[date_hired]"));
    } catch (ParseException e) {
      
    }

    try {
      gwa = new BigDecimal(req.getParameter("person[gwa]"));
    } catch (NumberFormatException e) {

    }

    try {
      employed = req.getParameter("person[employed]").equals("true");
    } catch (NullPointerException e) {

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

  private List<String> getQueryProperties() {
    List<String> queryProperties = new ArrayList(3);
    queryProperties.add("Last name");
    queryProperties.add("Date hired");
    queryProperties.add("GWA");

    return queryProperties;
  }

  private long getPersonId(String uri) {
    long id = -1;
    
    try {
      id = Long.parseLong(uri.replaceAll("[^0-9]", ""));
    } catch (NumberFormatException e) {

    }

    return id;
  }
}
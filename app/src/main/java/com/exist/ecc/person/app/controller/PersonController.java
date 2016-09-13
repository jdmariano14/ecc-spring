package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.math.BigDecimal;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.exist.ecc.person.app.flash.FlashUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Name;
import com.exist.ecc.person.core.model.Address;
import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;
  
public class PersonController extends AppController {

  private final PersonDao personDao = new PersonCriteriaDao();

  private final List<String> queryProperties = getQueryProperties();
  private final DateFormat dateFormat = DateUtil.getDateFormat();

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
      req.getRequestDispatcher("/WEB-INF/views/persons/new.jsp")
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
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      PersonWrapper personWrapper = new PersonWrapper(person);
      
      req.setAttribute("person", person);
      req.setAttribute("personWrapper", personWrapper);
      req.getRequestDispatcher("/WEB-INF/views/persons/edit.jsp")
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
      Person person =  Transactions.get(dbSession, personDao, () -> 
        personDao.get(personId));

      person.getRoles().forEach(r -> r.getPersons().remove(person));
      person.getRoles().clear();

      Transactions.conduct(dbSession, personDao, () -> 
        personDao.delete(person));

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
    String property = req.getParameter("person_query[property]");

    try {
      switch (property) {
        case "Last name":
          req.setAttribute("minString", "abc");
          req.setAttribute("maxString", "xyz");
          req.setAttribute("likeString", "%man");
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

  public void result(HttpServletRequest req, HttpServletResponse res)
    throws IOException, ServletException
  {
    Session dbSession = Sessions.getSession();
    String property = req.getParameter("person_result[property]");
    Boolean desc = req.getParameter("person_result[order]").equals("desc");

    try {
      List<Person> persons = null;

      switch (property) {
        case "Last name":
          String minString = req.getParameter("person_result[min_string]");
          String maxString = req.getParameter("person_result[max_string]");
          String likeString = req.getParameter("person_result[like_string]");

          persons = Transactions.get(dbSession, personDao, () ->
            personDao.queryLastName(minString, maxString, likeString, desc));
          break;
        case "Date hired":
          Date minDate = DateUtil.parse(dateFormat,
            req.getParameter("person_result[min_date]"));
          Date maxDate = DateUtil.parse(dateFormat,
            req.getParameter("person_result[max_date]"));

          persons = Transactions.get(dbSession, personDao, () ->
            personDao.queryDateHired(minDate, maxDate, desc));
          break;
        case "GWA":
          BigDecimal minBigDecimal = BigDecimalUtil.parse(
            req.getParameter("person_result[min_big_decimal]"));
          BigDecimal maxBigDecimal = BigDecimalUtil.parse(
            req.getParameter("person_result[max_big_decimal]"));

          persons = Transactions.get(dbSession, personDao, () ->
            personDao.queryGwa(minBigDecimal, maxBigDecimal, desc));
          break;
        default:
          throw new RuntimeException("Invalid query property");
      }

      List<PersonWrapper> personWrappers = 
        PersonWrapper.wrapCollection(persons);

      req.setAttribute("persons", personWrappers);
      req.setAttribute("queryProperty", property);
      req.setAttribute("properties", queryProperties);
      req.getRequestDispatcher("/WEB-INF/views/persons/result.jsp")
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
    Date birthDate = DateUtil.parse(dateFormat,
      req.getParameter("person[birth_date]"));
    Date dateHired = DateUtil.parse(dateFormat, 
      req.getParameter("person[date_hired]"));
    BigDecimal gwa = BigDecimalUtil.parse(req.getParameter("person[gwa]"));
    boolean employed = Boolean.valueOf(req.getParameter("person[employed]")); 

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
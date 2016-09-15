package com.exist.ecc.person.app.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

@Controller
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonCriteriaDao personDao;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      List<Person> persons = Transactions.get(dbSession, personDao, () ->
        personDao.getAllById());

      List<PersonWrapper> personWrappers =
        PersonWrapper.wrapCollection(persons);

      model.addAttribute("persons", personWrappers);
      path = "persons/index";
    } catch (Exception e) {
      path = "redirect:/";
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/{personId}", method = RequestMethod.GET)
  public String show(Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      PersonWrapper personWrapper = new PersonWrapper(person);

      model.addAttribute("person", personWrapper);
      path = "persons/show";
    } catch (Exception e) {
      path = "redirect:/persons";
    } finally {
      dbSession.close();
    }

    return path;
  }  

  /*
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String _new() {
    return "persons/new";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(@ModelAttribute Person person) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Transactions.conduct(dbSession, personDao, () -> personDao.save(person));
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/edit", method = RequestMethod.GET)
  public String edit(Locale locale, Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      
      model.addAttribute("person", person);
      path = "persons/edit";
    } catch (Exception e) {
      path = "redirect:/persons";
    } finally {
      dbSession.close();
    }
    
    return path;
  }

  @RequestMapping(value = "/{personId}", method = RequestMethod.POST)
  public String update(@ModelAttribute Person person, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      if (person.getPersonId() == personId) {
        Transactions.conduct(dbSession, personDao, () -> personDao.save(person)); 
      }
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }
  */

  @RequestMapping(value = "/{personId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      person.getRoles().forEach(r -> r.getPersons().remove(person));
      person.getRoles().clear();

      Transactions.conduct(dbSession, personDao, () ->
        personDao.delete(person));
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
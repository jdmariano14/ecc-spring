package com.exist.ecc.person.app.controller;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import  org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.*;
import com.exist.ecc.person.core.model.wrapper.RoleWrapper;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;

@Controller
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonCriteriaDao personDao;

  @Autowired
  private RoleCriteriaDao roleDao;

  @Autowired
  private ContactCriteriaDao contactDao;

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
      e.printStackTrace();
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
      e.printStackTrace();
      path = "redirect:/persons";
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String _new(Model model) {
    Person person = new Person();
    Name name = new Name();
    Address address = new Address();

    person.setName(name);
    person.setAddress(address);

    model.addAttribute("person", person);

    return "persons/new";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(@ModelAttribute Person person, BindingResult result) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    for (ObjectError error : result.getAllErrors()) {
      System.out.println(error);
    }

    try {
      Transactions.conduct(dbSession, personDao, () -> personDao.save(person));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/edit", method = RequestMethod.GET)
  public String edit(Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      PersonWrapper personWrapper = new PersonWrapper(person); 
      
      model.addAttribute("person", person);
      model.addAttribute("personWrapper", personWrapper);
      path = "persons/edit";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons";
    } finally {
      dbSession.close();
    }
    
    return path;
  }

  @RequestMapping(value = "/{personId}", method = RequestMethod.POST)
  public String update(@ModelAttribute Person person, BindingResult result,
    @PathVariable Long personId)
  {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    for (ObjectError error : result.getAllErrors()) {
      System.out.println(error);
    }

    try {
      if (person.getPersonId() == personId) {
        Transactions.conduct(dbSession, personDao, () -> personDao.save(person)); 
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

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
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/contacts/new", method = RequestMethod.GET)
  public String newContact(Model model, @PathVariable Long personId) {
    String path = null;

    Set<String> contactTypes = 
      Stream.of("Email", "Landline", "Mobile")
            .collect(Collectors.toSet());
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Contact contact = new Contact();

      PersonWrapper personWrapper = new PersonWrapper(person);

      model.addAttribute("person", personWrapper);
      model.addAttribute("contact", contact);
      model.addAttribute("contactTypes", contactTypes);
      path = "contacts/new";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons/" + personId;
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/contacts", 
                  method = RequestMethod.POST,
                  params = {"contactType=Email"})
  public String createEmail(@ModelAttribute Contact contact,
    @PathVariable Long personId)
  {
    String path = null;
    Email email = new Email();

    createContact(email, contact, personId);

    return "redirect:/persons/" + personId;
  }

  @RequestMapping(value = "/{personId}/contacts", 
                  method = RequestMethod.POST,
                  params = {"contactType=Landline"})
  public String createLandline(@ModelAttribute Contact contact,
    @PathVariable Long personId)
  {
    String path = null;
    Landline landline = new Landline();

    createContact(landline, contact, personId);

    return "redirect:/persons/" + personId;
  }

  @RequestMapping(value = "/{personId}/contacts", 
                  method = RequestMethod.POST,
                  params = {"contactType=Mobile"})
  public String createMobile(@ModelAttribute Contact contact,
    @PathVariable Long personId)
  {
    String path = null;
    Mobile mobile = new Mobile();

    createContact(mobile, contact, personId);

    return "redirect:/persons/" + personId;
  }

  private void createContact(Contact concreteContact, Contact abstractContact, 
    long personId) 
  {
    Session dbSession = Sessions.getSession();

    concreteContact.setInfo(abstractContact.getInfo());

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));

      concreteContact.setPerson(person);
      person.getContacts().add(concreteContact);

      Transactions.conduct(dbSession, contactDao, () ->
        contactDao.save(concreteContact));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
    }
  }


  @RequestMapping(value = "/{personId}/roles/new", method = RequestMethod.GET)
  public String newRole(Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      List<Role> roles = Transactions.get(dbSession, roleDao, () ->
        roleDao.getAllGrantable(person));

      PersonWrapper personWrapper = new PersonWrapper(person);
      List<RoleWrapper> roleWrappers = RoleWrapper.wrapCollection(roles);

      model.addAttribute("person", personWrapper);
      model.addAttribute("roles", roleWrappers);
      path = "person_roles/new";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons/" + personId;
    } finally {
      dbSession.close();
    }

    return path;
  }


  @RequestMapping(value = "/{personId}/roles", method = RequestMethod.POST)
  public String createRole(@PathVariable Long personId, 
    @RequestParam("roleId") Long roleId)
  {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));

      person.getRoles().add(role);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/roles/{roleId}/delete", 
                  method = RequestMethod.GET)
  public String deleteRole(@PathVariable Long personId, 
    @PathVariable Long roleId) 
  {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));

      person.getRoles().remove(role);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
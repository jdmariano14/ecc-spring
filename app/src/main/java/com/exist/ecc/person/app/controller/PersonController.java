package com.exist.ecc.person.app.controller;

import java.math.BigDecimal;

import java.text.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;

@Controller
@RequestMapping("/persons")
public class PersonController {

  @Autowired
  private PersonDataService personDataService;

  @Autowired
  private ContactDataService contactDataService;

  @Autowired
  private RoleDataService roleDataService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
    String path = null;

    Session dbSession = Sessions.getSession();
    personDataService.setSession(dbSession);

    try {
      List<PersonDto> personDtos = personDataService.getAll();

      model.addAttribute("persons", personDtos);
      model.addAttribute("queryProperties", getQueryProperties());
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
    personDataService.setSession(dbSession);
    roleDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      List<RoleDto> roleDtos = 
        roleDataService.getFromPerson(personDto);

      model.addAttribute("person", personDto);
      model.addAttribute("roles", roleDtos);
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
    PersonDto personDto = new PersonDto();
    model.addAttribute("person", personDto);

    return "persons/new";
  }


  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(@ModelAttribute PersonDto personDto, 
    BindingResult result) 
  {
    String path = null;
    
    Session dbSession = Sessions.getSession();
    personDataService.setSession(dbSession);

    for (ObjectError error : result.getAllErrors()) {
      System.out.println(error);
    }

    try {
      personDataService.save(personDto);
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
    personDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      
      model.addAttribute("person", personDto);
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
  public String update(@ModelAttribute PersonDto personDto, 
    BindingResult result, @PathVariable Long personId)
  {
    String path = null;
    
    Session dbSession = Sessions.getSession();    
    personDataService.setSession(dbSession);

    for (ObjectError error : result.getAllErrors()) {
      System.out.println(error);
    }

    try {
      personDataService.save(personDto);
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
    personDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      personDataService.delete(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons";
    }

    return path;
  }

/*
  @RequestMapping(value = "/query", method = RequestMethod.POST)
  public String query(Model model, @RequestParam String queryProperty) {
    String path = null;

    try {
      switch (queryProperty) {
        case "Last name":
          model.addAttribute("minString", "abc");
          model.addAttribute("maxString", "xyz");
          model.addAttribute("likeString", "%man");
          break;
        case "Date hired":
          model.addAttribute("minDate", "2000-01-15");
          model.addAttribute("maxDate", "2015-12-31");
          break;
        case "GWA":
          model.addAttribute("minBigDecimal", "1.0");
          model.addAttribute("maxBigDecimal", "5.0");
          break;
        default:
          throw new RuntimeException("Invalid query property");
      }
      
      model.addAttribute("queryProperty", queryProperty);
      path = "persons/query";
    } catch(Exception e) {
      e.printStackTrace();
      path = "redirect:/persons";
    }

    return path;
  }

  @RequestMapping(value = "/result", 
                  method = RequestMethod.POST,
                  params = {"queryProperty=Last name"})
  public String resultLastName(Model model,
                 @RequestParam String minString,
                 @RequestParam String maxString,
                 @RequestParam String likeString,
                 @RequestParam String order)
  {
    boolean desc = order.equals("desc");

    Supplier<List<Person>> query = () ->
      personDao.queryLastName(minString, maxString, likeString, desc);

    return getResultPage(model, query, "Last name");
  }

  @RequestMapping(value = "/result", 
                  method = RequestMethod.POST,
                  params = {"queryProperty=Date hired"})
  public String resultDateHired(Model model,
                 @RequestParam String minDate,
                 @RequestParam String maxDate,
                 @RequestParam String order)
  {
    boolean desc = order.equals("desc");

    DateFormat dateFormat = DateUtil.getDateFormat();

    Date _minDate = DateUtil.parse(dateFormat, minDate);
    Date _maxDate = DateUtil.parse(dateFormat, maxDate);

    Supplier<List<Person>> query = () ->
      personDao.queryDateHired(_minDate, _maxDate, desc);

    return getResultPage(model, query, "Date hired");
  }

  @RequestMapping(value = "/result", 
                  method = RequestMethod.POST,
                  params = {"queryProperty=GWA"})
  public String resultGwa(Model model,
                 @RequestParam String minBigDecimal,
                 @RequestParam String maxBigDecimal,
                 @RequestParam String order)
  {
    boolean desc = order.equals("desc");

    BigDecimal _minBigDecimal = BigDecimalUtil.parse(minBigDecimal);
    BigDecimal _maxBigDecimal = BigDecimalUtil.parse(maxBigDecimal);

    Supplier<List<Person>> query = () ->
      personDao.queryGwa(_minBigDecimal, _maxBigDecimal, desc);

    return getResultPage(model, query, "GWA");
  }

  private String getResultPage(Model model, Supplier<List<Person>> query, 
    String property)
  {
    String path = null;

    Session dbSession = Sessions.getSession();

    try {
      List<Person> persons = Transactions.get(dbSession, personDao, query);

      List<PersonWrapper> personWrappers =
        PersonWrapper.wrapCollection(persons);

      model.addAttribute("persons", personWrappers);
      model.addAttribute("selectedProperty", property);
      model.addAttribute("queryProperties", getQueryProperties());

      path = "persons/result";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons";
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/{personId}/contacts/new", method = RequestMethod.GET)
  public String newContact(Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Contact contact = new Contact();

      PersonWrapper personWrapper = new PersonWrapper(person);

      model.addAttribute("person", personWrapper);
      model.addAttribute("contact", contact);
      model.addAttribute("contactTypes", getContactTypes());
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
    Session dbSession = contactDataService.newSession();

    try {
      PersonDto person = personDataService.get(personId);

      person.getContactIds().add(concreteContact.getContactId());
      contact.setPersonId(person.getPersonId());
      
      contactDataService.save(concreteContact);
      personDataService.save(person);      
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      dbSession.close();
    }
  }
*/

  @RequestMapping(value = "/{personId}/roles/new", method = RequestMethod.GET)
  public String newRole(Model model, @PathVariable Long personId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();
    personDataService.setSession(dbSession);
    roleDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      List<RoleDto> roleDtos = roleDataService.getAll();

      model.addAttribute("person", personDto);
      model.addAttribute("roles", roleDtos);
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
    personDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().add(roleId);
      System.out.println("I got here");
      personDataService.save(personDto);
      System.out.println("I got there");
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
    personDataService.setSession(dbSession);

    try {
      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().remove(roleId);
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  private List<String> getQueryProperties() {
    List<String> queryProperties = 
      Stream.of("Last name", "Date hired", "GWA")
            .collect(Collectors.toList());

    return queryProperties;
  }

  private Set<String> getContactTypes() {
    Set<String> contactTypes = 
      Stream.of("Email", "Landline", "Mobile")
            .collect(Collectors.toSet());

    return contactTypes;
  }
  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
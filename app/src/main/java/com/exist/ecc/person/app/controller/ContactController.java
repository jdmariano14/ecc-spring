  package com.exist.ecc.person.app.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;

@Controller
@RequestMapping("/contacts")
public class ContactController {
/*
  @Autowired
  private PersonCriteriaDao personDao;

  @Autowired
  private ContactCriteriaDao contactDao;

  @RequestMapping(value = "/{contactId}/edit", method = RequestMethod.GET)
  public String edit(Model model, @PathVariable Long contactId) {
    String path = null;
    long personId = -1;
    
    Session dbSession = Sessions.getSession();

    try {
      Contact contact = Transactions.get(dbSession, contactDao, () ->
        contactDao.get(contactId)); 

      PersonWrapper personWrapper = new PersonWrapper(contact.getPerson());

      personId = personWrapper.getPersonId();

      model.addAttribute("contact", contact);
      path = "contacts/edit";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons/" + personId;
    } finally {
      dbSession.close();
    }
    
    return path;
  }

  @RequestMapping(value = "/{contactId}", method = RequestMethod.POST)
  public String update(@ModelAttribute ContactDto contactDto,
                       @PathVariable Long contactId)
  { 
    String path = "redirect:/persons" + contactDto.getPersonId();
    
    Session dbSession = Sessions.getSession();
    contactService.setSession(dbSession);

    try {
      contactService.save(contactDto);
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/{contactId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long contactId) 
  {
    String path = null;
    long personId = -1;

    Session dbSession = Sessions.getSession();

    try {
      Contact contact = Transactions.get(dbSession, contactDao, () ->
        contactDao.get(contactId)); 

      personId = contact.getPerson().getPersonId();

      Transactions.conduct(dbSession, contactDao, () ->
        contactDao.delete(contact));
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  /*
  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Email"})
  public String createEmail(Locale locale, Model model, 
    @PathVariable Long personId) 
  {
    return "";
  }

  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Landline"})
  public String createLandline(Locale locale, Model model, 
    @PathVariable Long personId) 
  {
    return "";
  }

  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Mobile"})
  public String createMobile(Locale locale, Model model, 
    @PathVariable Long personId) 
  {
    return "";
  }*/
  

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
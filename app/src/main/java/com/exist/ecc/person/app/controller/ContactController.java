  package com.exist.ecc.person.app.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.ContactDao;
import com.exist.ecc.person.core.dao.impl.ContactCriteriaDao;

import com.exist.ecc.person.core.model.Contact;
import com.exist.ecc.person.core.model.wrapper.ContactWrapper;

@Controller
@RequestMapping("/persons")
public class ContactController {

  private static final ContactDao contactDao = new ContactCriteriaDao();
  private static final Logger logger = 
    LoggerFactory.getLogger(ContactController.class);
  
  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Email"})
  public String createEmail(Locale locale, Model model, 
    @PathVariable("personId") Long personId) 
  {
    return "";
  }

  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Landline"})
  public String createLandline(Locale locale, Model model, 
    @PathVariable("personId") Long personId) 
  {
    return "";
  }

  @RequestMapping(value = "/{personId}/contact", method = RequestMethod.POST, params={"type=Mobile"})
  public String createMobile(Locale locale, Model model, 
    @PathVariable("personId") Long personId) 
  {
    return "";
  }
  

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
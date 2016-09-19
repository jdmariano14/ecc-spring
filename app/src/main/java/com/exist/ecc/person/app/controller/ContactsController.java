package com.exist.ecc.person.app.controller;

import java.util.Locale;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;

public class ContactsController extends MultiActionController {

  private ContactDataService contactDataService;
  private PersonDataService personDataService;

  public void setContactDataService(ContactDataService contactDataService) {
    this.contactDataService = contactDataService;
  }

  public void setPersonDataService(PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  public ModelAndView add(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    Model model = new ExtendedModelMap();
    String view = null;

    long personId = -1;

    try {
      personId = getPersonId(req);

      PersonDto personDto = personDataService.get(personId);
      ContactDto contactDto = new ContactDto();
      contactDto.setPersonId(personId);

      model.addAttribute("person", personDto);
      model.addAttribute("contact", contactDto);
      model.addAttribute("contactTypes", getContactTypes());
      view = "contacts/add";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/persons/show?id=" + personId;
    } 

    return new ModelAndView(view, model.asMap());
  }

  public String create(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    long personId = -1;
    
    try {
      personId = getPersonId(req);
    } catch (NullPointerException | NumberFormatException e) {

    }

    return save(req, "redirect:/persons/show?id=" + personId);
  }

  public ModelAndView edit(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    Model model = new ExtendedModelMap();
    String view = null;

    long personId = -1;

    try {
      long contactId = getContactId(req);

      ContactDto contactDto = contactDataService.get(contactId);
      personId = contactDto.getPersonId();
      model.addAttribute("contact", contactDto);
      view = "contacts/edit";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/persons/show?id=" + personId;
    }

    return new ModelAndView(view, model.asMap());
  }

  public String update(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    long personId = -1;
    
    try {
      personId = getPersonId(req);
    } catch (NullPointerException | NumberFormatException e) {

    }

    return save(req, "redirect:/persons/show?id=" + personId);
  }

  /*
  @RequestMapping(value = "/{contactId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long contactId) {
    String view = null;
    long personId = -1;

    try {
      ContactDto contactDto = contactDataService.get(contactId);
      personId = contactDto.getPersonId();
      contactDataService.delete(contactDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/persons/" + personId;
    }

    return view;
  }*/

  private long getContactId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("id"));
  }

  private long getPersonId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("personId"));
  }

  private ContactDto getContactDto(HttpServletRequest req) {
    long contactId = -1;
    String contactType = req.getParameter("contactType");
    String value = req.getParameter("value");
    long personId = -1;

    try {
      contactId = getContactId(req);
    } catch (NullPointerException | NumberFormatException e) {

    }

    try {
      personId = getPersonId(req);
    } catch (NullPointerException | NumberFormatException e) {

    }

    return new ContactDto(contactId, contactType, value, personId);
  }

  private List<String> getContactTypes() {
    List<String> contactTypes = 
      Stream.of("Email", "Landline", "Mobile")
            .collect(Collectors.toList());

    return contactTypes;
  }

  private String save(HttpServletRequest req, String redirectUrl) {
    String view = null;

    try {
      ContactDto contactDto = getContactDto(req);

      contactDataService.save(contactDto);
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      view = redirectUrl;
    }

    return view;
  }

}
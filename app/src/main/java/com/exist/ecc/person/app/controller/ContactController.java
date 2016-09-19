package com.exist.ecc.person.app.controller;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.Collectors;

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
import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;

@Controller
@RequestMapping("/contacts")
public class ContactController {

  @Autowired
  private Sessions sessions;

  @Autowired
  private ContactDataService contactDataService;

  @Autowired
  private PersonDataService personDataService;

  public String _new(Model model, Long personId) { 
    String path = null;
    
    Session dbSession = sessions.getSession();
    personDataService.setSession(null);

    try {
      PersonDto personDto = personDataService.get(personId);
      ContactDto contactDto = new ContactDto();
      contactDto.setPersonId(personId);

      model.addAttribute("person", personDto);
      model.addAttribute("contact", contactDto);
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

  public String create(ContactDto contactDto, Long personId) {
    String path = null;

    Session dbSession = sessions.getSession();
    contactDataService.setSession(null);

    try {
      contactDataService.save(contactDto);
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }


  @RequestMapping(value = "/{contactId}/edit", method = RequestMethod.GET)
  public String edit(Model model, @PathVariable Long contactId) {
    String path = null;
    long personId = -1;
    
    Session dbSession = sessions.getSession();
    contactDataService.setSession(null);

    try {
      ContactDto contactDto = contactDataService.get(contactId);
      personId = contactDto.getPersonId();
      model.addAttribute("contact", contactDto);
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
    String path = null;
    long personId = -1;

    Session dbSession = sessions.getSession();
    contactDataService.setSession(null);

    try {
      contactDataService.save(contactDto);
      personId = contactDto.getPersonId();
    } catch (Exception e){
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  @RequestMapping(value = "/{contactId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long contactId) {
    String path = null;
    long personId = -1;

    Session dbSession = sessions.getSession();
    contactDataService.setSession(null);

    try {
      ContactDto contactDto = contactDataService.get(contactId);
      personId = contactDto.getPersonId();
      contactDataService.delete(contactDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  private Set<String> getContactTypes() {
    Set<String> contactTypes = 
      Stream.of("Email", "Landline", "Mobile")
            .collect(Collectors.toSet());

    return contactTypes;
  }
}
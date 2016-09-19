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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.exist.ecc.person.core.dto.ContactDto;
import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.build.PersonBuildService;
import com.exist.ecc.person.core.service.data.impl.ContactDataService;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

import com.exist.ecc.person.util.BigDecimalUtil;
import com.exist.ecc.person.util.DateUtil;

public class PersonsController extends MultiActionController {

  private PersonDataService personDataService;
  private PersonBuildService personBuildService;/*
  private ContactDataService contactDataService;
  private RoleDataService roleDataService;

  private ContactController contactController;
  private PersonRoleController personRoleController;*/

  public void setPersonDataService(PersonDataService personDataService) {
    this.personDataService = personDataService;
  }

  public void setPersonBuildService(PersonBuildService personBuildService) {
    this.personBuildService = personBuildService;
  }
/*
  public void setContactDataService(ContactDataService contactDataService) {
    this.contactDataService = contactDataService;
  }

  public void setRoleDataService(RoleDataService roleDataService) {
    this.roleDataService = roleDataService;
  }

  public void setContactController(ContactController contactController) {
    this.contactController = contactController;
  }

  public void setPersonRoleController(PersonRoleController personRoleController) {
    this.personRoleController = personRoleController;
  }
*/
  public ModelAndView index(HttpServletRequest req, HttpServletResponse res)
    throws Exception 
  {
    Model model = new ExtendedModelMap();
    String view = null;

    try {
      List<PersonDto> personDtos = personDataService.getAll();

      model.addAttribute("persons", personDtos);
      model.addAttribute("queryProperties", getQueryProperties());
      view = "persons/index";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/";
    }

    return new ModelAndView(view, model.asMap());
  }
  
  public ModelAndView add(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    Model model = new ExtendedModelMap();
    String view = "persons/add";

    PersonDto personDto = new PersonDto();
    model.addAttribute("person", personDto);

    return new ModelAndView(view, model.asMap());
  }

  public String create(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    return save(req, "redirect:/persons/index");
  }
  
  public ModelAndView edit(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    Model model = new ExtendedModelMap();
    String view = null;

    try {
      long personId = getPersonId(req);
      PersonDto person = personDataService.get(personId);

      model.addAttribute("person", person);
      view = "persons/edit";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/persons/index";
    } 

    return new ModelAndView(view, model.asMap());
  }

  public String update(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String redirectUrl = null;

    try {
      long personId = getPersonId(req);
      redirectUrl = "redirect:/persons/show?id=" + personId;
    } catch (Exception e) {
      e.printStackTrace();
      redirectUrl = "redirect:/persons/index";
    }

    return save(req, redirectUrl);
  }

  
  public String delete(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String view = null;

    try {
      long personId = getPersonId(req);
      PersonDto personDto = personDataService.get(personId);

      personDataService.delete(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/persons/index";
    }

    return view;
  }

  
  private long getPersonId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("id"));
  }

  private PersonDto getPersonDto(HttpServletRequest req) {
    return personBuildService
           .getDtoBuilder()
           .setPersonId(req.getParameter("id"))
           .setFirstName(req.getParameter("name.firstName"))
           .setMiddleName(req.getParameter("name.middleName"))
           .setLastName(req.getParameter("name.lastName"))
           .setSuffix(req.getParameter("name.suffix"))
           .setTitle(req.getParameter("name.title"))
           .setStreetAddress(req.getParameter("address.streetAddress"))
           .setBarangay(req.getParameter("address.barangay"))
           .setMunicipality(req.getParameter("address.municipality"))
           .setZipCode(req.getParameter("address.zipCode"))
           .setBirthDate(req.getParameter("birthDate"))
           .setDateHired(req.getParameter("dateHired"))
           .setGwa(req.getParameter("gwa"))
           .setEmployed(req.getParameter("employed"))
           .build();
  }

  private String save(HttpServletRequest req, String redirectUrl)
    throws Exception 
  {
    String view = null;

    try {
      PersonDto personDto = getPersonDto(req);
 
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = redirectUrl;
    }

    return view;
  }
  private List<String> getQueryProperties() {
    List<String> queryProperties = 
      Stream.of("Last name", "Date hired", "GWA")
            .collect(Collectors.toList());

    return queryProperties;
  }
}
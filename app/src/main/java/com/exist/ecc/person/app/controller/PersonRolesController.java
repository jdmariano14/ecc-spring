package com.exist.ecc.person.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

public class PersonRolesController extends MultiActionController {

  private PersonDataService personDataService;

  private RoleDataService roleDataService;

  public void setPersonDataService(PersonDataService personDataService) {
    this.personDataService = personDataService;
  }
  
  public void setRoleDataService(RoleDataService roleDataService) {
    this.roleDataService = roleDataService;
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
      List<RoleDto> roleDtos = roleDataService.getAll();

      model.addAttribute("person", personDto);
      model.addAttribute("roles", roleDtos);
      view = "person_roles/add";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/persons/show?id=" + personId;
    }

    return new ModelAndView(view, model.asMap());
  }
  
  public String create(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String view = null;

    long personId = -1;

    try {
      long roleId = getRoleId(req);
      personId = getPersonId(req);

      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().add(roleId);
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/persons/show?id=" + personId;
    }

    return view;
  }

  public String delete(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String view = null;

    long personId = -1;

    try {
      long roleId = getRoleId(req);
      personId = getPersonId(req);

      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().remove(roleId);
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/persons/show?id=" + personId;
    }

    return view;
  }

  private long getPersonId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("personId"));
  }

  private long getRoleId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("roleId"));
  }

}
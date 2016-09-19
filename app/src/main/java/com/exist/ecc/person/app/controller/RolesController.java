package com.exist.ecc.person.app.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

public class RolesController extends MultiActionController {

  private RoleDataService roleDataService;

  public void setRoleDataService(RoleDataService roleDataService) {
    this.roleDataService = roleDataService;
  }

  public ModelAndView index(HttpServletRequest req, HttpServletResponse res)
    throws Exception 
  {
    Model model = new ExtendedModelMap();
    String view = null;

    try {
      List<RoleDto> roleDtos = roleDataService.getAll();
      model.addAttribute("roles", roleDtos);
      view = "roles/index";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/";
    }

    return new ModelAndView(view, model.asMap());
  }

  public String add(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    return "roles/add";
  }

  public String create(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String view = null;

    try {
      RoleDto roleDto = getRoleDto(req);
      
      roleDataService.save(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/roles/index";
    }

    return view;
  }

  private RoleDto getRoleDto(HttpServletRequest req) {
    String name = req.getParameter("name");

    return new RoleDto(-1, name, null);
  }
}
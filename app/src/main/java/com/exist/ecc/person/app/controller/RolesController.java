package com.exist.ecc.person.app.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
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

}
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
      view = "redirect:/index.jsp";
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
    return save(req, "redirect:/roles/index");
  }

  public ModelAndView edit(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    Model model = new ExtendedModelMap();
    String view = null;

    try {
      long roleId = getRoleId(req);
      RoleDto role = roleDataService.get(roleId);

      model.addAttribute("role", role);
      view = "roles/edit";
    } catch (Exception e) {
      e.printStackTrace();
      view = "redirect:/roles/index";
    } 

    return new ModelAndView(view, model.asMap());
  }

  public String update(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    return save(req, "redirect:/roles/index");
  }

  public String delete(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String view = null;

    try {
      long roleId = getRoleId(req);
      RoleDto roleDto = roleDataService.get(roleId);

      roleDataService.delete(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/roles/index";
    }

    return view;
  }

  private long getRoleId(HttpServletRequest req) {
    return Long.parseLong(req.getParameter("id"));
  }

  private RoleDto getRoleDto(HttpServletRequest req) {
    long id = -1;
    String name = req.getParameter("name");

    try {
      id = getRoleId(req);
    } catch (NullPointerException | NumberFormatException e) {

    }

    return new RoleDto(id, name, null);
  }

  private String save(HttpServletRequest req, String redirectUrl)
    throws Exception 
  {
    String view = null;

    try {
      RoleDto roleDto = getRoleDto(req);

      roleDataService.save(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = redirectUrl;
    }

    return view;
  }
}
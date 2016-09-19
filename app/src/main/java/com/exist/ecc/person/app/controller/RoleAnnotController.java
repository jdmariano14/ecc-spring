package com.exist.ecc.person.app.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

@Controller
@RequestMapping("/roles")
public class RoleAnnotController {
  
  @Autowired
  private RoleDataService roleDataService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
    String path = null;

    try {
      List<RoleDto> roleDtos = roleDataService.getAll();
      model.addAttribute("roles", roleDtos);
      path = "roles/index";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/";
    }

    return path;
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String _new() {
    return "roles/new";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(@ModelAttribute RoleDto roleDto) {
    String path = null;

    try {
      roleDataService.save(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      path = "redirect:/roles";
    }

    return path;
  }

  @RequestMapping(value = "/{roleId}/edit", method = RequestMethod.GET)
  public String edit(Locale locale, Model model, @PathVariable Long roleId) {
    String path = null;

    try {
      RoleDto role = roleDataService.get(roleId);
      model.addAttribute("role", role);
      path = "roles/edit";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/roles";
    }
    
    return path;
  }

  @RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
  public String update(@ModelAttribute RoleDto roleDto, @PathVariable Long roleId) {
    String path = null;

    try {
      roleDataService.save(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      path = "redirect:/roles";
    }

    return path;
  }

  @RequestMapping(value = "/{roleId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long roleId) {
    String path = null;

    try {
      RoleDto roleDto = roleDataService.get(roleId);
      roleDataService.delete(roleDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      path = "redirect:/roles";
    }

    return path;
  }
}
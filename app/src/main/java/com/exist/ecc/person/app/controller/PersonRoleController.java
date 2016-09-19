package com.exist.ecc.person.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.exist.ecc.person.core.dto.PersonDto;
import com.exist.ecc.person.core.dto.RoleDto;
import com.exist.ecc.person.core.service.data.impl.PersonDataService;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;

@Controller
public class PersonRoleController {

  @Autowired
  private PersonDataService personDataService;

  @Autowired
  private RoleDataService roleDataService;

  public String _new(Model model, Long personId) {
    String path = null;

    try {
      PersonDto personDto = personDataService.get(personId);
      List<RoleDto> roleDtos = roleDataService.getAll();

      model.addAttribute("person", personDto);
      model.addAttribute("roles", roleDtos);
      path = "person_roles/new";
    } catch (Exception e) {
      e.printStackTrace();
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  public String create(Long personId, Long roleId) {
    String path = null;

    try {
      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().add(roleId);
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      path = "redirect:/persons/" + personId;
    }

    return path;
  }

  public String delete(Long personId, Long roleId) {
    String path = null;

    try {
      PersonDto personDto = personDataService.get(personId);
      personDto.getRoleIds().remove(roleId);
      personDataService.save(personDto);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      path = "redirect:/persons/" + personId;
    }

    return path;
  }
}
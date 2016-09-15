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

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.model.wrapper.RoleWrapper;

@Controller
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private RoleCriteriaDao roleDao;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String index(Locale locale, Model model) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      List<Role> roles = Transactions.get(dbSession, roleDao, () ->
        roleDao.getAllById());

      List<RoleWrapper> roleWrappers =
        RoleWrapper.wrapCollection(roles);

      model.addAttribute("roles", roleWrappers);
      path = "roles/index";
    } catch (Exception e) {
      path = "redirect:/";
    } finally {
      dbSession.close();
    }

    return path;
  }

  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String _new() {
    return "roles/new";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String create(@ModelAttribute Role role) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Transactions.conduct(dbSession, roleDao, () -> roleDao.save(role));
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/roles";
    }

    return path;
  }

  @RequestMapping(value = "/{roleId}/edit", method = RequestMethod.GET)
  public String edit(Locale locale, Model model, @PathVariable Long roleId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));
      
      model.addAttribute("role", role);
      path = "roles/edit";
    } catch (Exception e) {
      path = "redirect:/roles";
    } finally {
      dbSession.close();
    }
    
    return path;
  }

  @RequestMapping(value = "/{roleId}", method = RequestMethod.POST)
  public String update(@ModelAttribute Role role, @PathVariable Long roleId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      if (role.getRoleId() == roleId) {
        Transactions.conduct(dbSession, roleDao, () -> roleDao.save(role)); 
      }
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/roles";
    }

    return path;
  }

  @RequestMapping(value = "/{roleId}/delete", method = RequestMethod.GET)
  public String delete(@PathVariable Long roleId) {
    String path = null;
    
    Session dbSession = Sessions.getSession();

    try {
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));

      role.getPersons().forEach(p -> p.getRoles().remove(role));
      role.getPersons().clear();

      Transactions.conduct(dbSession, roleDao, () -> roleDao.delete(role));
    } catch (Exception e) {

    } finally {
      dbSession.close();
      path = "redirect:/roles";
    }

    return path;
  }

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
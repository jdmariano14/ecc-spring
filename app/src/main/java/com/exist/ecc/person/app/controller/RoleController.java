package com.exist.ecc.person.app.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  private static final RoleDao roleDao = new RoleCriteriaDao();
  private static final Logger logger = 
    LoggerFactory.getLogger(HomeController.class);
  
  @RequestMapping(value = "/{roleId}/edit", method = RequestMethod.GET)
  public String edit(Locale locale, Model model, 
    @PathVariable("roleId") Long roleId) 
  {
    Session dbSession = Sessions.getSession();
    String url = null;

    logger.info("Welcome home! The client locale is {}.", locale);

    try {
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));
      
      model.addAttribute("role", role);
      url = "roles/edit";
    } catch (Exception e) {
      url = "redirect:";
    } finally {
      dbSession.close();
    }
    
    return url;
  }
  

  //http://www.concretepage.com/spring/spring-mvc/spring-mvc-modelattribute-annotation-example
}
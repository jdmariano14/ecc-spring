package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.exist.ecc.person.app.flash.FlashUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.api.PersonDao;
import com.exist.ecc.person.core.dao.api.RoleDao;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.model.wrapper.PersonWrapper;
import com.exist.ecc.person.core.model.wrapper.RoleWrapper;

public class PersonRoleController extends AppController {
  
  private final PersonDao personDao = new PersonCriteriaDao();
  private final RoleDao roleDao = new RoleCriteriaDao();

  public void _new(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());
    
    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      List<Role> roles = Transactions.get(dbSession, roleDao, () ->
        roleDao.getAllById(false));

      PersonWrapper personWrapper = new PersonWrapper(person);
      List<RoleWrapper> roleWrappers = RoleWrapper.wrapCollection(roles);

      req.setAttribute("person", personWrapper);
      req.setAttribute("roles", roleWrappers);
      req.getRequestDispatcher("/WEB-INF/views/person_roles/new.jsp")
         .forward(req, res);
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect(String.format("/persons/%d", personId));
    } finally {
      dbSession.close();
    }
  }

  public void create(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());
    long roleId = Long.parseLong(req.getParameter("person_role[role_id]"));

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));

      person.getRoles().add(role);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));

      FlashUtil.setNotice(req, "Role granted");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }
 
  public void delete(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();
    long personId = getPersonId(req.getRequestURI());
    long roleId = getRoleId(req.getRequestURI());

    try {
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Role role = Transactions.get(dbSession, roleDao, () ->
        roleDao.get(roleId));

      person.getRoles().remove(role);

      Transactions.conduct(dbSession, personDao, () ->
        personDao.save(person));

      FlashUtil.setNotice(req, "Role revoked");
    } catch (Exception e) {
      e.printStackTrace();
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect(String.format("/persons/%d", personId));
    }
  }

  private long getPersonId(String uri) {
    long id = -1;
    
    try {
      String idString = uri.replaceAll("/roles.*", "")  
                           .replaceAll("[^0-9]", "");

      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {

    }

    return id;
  }

  private long getRoleId(String uri) {
    long id = -1;
    
    try {
      String idString = uri.replaceAll("persons/[0-9]+/", "")  
                           .replaceAll("[^0-9]", "");

      id = Long.parseLong(idString);
    } catch (NumberFormatException e) {

    }

    return id;
  }
}
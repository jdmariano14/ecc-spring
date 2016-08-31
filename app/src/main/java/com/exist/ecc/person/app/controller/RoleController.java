package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.exist.ecc.person.app.util.FlashUtil;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Role;

public class RoleController extends AppController {

  private final RoleCriteriaDao roleDao = new RoleCriteriaDao();

  public void index(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();

    try {
      List<Role> roles = Transactions.get(dbSession, roleDao, () ->
        roleDao.getAll());

      req.setAttribute("roles", roles);
      req.getRequestDispatcher("/WEB-INF/views/roles/index.jsp")
         .forward(req, res);
    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/");
    } finally {
      dbSession.close();
    }
  }

  public void _new(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    req.getRequestDispatcher("/WEB-INF/views/roles/new.jsp")
       .forward(req, res);
  }

  public void create(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();

    final Role role = new Role();
    role.setName(req.getParameter("role[name]"));
  
    try {
      Transactions.conduct(dbSession, roleDao, () -> roleDao.save(role));
      FlashUtil.setNotice(req, "Role created");
    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect("/roles");
    }
  }

  public void edit(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    final long roleId = getRoleId(req.getRequestURI());

    if (roleId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        final Role role = Transactions.get(dbSession, roleDao, () ->
          roleDao.get(roleId));

        req.setAttribute("role", role);
        req.getRequestDispatcher("/WEB-INF/views/roles/edit.jsp")
           .forward(req, res);     
      } catch (Exception e) {
        e.printStackTrace();
        FlashUtil.setError(req, e.getMessage());
        res.sendRedirect("/roles");
      } finally {
        dbSession.close();
      }
    }
  }

  public void update(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    final long roleId = getRoleId(req.getRequestURI());

    if (roleId > 0) {
      Session dbSession = Sessions.getSession();
    
      try {
        final Role role = Transactions.get(dbSession, roleDao, () ->
          roleDao.get(roleId));

        role.setName(req.getParameter("role[name]"));
        Transactions.conduct(dbSession, roleDao, () -> roleDao.save(role));
        FlashUtil.setNotice(req, "Role updated");
      } catch (Exception e) {
        FlashUtil.setError(req, e.getMessage());
      } finally {
        dbSession.close();
        res.sendRedirect("/roles");
      }
    }

  }

  public void delete(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    final long roleId = getRoleId(req.getRequestURI());

    if (roleId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        Transactions.conduct(dbSession, roleDao, () -> {
          roleDao.delete(roleDao.get(roleId));
        });

        FlashUtil.setNotice(req, "Role deleted");
      } catch (Exception e) {
        FlashUtil.setError(req, e.getMessage());
      } finally {
        dbSession.close();
        res.sendRedirect("/roles");
      }
    }
  }

  protected long getRoleId(String uri) {
    long id = -1;
    
    try {
      id = Long.parseLong(uri.replaceAll("[^0-9]", ""));
    } catch (NumberFormatException e) {

    }

    return id;
  }
}
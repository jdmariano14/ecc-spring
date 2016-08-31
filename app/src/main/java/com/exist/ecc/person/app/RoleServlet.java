package com.exist.ecc.person.app;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.model.Name;
 
public class RoleServlet extends HttpServlet {

  private final RoleCriteriaDao roleDao = new RoleCriteriaDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    try {
      if (uri.matches("\\A/roles/?\\z")) {
        index(req, res);
      } else if (uri.matches("\\A/roles/new/?\\z")) {
        throw new UnsupportedOperationException("new");
      } else if (uri.matches("\\A/roles/[0-9]+/edit/?\\z")) {
        throw new UnsupportedOperationException("edit");
      } else if (uri.matches("\\A/roles/[0-9]+/delete/?\\z")) {
        throw new UnsupportedOperationException("delete");
      } else {
        throw new RuntimeException(
          String.format("No Role action matches URI '%s'", uri));
      }

    } catch (RuntimeException e) {
      error(req, res, e.getMessage());
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    try {
      if (uri.matches("\\A/roles/?\\z")) {
        throw new UnsupportedOperationException("create");
      } else if (uri.matches("\\A/roles/[0-9]+/?\\z")) {
        throw new UnsupportedOperationException("update");
      } else {
        throw new RuntimeException(
          String.format("No Role action matches URI '%s'", uri));
      }

    } catch (RuntimeException e) {
      error(req, res, e.getMessage());
    }
  }

  private void index(HttpServletRequest req, HttpServletResponse res) {
    Session dbSession = Sessions.getSession();

    try {
      List<Role> roles = Transactions.get(dbSession, roleDao, () ->
        roleDao.getAll());

      req.setAttribute("roles", roles);
      req.getRequestDispatcher("/WEB-INF/views/roles/index.jsp")
         .forward(req, res);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      dbSession.close();
    }
  }

  private void delete(HttpServletRequest req, HttpServletResponse res) {
    final long roleId = getRoleId(req.getRequestURI());

    if (roleId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        Transactions.conduct(dbSession, roleDao, () -> {
          roleDao.delete(roleDao.get(roleId));
        });
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        dbSession.close();
      }
    }
  }

  private void error(HttpServletRequest req, 
                     HttpServletResponse res,
                     String errMsg)
    throws ServletException, IOException
  {
    req.setAttribute("errMsg", errMsg);
    req.getRequestDispatcher("/WEB-INF/views/roles/error.jsp")
       .forward(req, res);
  }

  private long getRoleId(String uri) {
    long roleId = -1;
    
    try {
      roleId = Long.parseLong(uri.replaceAll("[^0-9]", ""));
    } catch (NumberFormatException e) {

    }

    return roleId;
  }
  
}
package com.exist.ecc.person.app;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.RoleCriteriaDao;

import com.exist.ecc.person.core.model.Role;
import com.exist.ecc.person.core.model.Name;
 
public class RoleServlet extends AppServlet {

  private final RoleCriteriaDao roleDao = new RoleCriteriaDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    try {
      if (uri.matches("\\A/roles/?\\z")) {
        roleIndex(req, res);
      } else if (uri.matches("\\A/roles/new/?\\z")) {
        roleNew(req, res);
      } else if (uri.matches("\\A/roles/[0-9]+/edit/?\\z")) {
        throw new UnsupportedOperationException("edit");
      } else if (uri.matches("\\A/roles/[0-9]+/delete/?\\z")) {
        roleDelete(req, res);
      } else {
        throw new RuntimeException(
          String.format("No Role action matches URI '%s'", uri));
      }
    } catch (RuntimeException e) {
      setFlashError(req, e.getMessage());
      res.sendRedirect("/");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    try {
      if (uri.matches("\\A/roles/?\\z")) {
        roleCreate(req, res);
      } else if (uri.matches("\\A/roles/[0-9]+/?\\z")) {
        throw new UnsupportedOperationException("update");
      } else {
        throw new RuntimeException(
          String.format("No Role action matches URI '%s'", uri));
      }
    } catch (RuntimeException e) {
      setFlashError(req, e.getMessage());
      res.sendRedirect("/");
    }
  }

  private void roleIndex(HttpServletRequest req, HttpServletResponse res) 
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
      setFlashError(req, e.getMessage());
      res.sendRedirect("/");
    } finally {
      dbSession.close();
    }
  }

  private void roleNew(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    req.getRequestDispatcher("/WEB-INF/views/roles/new.jsp")
       .forward(req, res);
  }

  private void roleCreate(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    Session dbSession = Sessions.getSession();

    final Role role = new Role();
    role.setName(req.getParameter("role[name]"));
  
    try {
      Transactions.conduct(dbSession, roleDao, () -> roleDao.save(role));
      setFlashNotice(req, "Role created");
    } catch (Exception e) {
      setFlashError(req, e.getMessage());
    } finally {
      dbSession.close();
      res.sendRedirect("/roles");
    }
  }

  private void roleDelete(HttpServletRequest req, HttpServletResponse res) 
    throws IOException
  {
    final long roleId = getRoleId(req.getRequestURI());

    if (roleId > 0) {
      Session dbSession = Sessions.getSession();

      try {
        Transactions.conduct(dbSession, roleDao, () -> {
          roleDao.delete(roleDao.get(roleId));
        });

        setFlashNotice(req, "Role deleted");
      } catch (Exception e) {
        setFlashError(req, e.getMessage());
      } finally {
        dbSession.close();
        res.sendRedirect("/roles");
      }

      
    }
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
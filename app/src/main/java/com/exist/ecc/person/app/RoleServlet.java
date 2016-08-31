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
 
public class RoleServlet extends AppServlet {

  private final RoleCriteriaDao roleDao = new RoleCriteriaDao();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    String indexPattern = "\\A/roles/?\\z";
    String newPattern = "\\A/roles/new/?\\z";
    String editPattern = "\\A/roles/[0-9]+/edit/?\\z";
    String deletePattern = "\\A/roles/[0-9]+/delete/?\\z";

    if (uri.matches(indexPattern)) {
      roleIndex(req, res);
    } else if (uri.matches(newPattern)) {
      roleNew(req, res);
    } else if (uri.matches(editPattern)) {
      roleEdit(req, res);
    } else if (uri.matches(deletePattern)) {
      roleDelete(req, res);
    } else {
      String errMsg = String.format("No Role action matches GET '%s'", uri);
      setFlashError(req, errMsg);
      res.sendRedirect("/");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    String createPattern = "\\A/roles/?\\z";
    String updatePattern = "\\A/roles/[0-9]+/?\\z";

    if (uri.matches(createPattern)) {
      roleCreate(req, res);
    } else if (uri.matches(updatePattern)) {
      roleUpdate(req, res);
    } else {
      String errMsg = String.format("No Role action matches POST '%s'", uri);
      setFlashError(req, errMsg);
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

  private void roleEdit(HttpServletRequest req, HttpServletResponse res) 
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
        setFlashError(req, e.getMessage());
        res.sendRedirect("/roles");
      } finally {
        dbSession.close();
      }
    }
  }

  private void roleUpdate(HttpServletRequest req, HttpServletResponse res) 
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
        setFlashNotice(req, "Role updated");
      } catch (Exception e) {
        setFlashError(req, e.getMessage());
      } finally {
        dbSession.close();
        res.sendRedirect("/roles");
      }
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

  protected long getRoleId(String uri) {
    long id = -1;
    
    try {
      id = Long.parseLong(uri.replaceAll("[^0-9]", ""));
    } catch (NumberFormatException e) {

    }

    return id;
  }
  
}
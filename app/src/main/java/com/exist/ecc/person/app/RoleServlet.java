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
 
public class RoleServlet extends HttpServlet {

  private final RoleCriteriaDao roleDao = new RoleCriteriaDao();

  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    res.setContentType("text/html");

    try {
      index(req, res);
    } catch (RuntimeException e) {
      error(req, res, e.getMessage());
    }
  }

  private void index(HttpServletRequest req, HttpServletResponse res) 
    throws ServletException, IOException
  {
    Session dbSession = Sessions.getSession();

    try {
      HttpSession httpSession = req.getSession();

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

  private void error(HttpServletRequest req, 
                     HttpServletResponse res,
                     String errMsg)
    throws ServletException, IOException
  {
    req.setAttribute("errMsg", errMsg);
    req.getRequestDispatcher("/WEB-INF/views/roles/error.jsp")
       .forward(req, res);
  }
  
}
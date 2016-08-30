package com.exist.ecc.person.app;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Person;
import com.exist.ecc.person.core.model.Name;
 
public class PersonServlet extends HttpServlet {

  private final PersonCriteriaDao personDao = new PersonCriteriaDao();

  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    res.setContentType("text/html");

    try {
      long personId = getPersonId(req.getRequestURI());

      if (personId < 0) {
      } else {
        show(req, res, personId);
      }

    } catch (RuntimeException e) {
      res.setStatus(HttpServletResponse.SC_OK);
      res.getWriter().println("<h1>Error</h1>");
      res.getWriter().println("<p>" + e.getMessage() + "</p>");
      res.getWriter().println("<p><a href=\"/persons\">back</a></p>");
    }
  }

  private void show(HttpServletRequest req, HttpServletResponse res, 
    long personId) 
  {
    Session dbSession = Sessions.getSession();

    try {
      HttpSession httpSession = req.getSession();
      
      Person person = Transactions.get(dbSession, personDao, () ->
        personDao.get(personId));
      Name name = person.getName();

      req.setAttribute("firstname", name.getFirstName());
      req.getRequestDispatcher("/WEB-INF/views/persons/show.jsp")
         .forward(req, res);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      dbSession.close();
    }
  }

  private long getPersonId(String requestUri) {
    long personId = -1;
    String personIdString = requestUri.replaceAll("/*persons/*", "");

    if (personIdString.isEmpty()) {
      personId = -1;
    } else {
      try {
        personId = Long.parseLong(personIdString);
      } catch (NumberFormatException e) {
        throw new RuntimeException(e);
      }
    }

    return personId;
  }
  
}
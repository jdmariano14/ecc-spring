package com.exist.ecc.person.app;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.dao.Transactions;
import com.exist.ecc.person.core.dao.impl.PersonCriteriaDao;

import com.exist.ecc.person.core.model.Person;
 
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
        Session session = Sessions.getSession();

        try {
          Person person = Transactions.get(session, personDao, () ->
            personDao.get(personId));

          res.setStatus(HttpServletResponse.SC_OK);
          res.getWriter().println("<h1>" + person.getName().getFirstName() + "</h1>");
          res.getWriter().println("<p><a href=\"/persons\">back</a></p>");
        } catch (Exception e) {
          throw new RuntimeException(e);
        } finally {
          session.close();
        }
      }

    } catch (RuntimeException e) {
      res.setStatus(HttpServletResponse.SC_OK);
      res.getWriter().println("<h1>Error</h1>");
      res.getWriter().println("<p>" + e.getMessage() + "</p>");
      res.getWriter().println("<p><a href=\"/persons\">back</a></p>");
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
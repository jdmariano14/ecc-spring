package com.exist.ecc.person.app;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloServlet extends HttpServlet {

  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    res.setContentType("text/html");
    res.setStatus(HttpServletResponse.SC_OK);
    res.getWriter().println("<h1>Hello Servlet</h1>");
    res.getWriter().println("session=" + req.getSession(true).getId());
  }
  
}
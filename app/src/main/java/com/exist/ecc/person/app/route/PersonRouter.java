package com.exist.ecc.person.app.route;
 
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.PersonController;
import com.exist.ecc.person.app.controller.PersonRoleController;

import com.exist.ecc.person.app.util.FlashUtil;
 
public class PersonRouter extends AppRouter {

  private final PersonController personController = new PersonController();
  private final PersonRoleController personRoleController = 
    new PersonRoleController();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    HashMap<String, String> personRoutes = new HashMap();
    HashMap<String, String> contactRoutes = new HashMap();
    HashMap<String, String> personRoleRoutes = new HashMap();

    personRoutes.put("index", "\\A/persons/?\\z");
    personRoutes.put("show", "\\A/persons/[0-9]+/?\\z");
    personRoutes.put("_new", "\\A/persons/new/?\\z");
    personRoutes.put("edit", "\\A/persons/[0-9]+/edit/?\\z");
    personRoutes.put("delete", "\\A/persons/[0-9]+/delete/?\\z");

    contactRoutes.put(
      "_new", "\\A/persons/[0-9]+/contacts/new/?\\z");
    contactRoutes.put(
      "edit", "\\A/persons/[0-9]+/contacts/[0-9]+/edit/?\\z");
    contactRoutes.put(
      "delete", "\\A/persons/[0-9]+/contacts/[0-9]+/delete/?\\z");

    personRoleRoutes.put(
      "_new", "\\A/persons/[0-9]+/roles/new/?\\z");
    personRoleRoutes.put(
      "delete", "\\A/persons/[0-9]+/roles/[0-9]+/delete/?\\z");

    for (String action : personRoutes.keySet()) {
      if (uri.matches(personRoutes.get(action))) {
        invoke(personController, action, req, res);
        return;
      }
    }

    for (String action : contactRoutes.keySet()) {
      if (uri.matches(contactRoutes.get(action))) {
        res.getWriter().println("<h1>ContactController#" + action + "</h1>");
        return;
      }
    }

    for (String action : personRoleRoutes.keySet()) {
      if (uri.matches(personRoleRoutes.get(action))) {
        invoke(personRoleController, action, req, res);
        return;
      }
    }
     
    String errMsg = String.format("No action matches GET '%s'", uri);
    FlashUtil.setError(req, errMsg);
    res.sendRedirect("/");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();
    
    HashMap<String, String> personRoutes = new HashMap();
    HashMap<String, String> contactRoutes = new HashMap();
    HashMap<String, String> personRoleRoutes = new HashMap();

    personRoutes.put("create", "\\A/persons/?\\z");
    personRoutes.put("update", "\\A/persons/[0-9]+/?\\z");

    contactRoutes.put("create", "\\A/persons/[0-9]+/contacts/?\\z");
    contactRoutes.put("update", "\\A/persons/[0-9]+/contacts/[0-9]+/?\\z");

    personRoleRoutes.put("create", "\\A/persons/[0-9]+/roles/?\\z");

    for (String action : personRoutes.keySet()) {
      if (uri.matches(personRoutes.get(action))) {
        invoke(personController, action, req, res);
        return;
      }
    }

    for (String action : contactRoutes.keySet()) {
      if (uri.matches(contactRoutes.get(action))) {
        res.getWriter().println("<h1>ContactController#" + action + "</h1>");
        return;
      }
    }

    for (String action : personRoleRoutes.keySet()) {
      if (uri.matches(personRoleRoutes.get(action))) {
        invoke(personRoleController, action, req, res);
        return;
      }
    }
     
    String errMsg = String.format("No action matches POST '%s'", uri);
    FlashUtil.setError(req, errMsg);
    res.sendRedirect("/");
  }
}
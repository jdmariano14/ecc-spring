package com.exist.ecc.person.app.route;
 
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.AppController;
import com.exist.ecc.person.app.controller.ContactController;
import com.exist.ecc.person.app.controller.PersonController;
import com.exist.ecc.person.app.controller.PersonRoleController;

import com.exist.ecc.person.app.util.FlashUtil;
 
public class PersonRouter extends AppRouter {

  private final PersonController personController = new PersonController();
  private final ContactController contactController = new ContactController();
  private final PersonRoleController personRoleController = 
    new PersonRoleController();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> personRoutes = new HashMap();
    Map<String, String> contactRoutes = new HashMap();
    Map<String, String> personRoleRoutes = new HashMap();

    routes.put(personController, personRoutes);
    personRoutes.put("index", "\\A/persons/?\\z");
    personRoutes.put("show", "\\A/persons/[0-9]+/?\\z");
    personRoutes.put("_new", "\\A/persons/new/?\\z");
    personRoutes.put("edit", "\\A/persons/[0-9]+/edit/?\\z");
    personRoutes.put("delete", "\\A/persons/[0-9]+/delete/?\\z");

    routes.put(contactController, contactRoutes);
    contactRoutes.put("_new", "\\A/persons/[0-9]+/contacts/new/?\\z");

    routes.put(personRoleController, personRoleRoutes);
    personRoleRoutes.put(
      "_new", "\\A/persons/[0-9]+/roles/new/?\\z");
    personRoleRoutes.put(
      "delete", "\\A/persons/[0-9]+/roles/[0-9]+/delete/?\\z");

    matchRoute(req, res, routes, "GET");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> personRoutes = new HashMap();
    Map<String, String> contactRoutes = new HashMap();
    Map<String, String> personRoleRoutes = new HashMap();

    routes.put(personController, personRoutes);
    personRoutes.put("create", "\\A/persons/?\\z");
    personRoutes.put("update", "\\A/persons/[0-9]+/?\\z");
    personRoutes.put("query", "\\A/persons/query/?\\z");
    personRoutes.put("result", "\\A/persons/result/?\\z");

    routes.put(contactController, contactRoutes);
    contactRoutes.put("create", "\\A/persons/[0-9]+/contacts/?\\z");

    routes.put(personRoleController, personRoleRoutes);
    personRoleRoutes.put("create", "\\A/persons/[0-9]+/roles/?\\z");

    matchRoute(req, res, routes, "POST");
  }
}
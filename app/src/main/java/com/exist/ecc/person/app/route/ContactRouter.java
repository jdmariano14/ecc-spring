package com.exist.ecc.person.app.route;
 
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.AppController;
// import com.exist.ecc.person.app.controller.ContactController;

public class ContactRouter extends AppRouter {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> contactRoutes = new HashMap();

    routes.put(null, contactRoutes);
    contactRoutes.put(
      "edit", "\\A/contacts/[0-9]+/edit/?\\z");
    contactRoutes.put(
      "delete", "\\A/contacts/[0-9]+/delete/?\\z");

    matchRoute(req, res, routes, "GET");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> contactRoutes = new HashMap();

    routes.put(null, contactRoutes);
    contactRoutes.put("update", "\\A/contacts/[0-9]+/?\\z");

    matchRoute(req, res, routes, "POST");
  }
}
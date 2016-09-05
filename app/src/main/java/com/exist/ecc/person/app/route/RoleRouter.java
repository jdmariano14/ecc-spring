package com.exist.ecc.person.app.route;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.AppController;
import com.exist.ecc.person.app.controller.RoleController;

import com.exist.ecc.person.app.flash.FlashUtil;

public class RoleRouter extends AppRouter {

  private final RoleController roleController = new RoleController();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> roleRoutes = new HashMap();

    routes.put(roleController, roleRoutes);
    roleRoutes.put("index", "\\A/roles/?\\z");
    roleRoutes.put("_new", "\\A/roles/new/?\\z");
    roleRoutes.put("edit", "\\A/roles/[0-9]+/edit/?\\z");
    roleRoutes.put("delete", "\\A/roles/[0-9]+/delete/?\\z");

    matchRoute(req, res, routes, "GET");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    Map<AppController, Map<String, String>> routes = new HashMap();
    Map<String, String> roleRoutes = new HashMap();

    routes.put(roleController, roleRoutes);
    roleRoutes.put("create", "\\A/roles/?\\z");
    roleRoutes.put("update", "\\A/roles/[0-9]+/?\\z");

    matchRoute(req, res, routes, "GET");
  }

}
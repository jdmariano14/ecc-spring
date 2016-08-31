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

import com.exist.ecc.person.app.controller.RoleController;

import com.exist.ecc.person.app.util.FlashUtil;

public class RoleRouter extends AppRouter {

  private final RoleController roleController = new RoleController();
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    HashMap<String, String> roleRoutes = new HashMap();

    roleRoutes.put("index", "\\A/roles/?\\z");
    roleRoutes.put("_new", "\\A/roles/new/?\\z");
    roleRoutes.put("edit", "\\A/roles/[0-9]+/edit/?\\z");
    roleRoutes.put("delete", "\\A/roles/[0-9]+/delete/?\\z");

    for (String action : roleRoutes.keySet()) {
      if (uri.matches(roleRoutes.get(action))) {
        invoke(roleController, action, req, res);
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

    HashMap<String, String> roleRoutes = new HashMap();

    roleRoutes.put("create", "\\A/roles/?\\z");
    roleRoutes.put("update", "\\A/roles/[0-9]+/?\\z");

    for (String action : roleRoutes.keySet()) {
      if (uri.matches(roleRoutes.get(action))) {
        invoke(roleController, action, req, res);
        return;
      }
    }

    String errMsg = String.format("No action matches GET '%s'", uri);
    FlashUtil.setError(req, errMsg);
    res.sendRedirect("/");
  }

}
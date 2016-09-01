package com.exist.ecc.person.app.route;

import java.io.IOException;

import java.util.Map;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.AppController;

import com.exist.ecc.person.app.util.FlashUtil;
 
public class AppRouter extends HttpServlet {

  protected void matchRoute(HttpServletRequest req, 
                            HttpServletResponse res,
                            Map<AppController, 
                                Map<String, String>> controllerRoutes,
                            String httpVerb)
    throws ServletException, IOException
  {
    String uri = req.getRequestURI();

    try {
      for (AppController controller : controllerRoutes.keySet()) {
        Map<String, String> actionRoutes = controllerRoutes.get(controller);

        for (String action : actionRoutes.keySet()) {
          if (uri.matches(actionRoutes.get(action))) {
            invoke(controller, action, req, res);
            return;
          }
        }
      }

      String errMsg = String.format(
        "No controller action matches route %s '%s'", httpVerb, uri);
      
      throw new RuntimeException(errMsg);

    } catch (Exception e) {
      FlashUtil.setError(req, e.getMessage());
      res.sendRedirect("/");  
    }
  }

  private void invoke(AppController controller, 
                        String actionStr,
                        HttpServletRequest req,
                        HttpServletResponse res) 
  {
    try {
      Method action = controller.getClass()
                      .getDeclaredMethod(actionStr,
                        HttpServletRequest.class,
                        HttpServletResponse.class);

      action.invoke(controller, req, res);
    } catch (IllegalAccessException 
             | InvocationTargetException 
             | NoSuchMethodException e) 
    {
      e.printStackTrace();
    } 
  }

}
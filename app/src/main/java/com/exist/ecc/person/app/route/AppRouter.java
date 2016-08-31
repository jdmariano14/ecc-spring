package com.exist.ecc.person.app.route;
 
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exist.ecc.person.app.controller.AppController;

import com.exist.ecc.person.app.util.FlashUtil;
 
public class AppRouter extends HttpServlet {

  protected void invoke(AppController controller, 
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
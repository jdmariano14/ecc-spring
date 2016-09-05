package com.exist.ecc.person.app.flash;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

 public class FlashUtil {

  public static void setNotice(HttpServletRequest req, String notice) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", new Flash(notice));
    httpSession.setAttribute("_error", null);
  }

  public static void setError(HttpServletRequest req, String error) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", null);
    httpSession.setAttribute("_error", new Flash(error));
  }
  
}
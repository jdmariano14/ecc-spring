package com.exist.ecc.person.app.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

 public class FlashUtil {

  public static void clear(HttpServletRequest req) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", "");
    httpSession.setAttribute("_error", "");
  }

  public static void setNotice(HttpServletRequest req, String notice) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", notice);
    httpSession.setAttribute("_error", "");
  }

  public static void setError(HttpServletRequest req, String error) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", "");
    httpSession.setAttribute("_error", error);
  }
  
}
package com.exist.ecc.person.app;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

 public class AppServlet extends HttpServlet {

  protected void clearFlash(HttpServletRequest req) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", null);
    httpSession.setAttribute("_error", null);
  }

  protected void setFlashNotice(HttpServletRequest req, String notice) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", notice);
    httpSession.setAttribute("_error", null);
  }

  protected void setFlashError(HttpServletRequest req, String error) {
    HttpSession httpSession = req.getSession();
    httpSession.setAttribute("_notice", null);
    httpSession.setAttribute("_error", error);
  }
  
}
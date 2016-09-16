package com.exist.ecc.person.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;

@Controller
@RequestMapping("/uploads")
public class UploadController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String upload(Model model, @RequestParam String uploadType) {
    model.addAttribute("uploadType", uploadType);
    return "uploads/upload";
  }
}
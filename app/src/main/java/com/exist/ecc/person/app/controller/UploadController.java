package com.exist.ecc.person.app.controller;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.hibernate.Session;

import com.exist.ecc.person.core.dao.Sessions;
import com.exist.ecc.person.core.service.data.impl.RoleDataService;
import com.exist.ecc.person.core.service.input.impl.RoleCsvInputService;

@Controller
@RequestMapping("/uploads")
public class UploadController {

  @Autowired
  private RoleDataService roleDataService;

  @Autowired
  private RoleCsvInputService roleCsvInputService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String upload(Model model, @RequestParam String uploadType) {
    model.addAttribute("uploadType", uploadType);
    return "uploads/upload";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public String process(@RequestParam String uploadType,
                        @RequestParam MultipartFile file)
  {
    String path = null;

    Session dbSession = Sessions.getSession();
    roleDataService.setSession(dbSession);

    try {
      roleCsvInputService.execute(file.getInputStream(), false); 
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/roles";
    }

    return path;
  }

  @RequestMapping(value = "", method = RequestMethod.POST, params={"clear"})
  public String processClear(@RequestParam String uploadType,
                             @RequestParam MultipartFile file)
  {
    String path = null;

    Session dbSession = Sessions.getSession();
    roleDataService.setSession(dbSession);

    try {
      roleCsvInputService.execute(file.getInputStream(), true); 
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      dbSession.close();
      path = "redirect:/roles";
    }

    return path;
  }

}
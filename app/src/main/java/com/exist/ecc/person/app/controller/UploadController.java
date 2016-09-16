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

@Controller
@RequestMapping("/uploads")
public class UploadController {

  @RequestMapping(value = "", method = RequestMethod.GET)
  public String upload(Model model, @RequestParam String uploadType) {
    model.addAttribute("uploadType", uploadType);
    return "uploads/upload";
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public @ResponseBody String process(@RequestParam String uploadType,
                                      @RequestParam MultipartFile file)
  {
    String body = "";

    try {
      Scanner scanner = new Scanner(file.getInputStream());

      while(scanner.hasNext()) {
        body += scanner.nextLine() + System.lineSeparator();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }

    return body;
  }

}
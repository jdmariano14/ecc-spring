package com.exist.ecc.person.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.exist.ecc.person.core.service.data.impl.RoleDataService;
import com.exist.ecc.person.core.service.input.InputServiceFactory;
import com.exist.ecc.person.core.service.input.api.InputService;

public class UploadsController extends MultiActionController {
  
  private InputServiceFactory inputServiceFactory;

  public void setInputServiceFactory(InputServiceFactory inputServiceFactory) {
    this.inputServiceFactory = inputServiceFactory;
  }

  public ModelAndView upload(HttpServletRequest req, HttpServletResponse res)
    throws Exception 
  {
    Model model = new ExtendedModelMap();
    String view = null;

    model.addAttribute("uploadType", req.getParameter("uploadType"));
    view = "uploads/upload";

    return new ModelAndView(view, model.asMap());
  }

  public String process(HttpServletRequest req, HttpServletResponse res) 
    throws Exception
  {
    String view = null;
    String uploadType = "Persons";

    try {
      boolean clear = req.getParameter("clear") != null;

      System.out.println(clear);

      uploadType = req.getParameter("uploadType");

      InputService inputService = 
        inputServiceFactory.get(uploadType, "csv");

      InputStream inputStream = getUploadedFileInputStream(req);

      inputService.execute(inputStream, clear);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      view = "redirect:/uploads/upload?uploadType=" + uploadType;
    }

    return view;
  }

  private InputStream getUploadedFileInputStream(HttpServletRequest req) {
    try {
      Optional<InputStream> optional = 
        req.getParts().stream()
           .map(p -> getPartInputStream(p))
           .reduce((s1, s2) -> new SequenceInputStream(s1, s2));

      return optional.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private InputStream getPartInputStream(Part part) {
    try {
      return part.getInputStream(); 
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.services.ProfesorServices;

@Controller
public class ProfesorController {
  @Autowired
  private ProfesorServices service;
  
  @GetMapping("/profesores")
  public String profesores (Model model) {
    model.addAttribute("profesores", service.getAllProfesores());
    return "profesores";
  }
}
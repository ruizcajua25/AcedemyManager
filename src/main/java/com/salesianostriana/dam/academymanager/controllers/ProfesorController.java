package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.services.ProfesorService;


@Controller
public class ProfesorController {
  @Autowired
  private ProfesorService service;
  
  @GetMapping("/profesores")
  public String profesores(Model model) {
    model.addAttribute("profesores", service.findAll());
    return "profesores";
  }

  @PostMapping("/profesores")
  public String crearProfesor(@ModelAttribute("profesor") Profesor profesor) {
    service.save(profesor);
    return "redirect:/profesores";
  }
}
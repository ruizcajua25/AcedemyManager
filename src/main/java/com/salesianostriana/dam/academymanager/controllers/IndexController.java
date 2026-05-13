package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Profesor;


@Controller
public class IndexController {
  
  @GetMapping("/")
  public String index () {
    return "index.html";
  }

  @GetMapping("/formulario")
  public String formulario(Model model) {
    model.addAttribute("profesor", new Profesor());
    return "formulario";
  }
}
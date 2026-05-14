package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AcademiaController {
  @GetMapping("/academia/add")
  public String index () {
    return "academia/create";
  }

  @GetMapping("/academias")
  public String formulario(Model model) {
    return "academia/index";
  }
}
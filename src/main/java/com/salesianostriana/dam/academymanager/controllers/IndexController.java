package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Usuario;



@Controller
public class IndexController {
  
  @GetMapping("/")
  public String index () {
    return "index.html";
  }


  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("usuario", new Usuario());
    return "login";
  }
}
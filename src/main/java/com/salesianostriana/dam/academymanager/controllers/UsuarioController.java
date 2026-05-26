package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Usuario;


@Controller
public class UsuarioController {
  @GetMapping("/perfil")
  public String miUsuario(Model model) {
    model.addAttribute("usuario", 
    Usuario
    .builder()
    .nombre("Juan")
    .apellidos("Ruiz Campanario")
    .email("juan@gmail.com")
    .build()
  );
    
    return "usuario/perfil";
  }
  
  
}
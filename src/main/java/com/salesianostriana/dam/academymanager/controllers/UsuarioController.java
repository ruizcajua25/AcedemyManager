package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Usuario;


@Controller
public class UsuarioController {
  @GetMapping("/perfil")
  public String miUsuario(@AuthenticationPrincipal Usuario usuario, Model model) {
    model.addAttribute("usuario", usuario);
    return "usuario/perfil";
  }
}
package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.OfertaService;


@Controller
public class UsuarioController {
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private OfertaService ofertaService;

  @GetMapping("/perfil")
  public String miUsuario(@AuthenticationPrincipal Usuario usuario, Model model) {
    
    model.addAttribute("academias", academiaService.findAllByUsuario(usuario.getId()));
    model.addAttribute("usuario", usuario);
    model.addAttribute("ofertasAplicadas", ofertaService.findByUsuarioId(usuario.getId()));
    return "usuario/perfil";
  }
}
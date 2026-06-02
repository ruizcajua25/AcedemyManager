package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.UsuarioService;


@Controller
public class IndexController {
  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String index (Model model) {
    return "index.html";
  }


  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("usuario", new Usuario());
    return "login";
  }

  @GetMapping("/registro")
  public String registro(Model model) {
    model.addAttribute("usuario", new Usuario());
    return "registro";
  }

  @PostMapping("/registro")
  public String procesarRegistro(@ModelAttribute("usuario") Usuario usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    usuarioService.save(usuario);
    return "redirect:/login";
  }
}
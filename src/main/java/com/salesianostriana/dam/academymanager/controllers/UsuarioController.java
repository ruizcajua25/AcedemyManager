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
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.UsuarioService;
import java.util.List;


@Controller
public class UsuarioController {
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private UsuarioService usuarioService;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/perfil")
  public String miUsuario(@AuthenticationPrincipal Usuario usuario, Model model) {
    var academias = academiaService.findAllByUsuario(usuario.getId());
    
    int totalDirector = academias.getOrDefault("director", List.of()).size();
    int totalAlumno = academias.getOrDefault("alumno", List.of()).size();
    int totalProfesor = academias.getOrDefault("profesor", List.of()).size();
    
    model.addAttribute("academias", academias);
    model.addAttribute("usuario", usuario);
    model.addAttribute("totalDirector", totalDirector);
    model.addAttribute("totalAlumno", totalAlumno);
    model.addAttribute("totalProfesor", totalProfesor);
    model.addAttribute("totalAcademias", totalDirector + totalAlumno + totalProfesor);
    return "usuario/perfil";
  }

  @GetMapping("/perfil/editar")
  public String editarPerfil(@AuthenticationPrincipal Usuario usuario, Model model) {
    model.addAttribute("usuario", usuario);
    return "usuario/editar-perfil";
  }

  @PostMapping("/perfil/editar")
  public String guardarPerfil(@AuthenticationPrincipal Usuario usuarioOriginal, @ModelAttribute Usuario datos) {
    usuarioOriginal.setNombre(datos.getNombre());
    usuarioOriginal.setApellidos(datos.getApellidos());
    usuarioOriginal.setDni(datos.getDni());
    usuarioOriginal.setEmail(datos.getEmail());

    if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
      usuarioOriginal.setPassword(passwordEncoder.encode(datos.getPassword()));
    }

    usuarioService.edit(usuarioOriginal);
    return "redirect:/perfil";
  }
}
package com.salesianostriana.dam.academymanager.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.AlumnoService;
import com.salesianostriana.dam.academymanager.services.DirectorService;
import com.salesianostriana.dam.academymanager.services.ProfesorService;


@Controller
public class UsuarioController {
  @Autowired
  private AcademiaService academiaService;

  @Autowired
  private DirectorService directorService;
  @Autowired
  private AlumnoService alumnoService;
  @Autowired
  private ProfesorService profesorService;

  @GetMapping("/perfil")
  public String miUsuario(@AuthenticationPrincipal Usuario usuario, Model model) {
    HashMap<String, List<Academia>> academias = new HashMap<>();
    directorService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuario.getId())).forEach(d -> {
      academias.putIfAbsent("director", new ArrayList<>());
      academias.get("director").add(d.getAcademia());
    });
    alumnoService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuario.getId())).forEach(a -> {
      academias.putIfAbsent("alumno", new ArrayList<>());
      academias.get("alumno").add(a.getAcademia());
    });
    profesorService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuario.getId())).forEach(p -> {
      academias.putIfAbsent("profesor", new ArrayList<>());
      academias.get("profesor").add(p.getAcademia());
    });
    model.addAttribute("academias", academias);
    model.addAttribute("usuario", usuario);
    return "usuario/perfil";
  }
}
package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.DirectorService;


@Controller
public class AcademiaController {
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private DirectorService directorService;

  @GetMapping("/academia/create")
  public String index (Model model) { 
    model.addAttribute("academia", new Academia());
    return "academia/create";
  }

  @PostMapping("/academia/create")
  public String create (@AuthenticationPrincipal Usuario usuario ,@ModelAttribute Academia academia) {
    academiaService.save(academia);

    TipoUsuarioId id = TipoUsuarioId.builder()
      .academiaId(academia.getId())
      .usuarioId(usuario.getId())
      .build();

    Director director = Director.builder()
      .id(id)
      .academia(academia)
      .usuario(usuario)
      .build();

    directorService.save(director);
    return "redirect:/academias";
  }

  @GetMapping("/academias")
  public String formulario(Model model) {
    return "academia/index";
  }
}
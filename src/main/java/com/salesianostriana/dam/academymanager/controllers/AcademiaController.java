package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.CursoService;
import com.salesianostriana.dam.academymanager.services.DirectorService;
import com.salesianostriana.dam.academymanager.services.OfertaService;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class AcademiaController {
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private DirectorService directorService;
  @Autowired
  private CursoService cursoService;
  @Autowired
  private OfertaService ofertaService; 



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
    return "redirect:/perfil";
  }

  @GetMapping("/academias/find")
  public String formulario(Model model) {
    model.addAttribute("academias", academiaService.findAll());
    return "academia/find";
  }

  @GetMapping("/academias/{id}")
  public String detalle(@PathVariable("id") String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Academia academia = academiaService.findById(id).orElseThrow(() -> new RuntimeException("No se encontró la academia con ID: " + id));
    boolean isDirector = academia.getDirectores().stream().anyMatch(director -> director.getId().getUsuarioId().equals(usuario.getId()));
    model.addAttribute("director", isDirector);
    model.addAttribute("academia", academia);
    model.addAttribute("ofertasActivas", ofertaService.findByAcademia(academia).stream().filter(oferta -> ofertaService.esOfertaAplicable(oferta)).toList());
    model.addAttribute("cursosActivos", academia.getCursos().stream().filter(curso -> cursoService.esCursoActivo(curso)).toList());
    model.addAttribute("alumno", academia.getAlumnos().stream().filter(alumno -> alumno.getUsuario().getId().equals(usuario.getId())).findFirst().orElse(null));
    model.addAttribute("cursos", academia.getCursos());
    return "academia/detalle";
  }

  @GetMapping("/academias/mi")
  public String misAcademias(@AuthenticationPrincipal Usuario usuario, Model model) {
    model.addAttribute("academias", academiaService.findAllByUsuario(usuario.getId()));
    return "academia/mi";
  }
  
}
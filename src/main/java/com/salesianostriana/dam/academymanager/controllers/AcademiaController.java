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
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.AlumnoService;
import com.salesianostriana.dam.academymanager.services.CursoService;
import com.salesianostriana.dam.academymanager.services.OfertaService;


@Controller
public class AcademiaController {
  private final AlumnoService alumnoService;
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private CursoService cursoService;
  @Autowired
  private OfertaService ofertaService;


  AcademiaController(AlumnoService alumnoService) {
    this.alumnoService = alumnoService;
  } 


  @GetMapping("/academia/create")
  public String index (Model model) { 
    model.addAttribute("academia", new Academia());
    return "academia/create";
  }

  @PostMapping("/academia/create")
  public String create (@AuthenticationPrincipal Usuario usuario, @ModelAttribute Academia academia) {
    academiaService.crearAcademiaConDirector(academia, usuario);
    return "redirect:/perfil";
  }

  @GetMapping("/academias/{id}/editar")
  public String editarFormulario(@PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Academia academia = academiaService.findByIdAndDirectorOrThrow(id, usuario.getId());
    model.addAttribute("academia", academia);
    return "academia/editar";
  }

  @PostMapping("/academias/{id}/editar")
  public String editar(@PathVariable String id, @ModelAttribute Academia academia, @AuthenticationPrincipal Usuario usuario) {
    Academia academiaOriginal = academiaService.findByIdAndDirectorOrThrow(id, usuario.getId());

    academiaOriginal.setNombre(academia.getNombre());
    academiaOriginal.setDescripcion(academia.getDescripcion());
    academiaOriginal.setDireccion(academia.getDireccion());
    academiaOriginal.setTelefono(academia.getTelefono());
    academiaOriginal.setEmail(academia.getEmail());

    academiaService.save(academiaOriginal);
    return "redirect:/academias/" + id;
  }

  @GetMapping("/academias/find")
  public String formulario(Model model) {
    model.addAttribute("academias", academiaService.findAll());
    return "academia/find";
  }

  @GetMapping("/academias/{id}")
  public String detalle(@PathVariable("id") String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Academia academia = academiaService.findById(id).orElseThrow(() -> new com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException("No se encontro la academia con ID: " + id));
    boolean isDirector = academiaService.esDirector(id, usuario.getId());
    model.addAttribute("director", isDirector);
    model.addAttribute("academia", academia);
    model.addAttribute("ofertasActivas", ofertaService.findOfertasActivasByAcademia(id));
    model.addAttribute("cursosActivos", cursoService.findCursosActivosByAcademia(id));
    model.addAttribute("alumno", alumnoService.findByUsuarioIdAndAcademiaId(id, usuario.getId()));
    model.addAttribute("cursos", academia.getCursos());
    return "academia/detalle";
  }

  @GetMapping("/academias/mi")
  public String misAcademias(@AuthenticationPrincipal Usuario usuario, Model model) {
    model.addAttribute("academias", academiaService.findAllByUsuario(usuario.getId()));
    return "academia/mi";
  }
  
}

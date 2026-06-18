package com.salesianostriana.dam.academymanager.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.AlumnoService;
import com.salesianostriana.dam.academymanager.services.CursoService;
import com.salesianostriana.dam.academymanager.services.OfertaService;
import com.salesianostriana.dam.academymanager.services.ProfesorService;


@Controller
public class AcademiaController {
  private final AlumnoService alumnoService;
  private final ProfesorService profesorService;
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private CursoService cursoService;
  @Autowired
  private OfertaService ofertaService;


  AcademiaController(AlumnoService alumnoService, ProfesorService profesorService) {
    this.alumnoService = alumnoService;
    this.profesorService = profesorService;
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
  public String formulario(Model model, @AuthenticationPrincipal Usuario usuario) {
    List<Academia> academias = academiaService.findAllOrdenadasPorValoracion();
    Map<String, Double> valoraciones = academiaService.calcularValoraciones(academias);
    Map<String, Integer> topPosiciones = academiaService.calcularTopPosiciones();

    Set<String> misAcademiasIds = new HashSet<>();
    if (usuario != null) {
      Map<String, List<Academia>> misAcademias = academiaService.findAllByUsuario(usuario.getId());
      for (List<Academia> lista : misAcademias.values()) {
        for (Academia academia : lista) {
          misAcademiasIds.add(academia.getId());
        }
      }
    }

    model.addAttribute("academias", academias);
    model.addAttribute("valoraciones", valoraciones);
    model.addAttribute("topPosiciones", topPosiciones);
    model.addAttribute("misAcademiasIds", misAcademiasIds);
    return "academia/find";
  }

  @GetMapping("/academias/{id}")
  public String detalle(@PathVariable("id") String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Academia academia = academiaService.findById(id).orElseThrow(() -> new com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException("No se encontro la academia con ID: " + id));
    boolean isDirector = academiaService.esDirector(id, usuario.getId());
    boolean esAlumno = alumnoService.findByUsuarioIdAndAcademiaId(id, usuario.getId()) != null;
    boolean esProfesor = profesorService.findById(com.salesianostriana.dam.academymanager.modules.TipoUsuarioId.builder()
        .academiaId(id).usuarioId(usuario.getId()).build()).isPresent();
    boolean pertenece = isDirector || esAlumno || esProfesor;
    List<Curso> misCursos = cursoService.findCursosByUsuarioAndAcademia(usuario.getId(), id);
    Set<String> misCursosIds = misCursos.stream().map(Curso::getId).collect(Collectors.toSet());

    model.addAttribute("director", isDirector);
    model.addAttribute("pertenece", pertenece);
    model.addAttribute("academia", academia);
    model.addAttribute("valoracion", academiaService.calcularValoracion(academia));
    model.addAttribute("posicionTop", academiaService.calcularPosicionTop(academia));
    model.addAttribute("ofertasActivas", ofertaService.findOfertasActivasByAcademia(id));
    model.addAttribute("cursosActivos", cursoService.findCursosActivosByAcademia(id));
    model.addAttribute("alumno", alumnoService.findByUsuarioIdAndAcademiaId(id, usuario.getId()));
    model.addAttribute("cursos", cursoService.findByAcademiaOrderByNombre(id));
    model.addAttribute("misCursosIds", misCursosIds);
    return "academia/detalle";
  }

  @GetMapping("/academias/mi")
  public String misAcademias(@AuthenticationPrincipal Usuario usuario, Model model) {
    model.addAttribute("academias", academiaService.findAllByUsuario(usuario.getId()));
    return "academia/mi";
  }
  
}

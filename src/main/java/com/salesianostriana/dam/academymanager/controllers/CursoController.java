package com.salesianostriana.dam.academymanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.academymanager.exceptions.AccionNoPermitidaException;
import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.CursoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/academias/{academiaId}/cursos")
public class CursoController {
  @Autowired
  private CursoService cursoService;
  @Autowired
  private AcademiaService academiaService;

  @GetMapping("/{id}")
  public String detalles(Model model, @PathVariable String academiaId, @PathVariable String id, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);
    model.addAttribute("curso", curso);
    model.addAttribute("editable", academiaService.esDirector(academiaId, usuario.getId()));
    model.addAttribute("plazasDisponibles", cursoService.plazasDisponibles(curso));
    return "cursos/detalle";
  }
  
  @GetMapping("/{id}/personas")
  public String personas(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    model.addAttribute("curso", curso);
    model.addAttribute("profesores", curso.getProfesores());
    model.addAttribute("alumnos", curso.getAlumnos());
    model.addAttribute("editable", academiaService.esDirector(academiaId, usuario.getId()));
    model.addAttribute("profesoresDisponibles", cursoService.findProfesoresDisponibles(curso));
    model.addAttribute("alumnosDisponibles", cursoService.findAlumnosDisponibles(curso));
    model.addAttribute("plazasDisponibles", cursoService.plazasDisponibles(curso));

    return "cursos/personas";
  }

  @GetMapping("/{id}/profesores/add")
  public String addProfesorForm(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    model.addAttribute("curso", curso);
    model.addAttribute("profesoresDisponibles", cursoService.findProfesoresDisponibles(curso));

    return "cursos/add-profesores";
  }

  @GetMapping("/{id}/alumnos/add")
  public String addAlumnoForm(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    model.addAttribute("curso", curso);
    model.addAttribute("alumnosDisponibles", cursoService.findAlumnosDisponibles(curso));
    model.addAttribute("plazasDisponibles", cursoService.plazasDisponibles(curso));

    return "cursos/add-alumnos";
  }
  
  
  @GetMapping("/crear")
  public String crearCurso(Model model, @PathVariable String academiaId) {
    Academia academia = academiaService.findById(academiaId).orElseThrow(() -> new IllegalArgumentException("Academia no encontrada"));
    model.addAttribute("curso", Curso.builder().academia(academia).build());
    return "/cursos/crear";
  }

  @PostMapping("/crear")
  public String crearCursoAPI(@PathVariable String academiaId, @ModelAttribute Curso curso) {
    Academia academia = academiaService.findById(academiaId).orElseThrow(() -> new IllegalArgumentException("Academia no encontrada"));
    curso.setAcademia(academia);
    cursoService.save(curso);
    return "redirect:/academias/{academiaId}/cursos/" + curso.getId();
  }

  @GetMapping("/{id}/editar")
  public String editarCurso(Model model, @PathVariable String academiaId, @PathVariable String id) {
    Curso curso = cursoService.findByIdOrThrow(id);
    model.addAttribute("curso", curso);
    model.addAttribute("profesores", curso.getAcademia().getProfesores());
    model.addAttribute("alumnos", curso.getAcademia().getAlumnos());
    return "/cursos/editar";
  }

  @PostMapping("/{id}/editar")
  public String editarCursoAPI(@PathVariable String id, @PathVariable String academiaId, @ModelAttribute Curso curso) {
    cursoService.editarCurso(id, curso.getNombre(), curso.getDescripcion(), curso.getFechaInicio(), curso.getFechaFin(), curso.getCupoMaximo());
    return "redirect:/academias/{academiaId}/cursos/" + id;
  }

  @PostMapping("/{id}/borrar")
  public String deleteCursoAPI(@PathVariable String academiaId, @PathVariable String id, @AuthenticationPrincipal Usuario usuario) {
    cursoService.deleteById(id);
    return "redirect:/academias/{academiaId}";
  }

  @PostMapping("/{id}/profesores/add")
  public String addProfesor(@PathVariable String academiaId, @PathVariable String id, @RequestParam(required = false) List<String> profesorId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    cursoService.addProfesoresToCurso(curso, profesorId, academiaId);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/alumnos/add")
  public String addAlumno(@PathVariable String academiaId, @PathVariable String id, @RequestParam(required = false) List<String> alumnoId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    cursoService.addAlumnosToCurso(curso, alumnoId, academiaId);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/alumnos/{alumnoId}/eliminar")
  public String deleteAlumno(@PathVariable String academiaId, @PathVariable String id, @PathVariable String alumnoId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    cursoService.eliminarAlumno(curso, alumnoId);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/profesores/{profesorId}/eliminar")
  public String deleteProfesor(@PathVariable String academiaId, @PathVariable String id, @PathVariable String profesorId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findByIdOrThrow(id);

    if (!academiaService.esDirector(academiaId, usuario.getId())) {
      throw new AccionNoPermitidaException("No tienes permiso para editar este curso");
    }

    cursoService.removeProfesorFromCurso(curso, profesorId);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }
}

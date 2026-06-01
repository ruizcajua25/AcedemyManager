package com.salesianostriana.dam.academymanager.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AlumnoService;
import com.salesianostriana.dam.academymanager.services.CursoService;

import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.ProfesorService;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/academias/{academiaId}/cursos")
public class CursoController {
  @Autowired
  private CursoService cursoService;
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private ProfesorService profesorService;
  @Autowired
  private AlumnoService alumnoService;

  @GetMapping("/{id}")
  public String detalles(Model model, @PathVariable String academiaId, @PathVariable String id, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

    model.addAttribute("curso", curso);
    model.addAttribute("editable", curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId())));
    return "cursos/detalle";
  }
  
  @GetMapping("/{id}/personas")
  public String personas(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    model.addAttribute("curso", curso);
    model.addAttribute("profesores", curso.getProfesores());
    model.addAttribute("alumnos", curso.getAlumnos());
    model.addAttribute("editable", editable);
    model.addAttribute("profesoresDisponibles", curso.getAcademia().getProfesores().stream()
      .filter(profesor -> curso.getProfesores().stream().noneMatch(p -> p.getId().equals(profesor.getId())))
      .toList());
    model.addAttribute("alumnosDisponibles", curso.getAcademia().getAlumnos().stream()
      .filter(alumno -> curso.getAlumnos().stream().noneMatch(a -> a.getId().equals(alumno.getId())))
      .toList());

    return "cursos/personas";
  }

  @GetMapping("/{id}/profesores/add")
  public String addProfesorForm(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    model.addAttribute("curso", curso);
    model.addAttribute("profesoresDisponibles", curso.getAcademia().getProfesores().stream()
      .filter(profesor -> curso.getProfesores().stream().noneMatch(p -> p.getId().equals(profesor.getId())))
      .toList());

    return "cursos/add-profesores";
  }

  @GetMapping("/{id}/alumnos/add")
  public String addAlumnoForm(@PathVariable String academiaId, @PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    model.addAttribute("curso", curso);
    model.addAttribute("alumnosDisponibles", curso.getAcademia().getAlumnos().stream()
      .filter(alumno -> curso.getAlumnos().stream().noneMatch(a -> a.getId().equals(alumno.getId())))
      .toList());

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
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    model.addAttribute("curso", curso);
    model.addAttribute("profesores", curso.getAcademia().getProfesores());
    model.addAttribute("alumnos", curso.getAcademia().getAlumnos());
    return "/cursos/editar";
  }

  @PostMapping("/{id}/editar")
  public String editarCursoAPI(@PathVariable String id, @PathVariable String academiaId, @ModelAttribute Curso curso) {
    Curso cursoOriginal = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

    cursoOriginal.setNombre(curso.getNombre());
    cursoOriginal.setDescripcion(curso.getDescripcion());

    cursoService.save(cursoOriginal);
    return "redirect:/academias/{academiaId}/cursos/" + id;
  }

  @PostMapping("/{id}/borrar")
  public String deleteCursoAPI(@PathVariable String academiaId, @PathVariable String id, @AuthenticationPrincipal Usuario usuario) {
    cursoService.deleteById(id);
    return "redirect:/academias/{academiaId}";
  }

  @PostMapping("/{id}/profesores/add")
  public String addProfesor(@PathVariable String academiaId, @PathVariable String id, @RequestParam(required = false) List<String> profesorId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    Set<Profesor> profesores = new HashSet<>(curso.getProfesores());
    if (profesorId != null) {
      for (String p : profesorId) {
        profesores.add(
          profesorService.findById(TipoUsuarioId.builder()
          .academiaId(academiaId)
          .usuarioId(p)
          .build()).orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"))
        );
      }
    }
    curso.setProfesores(profesores);

    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/alumnos/add")
  public String addAlumno(@PathVariable String academiaId, @PathVariable String id, @RequestParam(required = false) List<String> alumnoId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    Set<Alumno> alumnos = new HashSet<>(curso.getAlumnos());
    if (alumnoId != null) {
      for (String a : alumnoId) {
        alumnos.add(
          alumnoService.findById(TipoUsuarioId.builder()
          .academiaId(academiaId)
          .usuarioId(a)
          .build()).orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado")
        ));
      }
    }
    curso.setAlumnos(alumnos);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/alumnos/{alumnoId}/eliminar")
  public String deleteAlumno(@PathVariable String academiaId, @PathVariable String id, @PathVariable String alumnoId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    cursoService.eliminarAlumno(curso, alumnoId);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }

  @PostMapping("/{id}/profesores/{profesorId}/eliminar")
  public String deleteProfesor(@PathVariable String academiaId, @PathVariable String id, @PathVariable String profesorId, @AuthenticationPrincipal Usuario usuario) {
    Curso curso = cursoService.findById(id).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
    boolean editable = curso.getAcademia().getDirectores().stream().anyMatch(d -> d.getUsuario().getId().equals(usuario.getId()));

    if (!editable) {
      throw new IllegalArgumentException("No tienes permiso para editar este curso");
    }

    Set<Profesor> profesores = new HashSet<>(curso.getProfesores());
    profesores.removeIf(profesor -> profesor.getUsuario().getId().equals(profesorId));
    curso.setProfesores(profesores);
    cursoService.save(curso);

    return "redirect:/academias/{academiaId}/cursos/" + id + "/personas";
  }
}

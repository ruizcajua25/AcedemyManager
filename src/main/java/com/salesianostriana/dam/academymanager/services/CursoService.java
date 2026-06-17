package com.salesianostriana.dam.academymanager.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException;
import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.repositories.AlumnoRepository;
import com.salesianostriana.dam.academymanager.repositories.CursoRepository;
import com.salesianostriana.dam.academymanager.repositories.ProfesorRepository;

@Service
public class CursoService extends BaseService<Curso, String, CursoRepository> {
  @Autowired
  private ProfesorRepository profesorRepository;
  @Autowired
  private AlumnoRepository alumnoRepository;

  public void eliminarAlumno(Curso curso, String alumnoId) {
    curso.getAlumnos().removeIf(alumno -> alumno.getUsuario().getId().equals(alumnoId));
  }

  public boolean esCursoActivo (Curso curso) {
    if(curso.getFechaInicio() == null) {
      return true;
    }
    return curso.getFechaInicio().isBefore(LocalDate.now()) && curso.getFechaFin().isAfter(LocalDate.now());
  }

  public int totalUsuariosEnCurso(Curso curso) {
    int alumnos = curso.getAlumnos() != null ? curso.getAlumnos().size() : 0;
    int profesores = curso.getProfesores() != null ? curso.getProfesores().size() : 0;
    return alumnos + profesores;
  }

  public Map<String, Integer> resumenUsuariosEnCurso(Curso curso) {
    Map<String, Integer> resumen = new java.util.HashMap<>();
    int alumnos = curso.getAlumnos() != null ? curso.getAlumnos().size() : 0;
    int profesores = curso.getProfesores() != null ? curso.getProfesores().size() : 0;
    resumen.put("alumnos", alumnos);
    resumen.put("profesores", profesores);
    resumen.put("total", alumnos + profesores);
    return resumen;
  }

  public int contarAlumnosUnicosEnCursosActivos(Academia academia) {
    return findCursosActivosByAcademia(academia.getId()).stream()
      .flatMap(curso -> curso.getAlumnos() != null ? curso.getAlumnos().stream() : java.util.stream.Stream.empty())
      .map(alumno -> alumno.getUsuario().getId())
      .collect(Collectors.toSet())
      .size();
  }

  public double calcularRatioAlumnosPorProfesorEnCursosActivos(Academia academia) {
    List<Curso> activos = findCursosActivosByAcademia(academia.getId());
    if (activos.isEmpty()) {
      return 0.0;
    }
    return activos.stream()
      .mapToDouble(curso -> {
        int alumnos = curso.getAlumnos() != null ? curso.getAlumnos().size() : 0;
        int profesores = curso.getProfesores() != null ? curso.getProfesores().size() : 0;
        return profesores == 0 ? (double) alumnos : (double) alumnos / profesores;
      })
      .average()
      .orElse(0.0);
  }

  public double calcularDuracionMediaCursosActivos(Academia academia) {
    List<Curso> activos = findCursosActivosByAcademia(academia.getId());
    return activos.stream()
      .filter(curso -> curso.getFechaInicio() != null && curso.getFechaFin() != null)
      .mapToLong(curso -> ChronoUnit.DAYS.between(curso.getFechaInicio(), curso.getFechaFin()))
      .average()
      .orElse(0.0);
  }

  public List<Curso> findCursosActivosByAcademia(String academiaId) {
    LocalDate now = LocalDate.now();
    return repository.findByAcademiaIdAndFechaInicioBeforeAndFechaFinAfter(academiaId, now, now);
  }

  public List<Curso> findByAcademia(String academiaId) {
    return repository.findByAcademiaId(academiaId);
  }

  public List<Curso> findCursosSinProfesoresByAcademia(String academiaId) {
    return repository.findByAcademiaIdAndProfesoresIsEmpty(academiaId);
  }

  public List<Curso> findByAcademiaOrderByNombre(String academiaId) {
    return repository.findByAcademiaIdOrderByNombre(academiaId);
  }

  public boolean esDirectorDeCurso(String cursoId, String usuarioId) {
    Curso curso = findById(cursoId).orElseThrow(() -> new ObjetoNoEncontradoException("Curso no encontrado"));
    return curso.getAcademia().getDirectores().stream()
      .anyMatch(d -> d.getUsuario().getId().equals(usuarioId));
  }

  public Curso findByIdOrThrow(String cursoId) {
    return findById(cursoId).orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
  }

  public void addProfesoresToCurso(Curso curso, List<String> profesorIds, String academiaId) {
    if (profesorIds != null) {
      for (String pid : profesorIds) {
        curso.getProfesores().add(
          profesorRepository.findById(TipoUsuarioId.builder()
            .academiaId(academiaId)
            .usuarioId(pid)
            .build()).orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"))
        );
      }
    }
  }

  public void addAlumnosToCurso(Curso curso, List<String> alumnoIds, String academiaId) {
    if (alumnoIds != null) {
      for (String aid : alumnoIds) {
        curso.getAlumnos().add(
          alumnoRepository.findById(TipoUsuarioId.builder()
            .academiaId(academiaId)
            .usuarioId(aid)
            .build()).orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"))
        );
      }
    }
  }

  public void removeProfesorFromCurso(Curso curso, String profesorId) {
    curso.getProfesores().removeIf(p -> p.getUsuario().getId().equals(profesorId));
  }

  public List<Profesor> findProfesoresDisponibles(Curso curso) {
    return curso.getAcademia().getProfesores().stream()
      .filter(profesor -> curso.getProfesores().stream().noneMatch(p -> p.getId().equals(profesor.getId())))
      .toList();
  }

  public List<Alumno> findAlumnosDisponibles(Curso curso) {
    return curso.getAcademia().getAlumnos().stream()
      .filter(alumno -> curso.getAlumnos().stream().noneMatch(a -> a.getId().equals(alumno.getId())))
      .toList();
  }

  public Curso editarCurso(String cursoId, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
    Curso curso = findByIdOrThrow(cursoId);
    curso.setNombre(nombre);
    curso.setDescripcion(descripcion);
    curso.setFechaInicio(fechaInicio);
    curso.setFechaFin(fechaFin);
    return save(curso);
  }
}

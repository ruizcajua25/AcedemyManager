package com.salesianostriana.dam.academymanager.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.repositories.CursoRepository;

@Service
public class CursoService extends BaseService<Curso, String, CursoRepository> {
  public void eliminarAlumno (Curso curso, String alumnoId) {
    Set<Alumno> alumnos = new HashSet<>(curso.getAlumnos());
    alumnos.removeIf(alumno -> alumno.getUsuario().getId().equals(alumnoId));
    curso.setAlumnos(alumnos);
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
    Map<String, Integer> resumen = new HashMap<>();
    int alumnos = curso.getAlumnos() != null ? curso.getAlumnos().size() : 0;
    int profesores = curso.getProfesores() != null ? curso.getProfesores().size() : 0;
    resumen.put("alumnos", alumnos);
    resumen.put("profesores", profesores);
    resumen.put("total", alumnos + profesores);
    return resumen;
  }
}

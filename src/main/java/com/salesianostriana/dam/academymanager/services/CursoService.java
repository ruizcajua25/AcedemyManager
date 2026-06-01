package com.salesianostriana.dam.academymanager.services;

import java.time.LocalDate;
import java.util.HashSet;
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


}

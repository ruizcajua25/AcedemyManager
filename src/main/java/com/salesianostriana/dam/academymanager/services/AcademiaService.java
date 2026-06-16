package com.salesianostriana.dam.academymanager.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.repositories.AcademiaRepository;

@Service
public class AcademiaService extends BaseService<Academia, String, AcademiaRepository> {
  @Autowired
  private DirectorService directorService;
  @Autowired
  private AlumnoService alumnoService;
  @Autowired
  private ProfesorService profesorService;

  public HashMap<String, List<Academia>> findAllByUsuario(String usuarioId) {
    HashMap<String, List<Academia>> academias = new HashMap<>();
    directorService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuarioId)).forEach(d -> {
      academias.putIfAbsent("director", new ArrayList<>());
      academias.get("director").add(d.getAcademia());
    });
    alumnoService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuarioId)).forEach(a -> {
      academias.putIfAbsent("alumno", new ArrayList<>());
      academias.get("alumno").add(a.getAcademia());
    });
    profesorService.findAllByPredicate(u -> u.getUsuario().getId().equals(usuarioId)).forEach(p -> {
      academias.putIfAbsent("profesor", new ArrayList<>());
      academias.get("profesor").add(p.getAcademia());
    });
    return academias;
  }

  public int totalUsuariosEnAcademia(Academia academia) {
    int alumnos = academia.getAlumnos() != null ? academia.getAlumnos().size() : 0;
    int profesores = academia.getProfesores() != null ? academia.getProfesores().size() : 0;
    int directores = academia.getDirectores() != null ? academia.getDirectores().size() : 0;
    return alumnos + profesores + directores;
  }

  public Map<String, Integer> resumenUsuariosEnAcademia(Academia academia) {
    Map<String, Integer> resumen = new HashMap<>();
    int alumnos = academia.getAlumnos() != null ? academia.getAlumnos().size() : 0;
    int profesores = academia.getProfesores() != null ? academia.getProfesores().size() : 0;
    int directores = academia.getDirectores() != null ? academia.getDirectores().size() : 0;
    resumen.put("alumnos", alumnos);
    resumen.put("profesores", profesores);
    resumen.put("directores", directores);
    resumen.put("total", alumnos + profesores + directores);
    return resumen;
  }
}

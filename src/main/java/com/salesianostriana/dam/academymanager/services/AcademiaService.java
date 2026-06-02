package com.salesianostriana.dam.academymanager.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
}

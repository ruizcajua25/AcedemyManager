package com.salesianostriana.dam.academymanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.repositories.AlumnoRepository;

@Service
public class AlumnoService extends BaseService<Alumno, TipoUsuarioId, AlumnoRepository> {
  public List<Alumno> findByUsuarioId(String usuarioId) {
    return repository.findByUsuarioId(usuarioId);
  }
}
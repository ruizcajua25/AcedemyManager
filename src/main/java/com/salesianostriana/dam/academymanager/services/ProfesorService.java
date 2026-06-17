package com.salesianostriana.dam.academymanager.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.repositories.ProfesorRepository;

@Service
public class ProfesorService extends BaseService<Profesor, TipoUsuarioId, ProfesorRepository> {
  public List<Profesor> findByUsuarioId(String usuarioId) {
    return repository.findByUsuarioId(usuarioId);
  }
}
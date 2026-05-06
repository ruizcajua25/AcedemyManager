package com.salesianostriana.dam.academymanager.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Profesor;

@Repository
public class ProfesorRepository {
  // TODO: esto esta falseado
  public List<Profesor> getAllProfesores () {
    return List.of(
      new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas"),
      new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas"),
      new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas")
    );
  }   
}
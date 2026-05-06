package com.salesianostriana.dam.academymanager.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Profesor;

@Repository
public class ProfesorRepository {
  // TODO: esto esta falseado

  private List<Profesor> profesores = new ArrayList<Profesor>(List.of(
    new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas"),
    new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas"),
    new Profesor("Juan", "Ruiz", "lkasakdsf@gmail.com", "matemáticas")
  ));

  public List<Profesor> getAll () {
    return profesores; 
  }   

  public void create(Profesor profesor) {
    profesores.add(profesor);
  }

  public void delete(Profesor profesor) {
    profesores.remove(profesor);
  }
}
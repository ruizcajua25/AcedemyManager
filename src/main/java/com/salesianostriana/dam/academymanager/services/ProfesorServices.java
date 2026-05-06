package com.salesianostriana.dam.academymanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.repositories.ProfesorRepository;

@Service
public class ProfesorServices {
  @Autowired
  private ProfesorRepository repository;
  
  public List<Profesor> getAllProfesores () {
    return repository.getAll();
  }    

  public void addProfesor(Profesor profesor) {
    repository.create(profesor);
  }
}
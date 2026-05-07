package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class Academia {
  private String nombre, id;
  private List<Profesor> profesores;
  private List<Alumno> alumnos; 
}
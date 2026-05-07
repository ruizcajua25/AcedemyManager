package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class Clase {
  private String nombre, id;
  private List<Asignatura> asignaturas;
  private List<Alumno> alumnos;
}
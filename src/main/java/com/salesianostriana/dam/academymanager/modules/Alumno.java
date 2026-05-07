package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class Alumno {
  private List<Asignatura> asignaturas;
  private Academia academia;
  private String nombre, apellidos, dni;  
}
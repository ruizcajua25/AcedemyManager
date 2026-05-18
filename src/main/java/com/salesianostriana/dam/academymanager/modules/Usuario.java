package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Usuario {
  @Id @GeneratedValue
  private long id;
  private String dni;
  private String nombre;
  private String apellidos;
  private String email;    
}
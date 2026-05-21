package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
  @Id @GeneratedValue
  private String id;
  private String dni;
  private String nombre;
  private String apellidos;
  private String username;
  private String password;
  private String email;    
}
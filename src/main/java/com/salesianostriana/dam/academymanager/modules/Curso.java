package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Curso {
  @Id
  @GeneratedValue
  private String id;

  private String nombre;
  private String descripcion;

  @ManyToMany
  private List<Alumno> alumnos;

  @ManyToMany
  private List<Profesor> profesores;
}

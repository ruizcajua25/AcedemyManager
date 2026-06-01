package com.salesianostriana.dam.academymanager.modules;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne
  private Academia academia;

  @ManyToMany
  @Builder.Default
  private Set<Alumno> alumnos = Set.of();

  @ManyToMany
  @Builder.Default
  private Set<Profesor> profesores = Set.of();
}

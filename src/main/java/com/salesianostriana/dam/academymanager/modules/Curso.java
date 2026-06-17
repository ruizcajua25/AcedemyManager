package com.salesianostriana.dam.academymanager.modules;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Curso {
  @Id
  @GeneratedValue
  @EqualsAndHashCode.Include
  private String id;

  private String nombre;
  @Column(length = 500)
  private String descripcion;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;

  @ManyToOne
  @ToString.Exclude
  private Academia academia;

  @ManyToMany
  @Builder.Default
  @ToString.Exclude
  private Set<Alumno> alumnos = new HashSet<>();

  @ManyToMany
  @Builder.Default
  @ToString.Exclude
  private Set<Profesor> profesores = new HashSet<>();
}

package com.salesianostriana.dam.academymanager.modules;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "academias")
@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Academia {
  @Id 
  @GeneratedValue
  @EqualsAndHashCode.Include
  private String id;
  private String nombre;
  @Column(length = 500)
  private String descripcion;
  private String direccion;
  private String telefono;
  private String email;

  @OneToMany(mappedBy = "academia")
  @ToString.Exclude
  private Set<Alumno> alumnos;

  @OneToMany(mappedBy = "academia")
  @ToString.Exclude
  private Set<Profesor> profesores;

  @OneToMany(mappedBy = "academia")
  @ToString.Exclude
  private Set<Director> directores;

  @OneToMany(mappedBy = "academia")
  @ToString.Exclude
  private Set<Curso> cursos;
}

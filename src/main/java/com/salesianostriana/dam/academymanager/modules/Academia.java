package com.salesianostriana.dam.academymanager.modules;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "academias")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Academia {

  @Id @GeneratedValue
  private String id;
  private String nombre;

  @OneToMany(mappedBy = "academia")
  private Set<Profesor> profesores;

  @OneToMany(mappedBy = "academia")
  private Set<Alumno> alumnos;
}

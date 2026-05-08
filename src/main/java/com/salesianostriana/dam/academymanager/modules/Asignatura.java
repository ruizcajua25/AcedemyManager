package com.salesianostriana.dam.academymanager.modules;

import java.util.List;
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
@Table(name = "asignaturas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Asignatura {

  @Id @GeneratedValue
  private String id;
  private String nombre;

  @OneToMany(mappedBy = "asignatura")
  private Set<Profesor> profesores;
}

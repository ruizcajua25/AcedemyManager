package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clases")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Clase {

  @Id @GeneratedValue
  private String id;
  private String nombre;

  @ManyToMany
  @JoinTable(
    name = "clase_asignatura",
    joinColumns = @JoinColumn(name = "clase_id"),
    inverseJoinColumns = @JoinColumn(name = "asignatura_id")
  )
  private List<Asignatura> asignaturas;

  @ManyToMany
  @JoinTable(
    name = "clase_alumno",
    joinColumns = @JoinColumn(name = "clase_id"),
    inverseJoinColumns = @JoinColumn(name = "alumno_dni")
  )
  private List<Alumno> alumnos;
}

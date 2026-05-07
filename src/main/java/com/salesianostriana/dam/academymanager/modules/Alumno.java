package com.salesianostriana.dam.academymanager.modules;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alumnos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Alumno {

  @Id
  private String dni;
  private String nombre;
  private String apellidos;

  @ManyToMany
  @JoinTable(
    name = "alumno_asignatura",
    joinColumns = @JoinColumn(name = "alumno_dni"),
    inverseJoinColumns = @JoinColumn(name = "asignatura_id")
  )
  private List<Asignatura> asignaturas;

  @ManyToOne
  @JoinColumn(name = "academia_id")
  private Academia academia;
}

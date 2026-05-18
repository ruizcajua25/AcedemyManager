package com.salesianostriana.dam.academymanager.modules;

import java.util.Set;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alumnos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Alumno {
  @EmbeddedId
  private long id;
  
  @MapsId("usuarioId")
  @OneToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @MapsId("academiaId")
  @OneToOne
  @JoinColumn(name = "academia_id")
  private Academia academia;

  @ManyToMany
  @JoinTable(
    name = "alumno_asignatura",
    joinColumns = @JoinColumn(name = "alumno_dni"),
    inverseJoinColumns = @JoinColumn(name = "asignatura_id")
  )
  private Set<Asignatura> asignaturas;
}
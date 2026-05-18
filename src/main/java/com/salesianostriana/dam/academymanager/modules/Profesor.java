package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profesores")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profesor extends Usuario {
  @ManyToOne
  @JoinColumn(name = "asignatura_id")
  private Asignatura asignatura;

  @ManyToOne
  @JoinColumn(name = "academia_id")
  private Academia academia;
}

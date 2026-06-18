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
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String nombre;

  @Column(length = 500)
  @Size(min = 10, max = 500, message = "La descripcion debe tener entre 10 y 500 caracteres")
  private String descripcion;

  @NotNull(message = "La fecha de inicio es obligatoria")
  private LocalDate fechaInicio;

  @NotNull(message = "La fecha de fin es obligatoria")
  private LocalDate fechaFin;

  @Min(value = 1, message = "El cupo maximo debe ser al menos 1")
  private Integer cupoMaximo;

  @AssertTrue(message = "La fecha de fin debe ser posterior a la fecha de inicio")
  public boolean isFechasValidas() {
    if (fechaInicio == null || fechaFin == null) {
      return true;
    }
    return fechaFin.isAfter(fechaInicio);
  }

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

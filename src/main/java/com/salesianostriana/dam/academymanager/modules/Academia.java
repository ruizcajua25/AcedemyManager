package com.salesianostriana.dam.academymanager.modules;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
  private String nombre;

  @Column(length = 500)
  @Size(min = 10, max = 500, message = "La descripcion debe tener entre 10 y 500 caracteres")
  private String descripcion;

  @NotBlank(message = "La direccion es obligatoria")
  @Size(min = 5, max = 150, message = "La direccion debe tener entre 5 y 150 caracteres")
  private String direccion;

  @NotBlank(message = "El telefono es obligatorio")
  @Pattern(regexp = "^[0-9]{9}$", message = "El telefono debe tener 9 digitos")
  private String telefono;

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El email no tiene un formato valido")
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

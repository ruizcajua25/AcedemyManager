package com.salesianostriana.dam.academymanager.modules;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "academias")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Academia {

  @Id
  private String id;
  private String nombre;

  @OneToMany(mappedBy = "academia")
  private List<Profesor> profesores;

  @OneToMany(mappedBy = "academia")
  private List<Alumno> alumnos;
}

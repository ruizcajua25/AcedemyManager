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
  import lombok.ToString;

  @Entity
  @Table(name = "academias")
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  @Builder
  public class Academia {
    @Id 
    @GeneratedValue
    private String id;
    private String nombre;
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
  }

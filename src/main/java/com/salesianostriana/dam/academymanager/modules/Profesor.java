package com.salesianostriana.dam.academymanager.modules;

import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "profesores")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Profesor {
  @EmbeddedId
  @EqualsAndHashCode.Include
  private TipoUsuarioId id;

  @ManyToOne
  @MapsId("academiaId")
  @JoinColumn(name = "academia_id")
  @ToString.Exclude
  private Academia academia;

  @ManyToOne
  @MapsId("usuarioId")
  @JoinColumn(name = "usuario_id")
  @ToString.Exclude
  private Usuario usuario;

  @ManyToMany(mappedBy = "profesores")
  @ToString.Exclude
  private List<Curso> cursos;
}

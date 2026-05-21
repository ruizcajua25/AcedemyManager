package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "directores")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Director {
  @EmbeddedId
  private TipoUsuarioId id;

  @ManyToOne
  @MapsId("academiaId")
  @JoinColumn(name = "academia_id")
  private Academia academia;

  @ManyToOne
  @MapsId("usuarioId")
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}
package com.salesianostriana.dam.academymanager.modules;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class TipoUsuarioId implements Serializable {
  private String usuarioId;
  private String academiaId;
}
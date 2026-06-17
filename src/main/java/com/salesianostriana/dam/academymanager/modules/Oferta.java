package com.salesianostriana.dam.academymanager.modules;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ofertas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Oferta {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private String id;
  
  @ManyToOne
  @ToString.Exclude
  private Academia academia;
  
  private TipoOferta tipoOferta;
  private String titulo;
  @Column(length = 500)
  private String descripcion;
  @ManyToMany
  @Builder.Default
  @ToString.Exclude
  private List<Usuario> candidatos = new ArrayList<>();

  @ManyToOne
  @ToString.Exclude
  private Curso curso;

  @Builder.Default
  private boolean activa = true;
}
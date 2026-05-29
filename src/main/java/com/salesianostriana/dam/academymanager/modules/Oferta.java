package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ofertas")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Oferta {
  @Id @GeneratedValue
  private String id;
  
  @ManyToOne
  private Academia academia;
  
  private TipoOferta tipoOferta;
  private String titulo;
  private String descripcion;
}
package com.salesianostriana.dam.academymanager.modules;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "directores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Director extends Usuario {

}
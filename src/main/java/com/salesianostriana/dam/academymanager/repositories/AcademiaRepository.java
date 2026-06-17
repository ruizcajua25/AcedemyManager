package com.salesianostriana.dam.academymanager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Academia;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, String> {
  List<Academia> findByDirectores_UsuarioId(String usuarioId);
  Optional<Academia> findByIdAndDirectores_UsuarioId(String id, String usuarioId);
  boolean existsByIdAndDirectores_UsuarioId(String id, String usuarioId);
}
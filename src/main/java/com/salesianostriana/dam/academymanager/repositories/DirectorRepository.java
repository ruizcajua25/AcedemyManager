package com.salesianostriana.dam.academymanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;

@Repository
public interface DirectorRepository extends JpaRepository<Director, TipoUsuarioId> {
  @Query("SELECT d FROM Director d WHERE d.usuario.id = :usuarioId AND d.academia.id = :academiaId")
  Director findByUsuarioIdAndAcademiaId(String usuarioId, String academiaId);
}
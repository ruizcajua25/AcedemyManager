package com.salesianostriana.dam.academymanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Profesor;

import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, TipoUsuarioId> {
  @Query("SELECT p FROM Profesor p WHERE p.usuario.id = :usuarioId AND p.academia.id = :academiaId")
  Profesor findByUsuarioIdAndAcademiaId(String usuarioId, String academiaId);
}
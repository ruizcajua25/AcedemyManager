package com.salesianostriana.dam.academymanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, TipoUsuarioId> {
  @Query("SELECT a FROM Alumno a WHERE a.usuario.id = :usuarioId AND a.academia.id = :academiaId")
  Alumno findByUsuarioIdAndAcademiaId(String usuarioId, String academiaId);
  List<Alumno> findByUsuarioId(String usuarioId);
}
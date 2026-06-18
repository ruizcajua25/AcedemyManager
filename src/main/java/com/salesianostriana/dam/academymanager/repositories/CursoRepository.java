package com.salesianostriana.dam.academymanager.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, String> {
  List<Curso> findByAcademiaId(String academiaId);
  List<Curso> findByAcademiaIdAndFechaInicioBeforeAndFechaFinAfter(String academiaId, LocalDate today1, LocalDate today2);
  List<Curso> findByAcademiaIdOrderByNombre(String academiaId);
  List<Curso> findByAcademiaIdAndProfesoresIsEmpty(String academiaId);
  List<Curso> findByProfesoresIsEmpty();
  List<Curso> findByAcademiaIdAndAlumnosUsuarioId(String academiaId, String usuarioId);
  List<Curso> findByAcademiaIdAndProfesoresUsuarioId(String academiaId, String usuarioId);
}

package com.salesianostriana.dam.academymanager.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, String> {
  List<Oferta> findByAcademia(Academia academia);
  List<Oferta> findByAcademiaId(String academiaId);
  List<Oferta> findByAcademiaIdAndActivaTrue(String academiaId);
  List<Oferta> findByAcademiaIdAndActivaTrueAndCurso_FechaInicioAfter(String academiaId, LocalDate today);
  List<Oferta> findByAcademiaIdAndActivaTrueAndCursoIsNull(String academiaId);
  List<Oferta> findByCandidatosId(String id);
  List<Oferta> findByCandidatosIdAndActivaTrue(String usuarioId);
  boolean existsByIdAndAcademiaDirectoresUsuarioId(String id, String usuarioId);
  boolean existsByIdAndAcademia_Directores_UsuarioId(String ofertaId, String usuarioId);
}
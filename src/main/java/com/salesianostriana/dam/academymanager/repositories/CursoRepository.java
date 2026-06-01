package com.salesianostriana.dam.academymanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.academymanager.modules.Curso;

public interface CursoRepository extends JpaRepository<Curso, String> {
  
}

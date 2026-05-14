package com.salesianostriana.dam.academymanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Alumno;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
}
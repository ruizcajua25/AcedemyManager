package com.salesianostriana.dam.academymanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Academia;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, String> {
  
}
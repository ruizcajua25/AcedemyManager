package com.salesianostriana.dam.academymanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Oferta;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, String> {
  public List<Oferta> findByAcademia(Academia academia);
}
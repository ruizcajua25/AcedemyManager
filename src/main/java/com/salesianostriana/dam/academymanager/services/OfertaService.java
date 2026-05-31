package com.salesianostriana.dam.academymanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.repositories.OfertaRepository;

@Service
public class OfertaService extends BaseService<Oferta, String, OfertaRepository> {
  @Autowired
  private OfertaRepository ofertaService;
  
  public List<Oferta> findByAcademia (Academia academia) {
    return ofertaService.findByAcademia(academia); 
  }

  public void aplicar(Oferta oferta, Usuario usuario) {
    oferta.getCandidatos().add(usuario);
  }
}

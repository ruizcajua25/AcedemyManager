package com.salesianostriana.dam.academymanager.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.modules.TipoOferta;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.repositories.AlumnoRepository;
import com.salesianostriana.dam.academymanager.repositories.DirectorRepository;
import com.salesianostriana.dam.academymanager.repositories.OfertaRepository;
import com.salesianostriana.dam.academymanager.repositories.ProfesorRepository;

@Service
public class OfertaService extends BaseService<Oferta, String, OfertaRepository> {
  @Autowired
  private OfertaRepository ofertaRepository;

  @Autowired 
  private DirectorRepository directorRepository;
  @Autowired
  private AlumnoRepository alumnoRepository;
  @Autowired
  private ProfesorRepository profesorRepository;
  
  public List<Oferta> findByAcademia (Academia academia) {
    return ofertaRepository.findByAcademia(academia); 
  }

  public void aplicar(Oferta oferta, Usuario usuario) {
    oferta.getCandidatos().add(usuario);
  }

  public List<Oferta> findByUsuarioId(String id) {
    return ofertaRepository.findByCandidatosId(id);
  }

  public void aceptarCandidato(Oferta oferta, String candidatoId) {
    Usuario candidato = oferta.getCandidatos().stream()
      .filter(c -> c.getId().equals(candidatoId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

    oferta.getCandidatos().remove(candidato);

    TipoUsuarioId nuevoId = TipoUsuarioId.builder().academiaId(oferta.getAcademia().getId()).usuarioId(candidatoId).build();
    
    if (oferta.getTipoOferta() == TipoOferta.profesor) {
      oferta.getAcademia().getProfesores().add(
        profesorRepository.save(Profesor.builder()
        .id(nuevoId)
        .academia(oferta.getAcademia())
        .usuario(candidato)
        .build())
      );
    } else if (oferta.getTipoOferta() == TipoOferta.direccion) {
      oferta.getAcademia().getDirectores().add(
        directorRepository.save(Director.builder()
        .id(nuevoId)
        .academia(oferta.getAcademia())
        .usuario(candidato)
        .build())
      );
    } else if (oferta.getTipoOferta() == TipoOferta.alumno) {
      oferta.getAcademia().getAlumnos().add(
        alumnoRepository.save(Alumno.builder()
        .id(nuevoId)
        .academia(oferta.getAcademia())
        .usuario(candidato)
        .build())
      );
    }
  }

  public void rechazarCandidato(Oferta oferta, String candidatoId) {
    Usuario candidato = oferta.getCandidatos().stream()
      .filter(c -> c.getId().equals(candidatoId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("Candidato no encontrado"));

    oferta.getCandidatos().remove(candidato);
  }

  public boolean esOfertaAplicable (Oferta oferta) {
    if(oferta.getCurso() == null || oferta.getCurso().getFechaInicio() == null) {
      return oferta.isActiva();
    }

    return oferta.isActiva() && oferta.getCurso().getFechaInicio().isAfter(LocalDate.now());
  }
}

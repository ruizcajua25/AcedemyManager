package com.salesianostriana.dam.academymanager.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException;
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
  @Autowired
  private CursoService cursoService;
  
  public List<Oferta> findByAcademia (Academia academia) {
    return ofertaRepository.findByAcademia(academia); 
  }

  public void aplicar(Oferta oferta, Usuario usuario) {
    oferta.getCandidatos().add(usuario);
  }

  public List<Oferta> findByUsuarioId(String id) {
    return ofertaRepository.findByCandidatosIdAndActivaTrue(id);
  }

  public void aceptarCandidato(Oferta oferta, String candidatoId) {
    Usuario candidato = oferta.getCandidatos().stream()
      .filter(c -> c.getId().equals(candidatoId))
      .findFirst()
      .orElseThrow(() -> new ObjetoNoEncontradoException("Candidato no encontrado"));

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
      .orElseThrow(() -> new ObjetoNoEncontradoException("Candidato no encontrado"));

    oferta.getCandidatos().remove(candidato);
  }

  public boolean esOfertaAplicable (Oferta oferta) {
    if(oferta.getCurso() == null || oferta.getCurso().getFechaInicio() == null) {
      return oferta.isActiva();
    }

    return oferta.isActiva() && oferta.getCurso().getFechaInicio().isAfter(LocalDate.now());
  }

  public int totalCandidatosEnOferta(Oferta oferta) {
    return oferta.getCandidatos() != null ? oferta.getCandidatos().size() : 0;
  }

  public Map<String, Integer> resumenCandidatosEnOferta(Oferta oferta) {
    Map<String, Integer> resumen = new HashMap<>();
    int candidatos = oferta.getCandidatos() != null ? oferta.getCandidatos().size() : 0;
    resumen.put("candidatos", candidatos);
    resumen.put("total", candidatos);
    return resumen;
  }

  public List<Oferta> findOfertasActivasByAcademia(String academiaId) {
    LocalDate now = LocalDate.now();
    List<Oferta> ofertasConCurso = ofertaRepository.findByAcademiaIdAndActivaTrueAndCurso_FechaInicioAfter(academiaId, now);
    List<Oferta> ofertasSinCurso = ofertaRepository.findByAcademiaIdAndActivaTrueAndCursoIsNull(academiaId);
    List<Oferta> combined = new java.util.ArrayList<>(ofertasConCurso);
    combined.addAll(ofertasSinCurso);
    return combined;
  }

  public List<Oferta> findAllActivas() {
    return findAll().stream()
      .filter(this::esOfertaAplicable)
      .toList();
  }

  public boolean esDirectorDeOferta(String ofertaId, String usuarioId) {
    return ofertaRepository.existsByIdAndAcademia_Directores_UsuarioId(ofertaId, usuarioId);
  }

  public Oferta findByIdOrThrow(String ofertaId) {
    return findById(ofertaId).orElseThrow(() -> new ObjetoNoEncontradoException("Oferta no encontrada"));
  }

  public void aplicarOferta(String ofertaId, String usuarioId) {
    Oferta oferta = findByIdOrThrow(ofertaId);
    if (!esOfertaAplicable(oferta)) {
      throw new com.salesianostriana.dam.academymanager.exceptions.AccionNoPermitidaException("No puedes aplicar a esta oferta");
    }
    aplicar(oferta, Usuario.builder().id(usuarioId).build());
    save(oferta);
  }

  public void aceptarCandidato(String ofertaId, String candidatoId) {
    Oferta oferta = findByIdOrThrow(ofertaId);
    aceptarCandidato(oferta, candidatoId);
    save(oferta);
  }

  public void rechazarCandidato(String ofertaId, String candidatoId) {
    Oferta oferta = findByIdOrThrow(ofertaId);
    rechazarCandidato(oferta, candidatoId);
    save(oferta);
  }

  public List<Oferta> findOfertasConCandidatosByAcademia(String academiaId) {
    return findOfertasActivasByAcademia(academiaId).stream()
      .filter(o -> o.getCandidatos() != null && !o.getCandidatos().isEmpty())
      .toList();
  }

  public List<Oferta> findOfertasSinCandidatosByAcademia(String academiaId) {
    return findOfertasActivasByAcademia(academiaId).stream()
      .filter(o -> o.getCandidatos() == null || o.getCandidatos().isEmpty())
      .toList();
  }

  public int totalCandidatosByAcademia(String academiaId) {
    return findOfertasActivasByAcademia(academiaId).stream()
      .mapToInt(o -> o.getCandidatos() != null ? o.getCandidatos().size() : 0)
      .sum();
  }

  public Oferta editarOferta(String ofertaId, String titulo, String descripcion, TipoOferta tipoOferta, String cursoId) {
    Oferta oferta = findByIdOrThrow(ofertaId);
    oferta.setTitulo(titulo);
    oferta.setDescripcion(descripcion);
    oferta.setTipoOferta(tipoOferta);
    if (cursoId != null && !cursoId.isEmpty()) {
      Curso curso = cursoService.findByIdOrThrow(cursoId);
      oferta.setCurso(curso);
    } else {
      oferta.setCurso(null);
    }
    return save(oferta);
  }
}

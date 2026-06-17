package com.salesianostriana.dam.academymanager.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.repositories.AcademiaRepository;
import com.salesianostriana.dam.academymanager.repositories.DirectorRepository;

@Service
public class AcademiaService extends BaseService<Academia, String, AcademiaRepository> {
  @Autowired
  private DirectorService directorService;
  @Autowired
  private AlumnoService alumnoService;
  @Autowired
  private ProfesorService profesorService;
  @Autowired
  private DirectorRepository directorRepository;
  @Autowired
  private CursoService cursoService;
  @Autowired
  private OfertaService ofertaService;

  public HashMap<String, List<Academia>> findAllByUsuario(String usuarioId) {
    HashMap<String, List<Academia>> academias = new HashMap<>();
    directorRepository.findByUsuarioId(usuarioId).forEach(d -> {
      academias.putIfAbsent("director", new ArrayList<>());
      academias.get("director").add(d.getAcademia());
    });
    alumnoService.findByUsuarioId(usuarioId).forEach(a -> {
      academias.putIfAbsent("alumno", new ArrayList<>());
      academias.get("alumno").add(a.getAcademia());
    });
    profesorService.findByUsuarioId(usuarioId).forEach(p -> {
      academias.putIfAbsent("profesor", new ArrayList<>());
      academias.get("profesor").add(p.getAcademia());
    });
    return academias;
  }

  public int totalUsuariosEnAcademia(Academia academia) {
    int alumnos = academia.getAlumnos() != null ? academia.getAlumnos().size() : 0;
    int profesores = academia.getProfesores() != null ? academia.getProfesores().size() : 0;
    int directores = academia.getDirectores() != null ? academia.getDirectores().size() : 0;
    return alumnos + profesores + directores;
  }

  public Map<String, Integer> resumenUsuariosEnAcademia(Academia academia) {
    Map<String, Integer> resumen = new HashMap<>();
    int alumnos = academia.getAlumnos() != null ? academia.getAlumnos().size() : 0;
    int profesores = academia.getProfesores() != null ? academia.getProfesores().size() : 0;
    int directores = academia.getDirectores() != null ? academia.getDirectores().size() : 0;
    resumen.put("alumnos", alumnos);
    resumen.put("profesores", profesores);
    resumen.put("directores", directores);
    resumen.put("total", alumnos + profesores + directores);
    return resumen;
  }

  public boolean esDirector(String academiaId, String usuarioId) {
    return repository.existsByIdAndDirectores_UsuarioId(academiaId, usuarioId);
  }

  @Transactional
  public void crearAcademiaConDirector(Academia academia, Usuario usuario) {
    save(academia);
    TipoUsuarioId id = TipoUsuarioId.builder()
      .academiaId(academia.getId())
      .usuarioId(usuario.getId())
      .build();
    Director director = Director.builder()
      .id(id)
      .academia(academia)
      .usuario(usuario)
      .build();
    directorService.save(director);
  }

  public List<Academia> findMisAcademiasComoDirector(String usuarioId) {
    return repository.findByDirectores_UsuarioId(usuarioId);
  }

  public Academia findByIdAndDirectorOrThrow(String academiaId, String usuarioId) {
    return repository.findByIdAndDirectores_UsuarioId(academiaId, usuarioId)
      .orElseThrow(() -> new com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException("No se encontro la academia con ID: " + academiaId));
  }

  public double calcularValoracion(Academia academia) {
    double totalUsuarios = totalUsuariosEnAcademia(academia);
    double alumnosUnicosActivos = cursoService.contarAlumnosUnicosEnCursosActivos(academia);
    double ratioAlumnosPorProfesor = cursoService.calcularRatioAlumnosPorProfesorEnCursosActivos(academia);
    double duracionMediaDias = cursoService.calcularDuracionMediaCursosActivos(academia);
    double tasaRespuesta = ofertaService.calcularTasaDeRespuestaOfertas(academia);
    double interesMedio = ofertaService.calcularInteresMedioPorOferta(academia);

    double ratioScore = ratioAlumnosPorProfesor > 0
      ? Math.min(1.5, 30.0 / ratioAlumnosPorProfesor / 6.0)
      : 0.0;

    double valoracion = Math.min(1.0, totalUsuarios * 0.05)
      + Math.min(1.0, alumnosUnicosActivos * 0.05)
      + ratioScore
      + Math.min(0.5, duracionMediaDias / 30.0 * 0.1)
      + Math.min(1.0, tasaRespuesta)
      + Math.min(1.0, interesMedio * 0.1);

    return Math.round(Math.min(5.0, valoracion) * 100.0) / 100.0;
  }

  public Map<String, Double> calcularValoraciones(List<Academia> academias) {
    Map<String, Double> valoraciones = new HashMap<>();
    for (Academia academia : academias) {
      valoraciones.put(academia.getId(), calcularValoracion(academia));
    }
    return valoraciones;
  }

  public List<Academia> findAllOrdenadasPorValoracion() {
    return findAll().stream()
      .sorted(Comparator.comparingDouble(this::calcularValoracion).reversed())
      .toList();
  }

  public Integer calcularPosicionTop(Academia academia) {
    List<Academia> academiasOrdenadas = findAll().stream()
      .sorted(Comparator.comparingDouble(this::calcularValoracion).reversed())
      .toList();

    for (int i = 0; i < Math.min(3, academiasOrdenadas.size()); i++) {
      if (academiasOrdenadas.get(i).getId().equals(academia.getId())) {
        return i + 1;
      }
    }
    return null;
  }

  public Map<String, Integer> calcularTopPosiciones() {
    List<Academia> academiasOrdenadas = findAll().stream()
      .sorted(Comparator.comparingDouble(this::calcularValoracion).reversed())
      .toList();

    Map<String, Integer> topPosiciones = new HashMap<>();
    for (int i = 0; i < Math.min(3, academiasOrdenadas.size()); i++) {
      topPosiciones.put(academiasOrdenadas.get(i).getId(), i + 1);
    }
    return topPosiciones;
  }
}

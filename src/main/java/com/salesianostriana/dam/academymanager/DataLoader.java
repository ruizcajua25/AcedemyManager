package com.salesianostriana.dam.academymanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Alumno;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Director;
import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.modules.TipoOferta;
import com.salesianostriana.dam.academymanager.modules.TipoUsuarioId;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.AlumnoService;
import com.salesianostriana.dam.academymanager.services.CursoService;
import com.salesianostriana.dam.academymanager.services.DirectorService;
import com.salesianostriana.dam.academymanager.services.OfertaService;
import com.salesianostriana.dam.academymanager.services.ProfesorService;
import com.salesianostriana.dam.academymanager.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

  private final UsuarioService usuarioService;
  private final AcademiaService academiaService;
  private final DirectorService directorService;
  private final AlumnoService alumnoService;
  private final ProfesorService profesorService;
  private final PasswordEncoder passwordEncoder;
  private final OfertaService ofertaService;
  private final CursoService cursoService;

  @Override
  @Transactional
  public void run(String... args) {
    if (!usuarioService.findAll().isEmpty()) {
      return;
    }

    // Usuarios genericos
    Usuario admin = usuarioService.save(Usuario.builder()
      .dni("00000000A")
      .nombre("Administrador")
      .apellidos("Sistema")
      .username("admin")
      .password(passwordEncoder.encode("admin"))
      .email("admin@academymanager.com")
      .role("ROLE_ADMIN")
      .build());

    Usuario usuarioGenerico = usuarioService.save(Usuario.builder()
      .dni("00000001B")
      .nombre("Usuario")
      .apellidos("Generico")
      .username("user")
      .password(passwordEncoder.encode("user"))
      .email("usuario@academymanager.com")
      .role("ROLE_USER")
      .build());

    // Usuarios directores
    Usuario laura = usuario("11111111A", "Laura", "Garcia Morales", "directora", "directora@academymanager.com");
    Usuario carlos = usuario("11111112B", "Carlos", "Fernandez Lopez", "director1", "carlos@academymanager.com");
    Usuario ana = usuario("11111113C", "Ana", "Rodriguez Perez", "directora2", "ana@academymanager.com");
    Usuario pedro = usuario("11111114D", "Pedro", "Martinez Gomez", "director3", "pedro@academymanager.com");
    Usuario elena = usuario("11111115E", "Elena", "Sanchez Ruiz", "directora4", "elena@academymanager.com");
    Usuario david = usuario("11111116F", "David", "Jimenez Torres", "director5", "david@academymanager.com");
    Usuario maria = usuario("11111117G", "Maria", "Alvarez Moreno", "directora6", "maria@academymanager.com");
    Usuario javier = usuario("11111118H", "Javier", "Romero Diaz", "director7", "javier@academymanager.com");

    // Usuarios alumnos
    Usuario mario = usuario("22222222B", "Mario", "Lopez Martin", "alumno", "alumno@academymanager.com");
    Usuario lucia = usuario("22222223C", "Lucia", "Fernandez Gomez", "alumno2", "alumno2@academymanager.com");
    Usuario pablo = usuario("22222224D", "Pablo", "Garcia Sanchez", "alumno3", "alumno3@academymanager.com");
    Usuario sofia = usuario("22222225E", "Sofia", "Martin Jimenez", "alumno4", "alumno4@academymanager.com");
    Usuario hugo = usuario("22222226F", "Hugo", "Rodriguez Alvarez", "alumno5", "alumno5@academymanager.com");
    Usuario emma = usuario("22222227G", "Emma", "Perez Romero", "alumno6", "alumno6@academymanager.com");
    Usuario daniel = usuario("22222228H", "Daniel", "Torres Moreno", "alumno7", "alumno7@academymanager.com");
    Usuario valeria = usuario("22222229I", "Valeria", "Vazquez Herrera", "alumno8", "alumno8@academymanager.com");
    Usuario alejandro = usuario("22222230J", "Alejandro", "Castro Ortega", "alumno9", "alumno9@academymanager.com");
    Usuario martina = usuario("22222231K", "Martina", "Navarro Delgado", "alumno10", "alumno10@academymanager.com");

    // Usuarios profesores
    Usuario carmen = usuario("33333333C", "Carmen", "Sanchez Ruiz", "profesora", "profesora@academymanager.com");
    Usuario antonio = usuario("33333334D", "Antonio", "Lopez Garcia", "profesor1", "antonio@academymanager.com");
    Usuario isabel = usuario("33333335E", "Isabel", "Martinez Fernandez", "profesora2", "isabel@academymanager.com");
    Usuario francisco = usuario("33333336F", "Francisco", "Perez Gomez", "profesor3", "francisco@academymanager.com");
    Usuario cristina = usuario("33333337G", "Cristina", "Jimenez Sanchez", "profesora4", "cristina@academymanager.com");
    Usuario sergio = usuario("33333338H", "Sergio", "Ruiz Martin", "profesor5", "sergio@academymanager.com");
    Usuario nuria = usuario("33333339I", "Nuria", "Diaz Alvarez", "profesora6", "nuria@academymanager.com");
    Usuario andres = usuario("33333340J", "Andres", "Moreno Torres", "profesor7", "andres@academymanager.com");

    // Academias
    Academia triana = academia("Academia Triana", "Calle Pureza 12, Sevilla", "954123456", "info@academiatriana.com");
    Academia nervion = academia("Academia Nervion", "Calle Luis Montoto 85, Sevilla", "954987654", "info@academianervion.com");
    Academia macarena = academia("Academia Macarena", "Plaza de la Macarena 5, Sevilla", "954112233", "contacto@academiamacarena.com");
    Academia remedios = academia("Academia Los Remedios", "Av. Republica Argentina 20, Sevilla", "954445566", "hola@academialosremedios.com");
    Academia centro = academia("Academia Sevilla Centro", "Calle Sierpes 44, Sevilla", "954778899", "centro@academiasevilla.com");
    Academia alameda = academia("Academia Alameda", "Calle Trajano 8, Sevilla", "954332211", "info@academiaalameda.com");
    Academia betis = academia("Academia Betis", "Av. de la Palmera 15, Sevilla", "954665544", "contacto@academiabetis.com");
    Academia heliopolis = academia("Academia Heliopolis", "Calle Doctor Maranon 3, Sevilla", "954009988", "info@academiaheliopolis.com");

    // Directores de cada academia
    director(laura, triana);
    director(admin, triana);
    director(carlos, nervion);
    director(admin, nervion);
    director(ana, macarena);
    director(pedro, remedios);
    director(elena, centro);
    director(david, alameda);
    director(maria, betis);
    director(javier, heliopolis);

    // Alumnos y profesores por academia
    alumno(usuarioGenerico, triana);
    alumno(usuarioGenerico, nervion);

    List<Usuario> alumnosTriana = List.of(mario, lucia, pablo, usuarioGenerico);
    List<Usuario> profesoresTriana = List.of(carmen, antonio);
    alumnosTriana.forEach(u -> alumno(u, triana));
    profesoresTriana.forEach(u -> profesor(u, triana));

    List<Usuario> alumnosNervion = List.of(sofia, hugo);
    List<Usuario> profesoresNervion = List.of(isabel);
    alumnosNervion.forEach(u -> alumno(u, nervion));
    profesoresNervion.forEach(u -> profesor(u, nervion));

    List<Usuario> alumnosMacarena = List.of(emma, daniel, valeria);
    List<Usuario> profesoresMacarena = List.of(francisco, cristina);
    alumnosMacarena.forEach(u -> alumno(u, macarena));
    profesoresMacarena.forEach(u -> profesor(u, macarena));

    List<Usuario> alumnosRemedios = List.of(alejandro, martina);
    List<Usuario> profesoresRemedios = List.of(sergio);
    alumnosRemedios.forEach(u -> alumno(u, remedios));
    profesoresRemedios.forEach(u -> profesor(u, remedios));

    List<Usuario> alumnosCentro = List.of(lucia, hugo);
    List<Usuario> profesoresCentro = List.of(nuria);
    alumnosCentro.forEach(u -> alumno(u, centro));
    profesoresCentro.forEach(u -> profesor(u, centro));

    List<Usuario> alumnosAlameda = List.of(pablo, sofia, emma);
    List<Usuario> profesoresAlameda = List.of(andres);
    alumnosAlameda.forEach(u -> alumno(u, alameda));
    profesoresAlameda.forEach(u -> profesor(u, alameda));

    List<Usuario> alumnosBetis = List.of(daniel, valeria);
    List<Usuario> profesoresBetis = List.of(carmen);
    alumnosBetis.forEach(u -> alumno(u, betis));
    profesoresBetis.forEach(u -> profesor(u, betis));

    List<Usuario> alumnosHeliopolis = List.of(alejandro, martina, mario);
    List<Usuario> profesoresHeliopolis = List.of(antonio, isabel);
    alumnosHeliopolis.forEach(u -> alumno(u, heliopolis));
    profesoresHeliopolis.forEach(u -> profesor(u, heliopolis));

    // Cursos del administrador (Triana y Nervion): algunos empezados, otros no, y uno con cupo completo
    Curso informaticaTriana = curso("Informatica Basica", "Fundamentos de informatica y programacion para principiantes.", triana, 20, LocalDate.of(2026, 3, 1), LocalDate.of(2026, 6, 30));
    Curso programacionTriana = curso("Programacion Java", "Curso avanzado de programacion orientada a objetos con Java.", triana, 15, LocalDate.of(2026, 9, 1), LocalDate.of(2026, 12, 31));
    Curso redesTriana = curso("Redes y Sistemas", "Configuracion y administracion de redes informaticas.", triana, 3, LocalDate.of(2027, 1, 15), LocalDate.of(2027, 4, 20));

    Curso inglesNervion = curso("Ingles B1", "Preparacion para el nivel B1 de ingles con practicas de conversacion.", nervion, 25, LocalDate.of(2026, 2, 1), LocalDate.of(2026, 7, 31));
    Curso inglesNervion2 = curso("Ingles B2", "Perfeccionamiento del ingles para alcanzar el nivel B2.", nervion, 20, LocalDate.of(2026, 10, 1), LocalDate.of(2027, 5, 15));

    Curso matematicasMacarena = curso("Matematicas de Bachillerato", "Refuerzo de matematicas para alumnos de bachillerato.", macarena, 22, LocalDate.of(2026, 9, 25), LocalDate.of(2027, 6, 10));
    Curso fisicaMacarena = curso("Fisica y Quimica", "Ciencias experimentales con practicas de laboratorio.", macarena, 16, LocalDate.of(2026, 10, 12), LocalDate.of(2027, 5, 25));

    Curso dibujoRemedios = curso("Dibujo Tecnico", "Tecnicas de representacion grafica y dibujo tecnico.", remedios, 14, LocalDate.of(2026, 9, 18), LocalDate.of(2027, 6, 18));

    Curso pianoCentro = curso("Piano para Principiantes", "Iniciacion al piano y lectura musical.", centro, 10, LocalDate.of(2026, 9, 22), LocalDate.of(2027, 6, 22));
    Curso guitarraCentro = curso("Guitarra Moderna", "Acordes, rasgueos y repertorio actual.", centro, 12, LocalDate.of(2026, 10, 8), LocalDate.of(2027, 5, 20));

    Curso cocinaAlameda = curso("Cocina Mediterranea", "Tecnicas y recetas de la cocina mediterranea.", alameda, 15, LocalDate.of(2026, 9, 28), LocalDate.of(2027, 6, 28));
    Curso reposteriaAlameda = curso("Reposteria Creativa", "Elaboracion de postres y decoracion con fondant.", alameda, 12, LocalDate.of(2026, 10, 15), LocalDate.of(2027, 5, 10));

    Curso futbolBetis = curso("Escuela de Futbol", "Entrenamiento tecnico y tactico de futbol.", betis, 30, LocalDate.of(2026, 9, 12), LocalDate.of(2027, 6, 12));

    Curso yogaHeliopolis = curso("Yoga y Meditacion", "Clases de yoga para todos los niveles.", heliopolis, 20, LocalDate.of(2026, 9, 16), LocalDate.of(2027, 6, 16));
    Curso fotografiaHeliopolis = curso("Fotografia Digital", "Composicion, iluminacion y edicion de fotografia.", heliopolis, 18, LocalDate.of(2026, 10, 2), LocalDate.of(2027, 5, 18));

    // Inscripciones de alumnos en cursos
    inscribir(informaticaTriana, List.of(mario, lucia, pablo, usuarioGenerico));
    inscribir(programacionTriana, List.of(mario, lucia, usuarioGenerico));
    inscribir(redesTriana, List.of(mario, lucia, pablo));

    inscribir(inglesNervion, List.of(sofia, hugo));
    inscribir(inglesNervion2, List.of(hugo));

    inscribir(matematicasMacarena, List.of(emma, daniel, valeria));
    inscribir(fisicaMacarena, List.of(daniel, valeria));

    inscribir(dibujoRemedios, List.of(alejandro, martina));

    inscribir(pianoCentro, List.of(lucia, hugo));
    inscribir(guitarraCentro, List.of(hugo));

    inscribir(cocinaAlameda, List.of(pablo, sofia, emma));
    inscribir(reposteriaAlameda, List.of(emma));

    inscribir(futbolBetis, List.of(daniel, valeria));

    inscribir(yogaHeliopolis, List.of(alejandro, martina, mario));
    inscribir(fotografiaHeliopolis, List.of(martina, mario));

    // Asignar profesores a cursos
    asignarProfesores(informaticaTriana, List.of(carmen));
    asignarProfesores(programacionTriana, List.of(antonio));
    asignarProfesores(redesTriana, List.of(carmen));

    asignarProfesores(inglesNervion, List.of(isabel));
    asignarProfesores(inglesNervion2, List.of(isabel));

    asignarProfesores(matematicasMacarena, List.of(francisco));
    asignarProfesores(fisicaMacarena, List.of(cristina));

    asignarProfesores(dibujoRemedios, List.of(sergio));

    asignarProfesores(pianoCentro, List.of(nuria));
    asignarProfesores(guitarraCentro, List.of(nuria));

    asignarProfesores(cocinaAlameda, List.of(andres));
    asignarProfesores(reposteriaAlameda, List.of(andres));

    asignarProfesores(futbolBetis, List.of(carmen));

    asignarProfesores(yogaHeliopolis, List.of(antonio));
    asignarProfesores(fotografiaHeliopolis, List.of(isabel));

    // Ofertas
    oferta("Profesor de Programacion Java", "Buscamos un profesor para impartir clases de programacion Java en el curso avanzado.", triana, TipoOferta.profesor, programacionTriana, List.of(sofia, hugo));
    oferta("Profesor de Ingles B1", "Se necesita profesor nativo o con certificacion C1 para clases de ingles.", nervion, TipoOferta.profesor, inglesNervion, List.of(emma));
    oferta("Monitor de Escuela de Futbol", "Monitor para entrenamiento tecnico y tactico de futbol.", betis, TipoOferta.profesor, futbolBetis, List.of(pablo));
    oferta("Profesor de Matematicas", "Docente para refuerzo de matematicas de bachillerato.", macarena, TipoOferta.profesor, matematicasMacarena, List.of(lucia));
    oferta("Profesor de Fotografia Digital", "Especialista en composicion, iluminacion y edicion de fotografia.", heliopolis, TipoOferta.profesor, fotografiaHeliopolis, List.of(sofia));
    oferta("Tecnico de Sonido", "Tecnico para clases de musica y eventos del centro.", centro, TipoOferta.profesor, null, List.of(usuarioGenerico));

    oferta("Plaza de alumno en Reposteria Creativa", "Oferta de plaza para cursar el taller de elaboracion de postres y decoracion con fondant.", alameda, TipoOferta.alumno, reposteriaAlameda, List.of(emma));
    oferta("Plaza de alumno en Dibujo Tecnico", "Oferta de plaza para cursar el taller de representacion grafica y dibujo tecnico.", remedios, TipoOferta.alumno, dibujoRemedios, List.of(daniel));
    oferta("Plaza de alumno en Cocina Mediterranea", "Oferta de plaza para cursar el curso de tecnicas y recetas de la cocina mediterranea.", alameda, TipoOferta.alumno, cocinaAlameda, List.of(valeria));

    oferta("Director de Marketing", "Buscamos un director de marketing para gestionar la comunicacion del centro.", centro, TipoOferta.direccion, null, List.of(daniel));
    oferta("Director de Expansion", "Director para liderar la apertura de nuevos centros en la provincia.", remedios, TipoOferta.direccion, null, List.of(alejandro));
  }

  private Usuario usuario(String dni, String nombre, String apellidos, String username, String email) {
    return usuarioService.save(Usuario.builder()
      .dni(dni)
      .nombre(nombre)
      .apellidos(apellidos)
      .username(username)
      .password(passwordEncoder.encode("1234"))
      .email(email)
      .role("ROLE_USER")
      .build());
  }

  private Academia academia(String nombre, String direccion, String telefono, String email) {
    return academiaService.save(Academia.builder()
      .nombre(nombre)
      .direccion(direccion)
      .telefono(telefono)
      .email(email)
      .build());
  }

  private Director director(Usuario usuario, Academia academia) {
    return directorService.save(Director.builder()
      .id(tipoUsuarioId(usuario, academia))
      .usuario(usuario)
      .academia(academia)
      .build());
  }

  private Alumno alumno(Usuario usuario, Academia academia) {
    return alumnoService.save(Alumno.builder()
      .id(tipoUsuarioId(usuario, academia))
      .usuario(usuario)
      .academia(academia)
      .build());
  }

  private Profesor profesor(Usuario usuario, Academia academia) {
    return profesorService.save(Profesor.builder()
      .id(tipoUsuarioId(usuario, academia))
      .usuario(usuario)
      .academia(academia)
      .build());
  }

  private Curso curso(String nombre, String descripcion, Academia academia, int cupo, LocalDate inicio, LocalDate fin) {
    return cursoService.save(Curso.builder()
      .nombre(nombre)
      .descripcion(descripcion)
      .academia(academia)
      .cupoMaximo(cupo)
      .fechaInicio(inicio)
      .fechaFin(fin)
      .alumnos(new HashSet<>())
      .profesores(new HashSet<>())
      .build());
  }

  private void inscribir(Curso curso, List<Usuario> usuarios) {
    Curso cursoActualizado = cursoService.findByIdOrThrow(curso.getId());
    for (Usuario usuario : usuarios) {
      Alumno alumno = alumnoService.findById(tipoUsuarioId(usuario, curso.getAcademia())).orElse(null);
      if (alumno != null && !cursoActualizado.getAlumnos().contains(alumno)) {
        cursoActualizado.getAlumnos().add(alumno);
      }
    }
    cursoService.save(cursoActualizado);
  }

  private void asignarProfesores(Curso curso, List<Usuario> usuarios) {
    Curso cursoActualizado = cursoService.findByIdOrThrow(curso.getId());
    for (Usuario usuario : usuarios) {
      Profesor profesor = profesorService.findById(tipoUsuarioId(usuario, curso.getAcademia())).orElse(null);
      if (profesor != null && !cursoActualizado.getProfesores().contains(profesor)) {
        cursoActualizado.getProfesores().add(profesor);
      }
    }
    cursoService.save(cursoActualizado);
  }

  private void oferta(String titulo, String descripcion, Academia academia, TipoOferta tipo, Curso curso, List<Usuario> candidatos) {
    ofertaService.save(Oferta.builder()
      .academia(academia)
      .tipoOferta(tipo)
      .titulo(titulo)
      .descripcion(descripcion)
      .curso(curso)
      .candidatos(new ArrayList<>(candidatos))
      .build());
  }

  private TipoUsuarioId tipoUsuarioId(Usuario usuario, Academia academia) {
    return TipoUsuarioId.builder()
      .usuarioId(usuario.getId())
      .academiaId(academia.getId())
      .build();
  }
}

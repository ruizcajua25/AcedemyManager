package com.salesianostriana.dam.academymanager;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
  public void run(String... args) {
    if (!usuarioService.findAll().isEmpty()) {
      return;
    }

    Usuario directorUsuario = usuarioService.save(Usuario.builder()
      .dni("11111111A")
      .nombre("Laura")
      .apellidos("Garcia Morales")
      .username("directora")
      .password(passwordEncoder.encode("1234"))
      .email("directora@academymanager.com")
      .role("ROLE_USER")
      .build());

    Usuario alumnoUsuario = usuarioService.save(Usuario.builder()
      .dni("22222222B")
      .nombre("Mario")
      .apellidos("Lopez Martin")
      .username("alumno")
      .password(passwordEncoder.encode("1234"))
      .email("alumno@academymanager.com")
      .role("ROLE_USER")
      .build());

    Usuario profesorUsuario = usuarioService.save(Usuario.builder()
      .dni("33333333C")
      .nombre("Carmen")
      .apellidos("Sanchez Ruiz")
      .username("profesora")
      .password(passwordEncoder.encode("1234"))
      .email("profesora@academymanager.com")
      .role("ROLE_USER")
      .build());

    Academia academiaTriana = academiaService.save(Academia.builder()
      .nombre("Academia Triana")
      .direccion("Calle Pureza 12, Sevilla")
      .telefono("954123456")
      .email("info@academiatriana.com")
      .build());

    cursoService.save(Curso.builder()
      .nombre("Informatica")
      .descripcion("Estudio de informatica")
      .academia(academiaTriana)
      .build()
    );

    academiaService.save(Academia.builder()
      .nombre("Academia Nervión")
      .direccion("Calle Luis Montoto 85, Sevilla")
      .telefono("954987654")
      .email("info@academianervion.com")
      .build());

    academiaService.save(Academia.builder()
      .nombre("Academia Macarena")
      .direccion("Plaza de la Macarena 5, Sevilla")
      .telefono("954112233")
      .email("contacto@academiamacarena.com")
      .build());

    academiaService.save(Academia.builder()
      .nombre("Academia Los Remedios")
      .direccion("Av. República Argentina 20, Sevilla")
      .telefono("954445566")
      .email("hola@academialosremedios.com")
      .build());

    academiaService.save(Academia.builder()
      .nombre("Academia Sevilla Centro")
      .direccion("Calle Sierpes 44, Sevilla")
      .telefono("954778899")
      .email("centro@academiasevilla.com")
      .build());

    directorService.save(Director.builder()
      .id(tipoUsuarioId(directorUsuario, academiaTriana))
      .usuario(directorUsuario)
      .academia(academiaTriana)
      .build());

    alumnoService.save(Alumno.builder()
      .id(tipoUsuarioId(alumnoUsuario, academiaTriana))
      .usuario(alumnoUsuario)
      .academia(academiaTriana)
      .build());

    profesorService.save(Profesor.builder()
      .id(tipoUsuarioId(profesorUsuario, academiaTriana))
      .usuario(profesorUsuario)
      .academia(academiaTriana)
      .build());

    Usuario candidatoUsuario = usuarioService.save(Usuario.builder()
      .dni("44444444D")
      .nombre("Lucia")
      .apellidos("Fernandez Gomez")
      .username("alumno2")
      .password(passwordEncoder.encode("1234"))
      .email("alumno2@academymanager.com")
      .build());

    ofertaService.save(Oferta.builder()
      .academia(academiaTriana)
      .tipoOferta(TipoOferta.profesor)
      .titulo("Profesor de FP de Informática")
      .descripcion("Buscamos un profesor para impartir clases de informática en nuestro centro. Se valorará experiencia previa y titulación relacionada.")
      .candidatos(List.of(candidatoUsuario))
      .build());
  }

  private TipoUsuarioId tipoUsuarioId(Usuario usuario, Academia academia) {
    return TipoUsuarioId.builder()
      .usuarioId(usuario.getId())
      .academiaId(academia.getId())
      .build();
  }
}

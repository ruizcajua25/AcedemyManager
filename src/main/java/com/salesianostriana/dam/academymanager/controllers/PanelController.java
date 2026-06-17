package com.salesianostriana.dam.academymanager.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Curso;
import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.CursoService;
import com.salesianostriana.dam.academymanager.services.OfertaService;

@Controller
@RequestMapping("/panel")
public class PanelController {

  @Autowired
  private AcademiaService academiaService;

  @Autowired
  private CursoService cursoService;

  @Autowired
  private OfertaService ofertaService;

  @GetMapping
  public String dashboard(@AuthenticationPrincipal Usuario usuario, Model model) {
    List<Academia> academiasDirector = academiaService.findMisAcademiasComoDirector(usuario.getId());

    List<Curso> cursosActivos = new ArrayList<>();
    List<Oferta> ofertasConCandidatos = new ArrayList<>();
    List<Curso> cursosSinProfesores = new ArrayList<>();
    List<Oferta> ofertasSinCandidatos = new ArrayList<>();
    int totalCandidatos = 0;

    for (Academia academia : academiasDirector) {
      String academiaId = academia.getId();

      cursosActivos.addAll(cursoService.findCursosActivosByAcademia(academiaId));
      cursosSinProfesores.addAll(cursoService.findCursosSinProfesoresByAcademia(academiaId));

      List<Oferta> ofertasAcademia = ofertaService.findOfertasActivasByAcademia(academiaId);
      ofertasConCandidatos.addAll(ofertaService.findOfertasConCandidatosByAcademia(academiaId));
      ofertasSinCandidatos.addAll(ofertaService.findOfertasSinCandidatosByAcademia(academiaId));
      totalCandidatos += ofertaService.totalCandidatosByAcademia(academiaId);
    }

    model.addAttribute("academias", academiasDirector);
    model.addAttribute("totalAcademias", academiasDirector.size());
    model.addAttribute("totalCursos", cursosActivos.size());
    model.addAttribute("totalOfertas", ofertaService.findAllActivas().size());
    model.addAttribute("totalCandidatos", totalCandidatos);
    model.addAttribute("cursosActivos", cursosActivos);
    model.addAttribute("ofertasConCandidatos", ofertasConCandidatos);
    model.addAttribute("cursosSinProfesores", cursosSinProfesores);
    model.addAttribute("ofertasSinCandidatos", ofertasSinCandidatos);
    model.addAttribute("ofertasAplicadas", ofertaService.findByUsuarioId(usuario.getId()));

    List<Map<String, String>> academiasSimple = new ArrayList<>();
    for (Academia a : academiasDirector) {
      Map<String, String> map = new HashMap<>();
      map.put("id", a.getId());
      map.put("nombre", a.getNombre());
      academiasSimple.add(map);
    }
    model.addAttribute("academiasDirector", academiasSimple);

    return "panel/dashboard";
  }

  @GetMapping("/academias")
  public String misAcademias(@AuthenticationPrincipal Usuario usuario, Model model) {
    List<Academia> academias = academiaService.findMisAcademiasComoDirector(usuario.getId());
    model.addAttribute("academias", academias);
    return "panel/academias";
  }

  @GetMapping("/cursos")
  public String misCursos(@AuthenticationPrincipal Usuario usuario, Model model) {
    List<Academia> academias = academiaService.findMisAcademiasComoDirector(usuario.getId());

    List<Curso> cursos = new ArrayList<>();
    for (Academia academia : academias) {
      cursos.addAll(cursoService.findByAcademiaOrderByNombre(academia.getId()));
    }

    model.addAttribute("cursos", cursos);
    return "panel/cursos";
  }

  @GetMapping("/ofertas")
  public String misOfertas(@AuthenticationPrincipal Usuario usuario, Model model) {
    List<Academia> academias = academiaService.findMisAcademiasComoDirector(usuario.getId());

    List<Oferta> ofertas = new ArrayList<>();
    for (Academia academia : academias) {
      ofertas.addAll(ofertaService.findByAcademia(academia));
    }

    model.addAttribute("ofertas", ofertas);
    return "panel/ofertas";
  }
}

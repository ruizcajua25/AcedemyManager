package com.salesianostriana.dam.academymanager.controllers;

import java.time.LocalDate;
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
    var academiasMap = academiaService.findAllByUsuario(usuario.getId());
    List<Academia> academiasDirector = academiasMap.getOrDefault("director", new ArrayList<>());

    List<Curso> cursosActivos = new ArrayList<>();
    List<Oferta> ofertasAbiertas = new ArrayList<>();
    List<Oferta> ofertasConCandidatos = new ArrayList<>();
    List<Curso> cursosSinProfesores = new ArrayList<>();
    List<Oferta> ofertasSinCandidatos = new ArrayList<>();
    int totalCandidatos = 0;

    for (Academia academia : academiasDirector) {
      for (Curso curso : academia.getCursos()) {
        if (cursoService.esCursoActivo(curso)) {
          cursosActivos.add(curso);
        }
        if (curso.getProfesores() == null || curso.getProfesores().isEmpty()) {
          cursosSinProfesores.add(curso);
        }
      }

      List<Oferta> ofertasAcademia = ofertaService.findByAcademia(academia);
      for (Oferta oferta : ofertasAcademia) {
        if (ofertaService.esOfertaAplicable(oferta)) {
          ofertasAbiertas.add(oferta);
          totalCandidatos += oferta.getCandidatos().size();
          if (oferta.getCandidatos() != null && !oferta.getCandidatos().isEmpty()) {
            ofertasConCandidatos.add(oferta);
          }
          if (oferta.getCandidatos() == null || oferta.getCandidatos().isEmpty()) {
            ofertasSinCandidatos.add(oferta);
          }
        }
      }
    }

    model.addAttribute("academias", academiasDirector);
    model.addAttribute("totalAcademias", academiasDirector.size());
    model.addAttribute("totalCursos", cursosActivos.size());
    model.addAttribute("totalOfertas", ofertasAbiertas.size());
    model.addAttribute("totalCandidatos", totalCandidatos);
    model.addAttribute("cursosActivos", cursosActivos);
    model.addAttribute("ofertasConCandidatos", ofertasConCandidatos);
    model.addAttribute("cursosSinProfesores", cursosSinProfesores);
    model.addAttribute("ofertasSinCandidatos", ofertasSinCandidatos);

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
    var academiasMap = academiaService.findAllByUsuario(usuario.getId());
    List<Academia> academias = academiasMap.getOrDefault("director", new ArrayList<>());
    model.addAttribute("academias", academias);
    return "panel/academias";
  }

  @GetMapping("/cursos")
  public String misCursos(@AuthenticationPrincipal Usuario usuario, Model model) {
    var academiasMap = academiaService.findAllByUsuario(usuario.getId());
    List<Academia> academias = academiasMap.getOrDefault("director", new ArrayList<>());

    List<Curso> cursos = new ArrayList<>();
    for (Academia academia : academias) {
      cursos.addAll(academia.getCursos());
    }

    model.addAttribute("cursos", cursos);
    return "panel/cursos";
  }

  @GetMapping("/ofertas")
  public String misOfertas(@AuthenticationPrincipal Usuario usuario, Model model) {
    var academiasMap = academiaService.findAllByUsuario(usuario.getId());
    List<Academia> academias = academiasMap.getOrDefault("director", new ArrayList<>());

    List<Oferta> ofertas = new ArrayList<>();
    for (Academia academia : academias) {
      ofertas.addAll(ofertaService.findByAcademia(academia));
    }

    model.addAttribute("ofertas", ofertas);
    return "panel/ofertas";
  }
}

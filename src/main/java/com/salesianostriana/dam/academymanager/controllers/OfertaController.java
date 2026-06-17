package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.academymanager.modules.Academia;
import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.OfertaService;




@Controller
public class OfertaController {
  @Autowired
  private AcademiaService academiaService;
  @Autowired
  private OfertaService ofertaService;

  @GetMapping("/ofertas/crear")
  public String formularioCrearOferta(@RequestParam String academiaId, Model model) {
    Oferta oferta = new Oferta();
    oferta.setAcademia(academiaService.findById(academiaId).orElseThrow(() -> new com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException("Academia no encontrada")));
    model.addAttribute("oferta", oferta);
    model.addAttribute("cursos", oferta.getAcademia().getCursos());
    return "ofertas/crear";
  }

  @PostMapping("/ofertas/crear")
  public String crearOferta(@ModelAttribute("oferta") Oferta oferta) {
    ofertaService.save(oferta);
    return "redirect:/ofertas/" + oferta.getId();
  }

  @GetMapping("/ofertas/buscar")
  public String buscarOfertas(Model model) {
    model.addAttribute("ofertas", ofertaService.findAllActivas());
    return "ofertas/buscar";
  }

  @GetMapping("/ofertas/{id}")
  public String detallesOferta(@PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Oferta oferta = ofertaService.findByIdOrThrow(id);
    boolean editable = academiaService.esDirector(oferta.getAcademia().getId(), usuario.getId());
    model.addAttribute("oferta", oferta);
    model.addAttribute("editable", editable);
    model.addAttribute("aplicado", oferta.getCandidatos().stream().anyMatch(c -> c.getId().equals(usuario.getId())));
    model.addAttribute("aplicable", ofertaService.esOfertaAplicable(oferta));
    return "ofertas/detalle";
  }  

  @GetMapping("/ofertas/editar")
  public String formularioEditarOferta(@RequestParam String id, Model model) {
    Oferta oferta = ofertaService.findByIdOrThrow(id);
    model.addAttribute("oferta", oferta);
    model.addAttribute("cursos", oferta.getAcademia().getCursos());
    return "ofertas/editar";
  }

  @PostMapping("/ofertas/editar")
  public String editarOferta(@ModelAttribute("oferta") Oferta oferta, @RequestParam String id, @RequestParam(required = false) String cursoId) {
    ofertaService.editarOferta(id, oferta.getTitulo(), oferta.getDescripcion(), oferta.getTipoOferta(), cursoId);
    return "redirect:/ofertas/" + id;
  }

  @PostMapping("/ofertas/borrar")
  public String borrarOferta(@RequestParam String id) {
    Oferta oferta = ofertaService.findByIdOrThrow(id);
    ofertaService.delete(oferta);
    return "redirect:/academias/" + oferta.getAcademia().getId();
  }

  @PostMapping("/ofertas/aplicar")
  public String aplicarOferta(@RequestParam String id, @AuthenticationPrincipal Usuario usuario) {
    ofertaService.aplicarOferta(id, usuario.getId());
    return "redirect:/ofertas/" + ofertaService.findByIdOrThrow(id).getId();
  }

  @GetMapping("/ofertas/{id}/candidatos")
  @PreAuthorize("@ofertaService.esDirectorDeOferta(#id, principal.id)")
  public String verCandidatos(@PathVariable String id, Model model) {
    Oferta oferta = ofertaService.findByIdOrThrow(id);
    model.addAttribute("candidatos", oferta.getCandidatos());
    model.addAttribute("oferta", oferta);
    return "ofertas/candidatos";
  }

  @PostMapping("/ofertas/{id}/aceptar/{candidatoId}")
  public String aceptarCandidato(@PathVariable String id, @PathVariable String candidatoId) {
    ofertaService.aceptarCandidato(id, candidatoId);
    return "redirect:/ofertas/" + id + "/candidatos";
  }

  @PostMapping("/ofertas/{id}/rechazar/{candidatoId}")
  public String rechazarCandidato(@PathVariable String id, @PathVariable String candidatoId) {
    ofertaService.rechazarCandidato(id, candidatoId);
    return "redirect:/ofertas/" + id + "/candidatos";
  }
}

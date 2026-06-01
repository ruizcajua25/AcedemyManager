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
    oferta.setAcademia(academiaService.findById(academiaId).orElseThrow(() -> new RuntimeException("Academia no encontrada")));
    model.addAttribute("oferta", oferta);
    return "ofertas/crear";
  }

  @PostMapping("/ofertas/crear")
  public String crearOferta(@ModelAttribute("oferta") Oferta oferta) {
    ofertaService.save(oferta);
    return "redirect:/ofertas/" + oferta.getId();
  }

  @GetMapping("/ofertas/{id}")
  public String detallesOferta(@PathVariable String id, Model model, @AuthenticationPrincipal Usuario usuario) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    boolean editable = oferta.getAcademia().getDirectores().stream().anyMatch(d -> d.getId().getUsuarioId().equals(usuario.getId()));
    model.addAttribute("oferta", oferta);
    model.addAttribute("editable", editable);
    model.addAttribute("aplicado", oferta.getCandidatos().stream().anyMatch(c -> c.getId().equals(usuario.getId())));
    model.addAttribute("aplicable", ofertaService.esOfertaAplicable(oferta));
    return "ofertas/detalle";
  }  

  @GetMapping("/ofertas/editar")
  public String formularioEditarOferta(@RequestParam String id, Model model) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    model.addAttribute("oferta", oferta);
    return "ofertas/editar";
  }

  @PostMapping("/ofertas/editar")
  public String editarOferta(@ModelAttribute("oferta") Oferta oferta, @RequestParam String id) {
    Oferta ofertaOriginal = ofertaService.findById(id)
      .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

    ofertaOriginal.setTitulo(oferta.getTitulo());
    ofertaOriginal.setDescripcion(oferta.getDescripcion());
    ofertaOriginal.setTipoOferta(oferta.getTipoOferta());

    ofertaService.save(ofertaOriginal);
    return "redirect:/ofertas/" + id;
  }

  @PostMapping("/ofertas/borrar")
  public String borrarOferta(@RequestParam String id) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    ofertaService.delete(oferta);
    return "redirect:/academias/" + oferta.getAcademia().getId();
  }

  @PostMapping("/ofertas/aplicar")
  public String aplicarOferta(@RequestParam String id, @AuthenticationPrincipal Usuario usuario) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    if (!ofertaService.esOfertaAplicable(oferta)) {
      throw new RuntimeException("No puedes aplicar a esta oferta");
    }
    ofertaService.aplicar(oferta, usuario);
    ofertaService.save(oferta);
    return "redirect:/ofertas/" + oferta.getId();
  }

  @GetMapping("/ofertas/{id}/candidatos")
  @PreAuthorize("@ofertaService.esDirectorDeOferta(#id, principal.id)")
  public String verCandidatos(@PathVariable String id, Model model) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    model.addAttribute("candidatos", oferta.getCandidatos());
    model.addAttribute("oferta", oferta);
    return "ofertas/candidatos";
  }

  @PostMapping("/ofertas/{id}/aceptar/{candidatoId}")
  public String aceptarCandidato(@PathVariable String id, @PathVariable String candidatoId) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    ofertaService.aceptarCandidato(oferta, candidatoId);
    ofertaService.save(oferta);
    return "redirect:/ofertas/" + id + "/candidatos";
  }

  @PostMapping("/ofertas/{id}/rechazar/{candidatoId}")
  public String rechazarCandidato(@PathVariable String id, @PathVariable String candidatoId) {
    Oferta oferta = ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada"));
    ofertaService.rechazarCandidato(oferta, candidatoId);
    ofertaService.save(oferta);
    return "redirect:/ofertas/" + id + "/candidatos";
  }
}
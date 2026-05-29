package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.academymanager.modules.Oferta;
import com.salesianostriana.dam.academymanager.services.AcademiaService;
import com.salesianostriana.dam.academymanager.services.OfertaService;

import org.springframework.web.bind.annotation.PostMapping;



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
    return "redirect:/academias/" + oferta.getAcademia().getId();
  }

  @GetMapping("/ofertas/{id}")
  public String detallesOferta(@PathVariable String id, Model model) {
    model.addAttribute("oferta", ofertaService.findById(id).orElseThrow(() -> new RuntimeException("Oferta no encontrada")));
    return "ofertas/detalle";
  }  
}
package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.modules.Profesor;
import com.salesianostriana.dam.academymanager.services.ProfesorServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class ProfesorController {
  @Autowired
  private ProfesorServices service;
  
  @GetMapping("/profesores")
  public String profesores (Model model) {
    model.addAttribute("profesores", service.getAllProfesores());
    return "profesores";
  }

  @PostMapping("/profesores")
  public ResponseEntity<Profesor> crearUsuario(@RequestBody Profesor profesor) {
    service.addProfesor(profesor);
    return ResponseEntity.ok(profesor);
  }
  
}
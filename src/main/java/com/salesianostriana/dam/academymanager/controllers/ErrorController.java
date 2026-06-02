package com.salesianostriana.dam.academymanager.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.academymanager.exceptions.AccionNoPermitidaException;
import com.salesianostriana.dam.academymanager.exceptions.ObjetoNoEncontradoException;
import com.salesianostriana.dam.academymanager.exceptions.UsernameRepetidoException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@ControllerAdvice
public class ErrorController {

  @GetMapping("/error")
  public String error(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    int statusCode = status != null ? Integer.parseInt(status.toString()) : 500;
    String titulo;
    String mensaje;
    switch (statusCode) {
      case 404 -> {
          mensaje = "La pagina que buscas no existe o se ha movido.";
          titulo = "Pagina no encontrada";
          }
      case 403 -> {
          mensaje = "No tienes permiso para entrar aqui.";
          titulo = "Acceso no permitido";
          }
      default -> {
          titulo = "Ha ocurrido un error";
          mensaje = "Algo ha fallado mientras cargabamos la pagina.";
          }
    }
            
    model.addAttribute("status", statusCode);
    model.addAttribute("titulo", titulo);
    model.addAttribute("mensaje", mensaje);

    return "error";
  }

  @ExceptionHandler(ObjetoNoEncontradoException.class)
  public String objetoNoEncontrado(ObjetoNoEncontradoException ex, Model model, HttpServletResponse response) {
    response.setStatus(HttpStatus.NOT_FOUND.value());
    model.addAttribute("status", HttpStatus.NOT_FOUND.value());
    model.addAttribute("titulo", "No hemos encontrado eso");
    model.addAttribute("mensaje", ex.getMessage());

    return "error";
  }

  @ExceptionHandler(AccionNoPermitidaException.class)
  public String accionNoPermitida(AccionNoPermitidaException ex, Model model, HttpServletResponse response) {
    response.setStatus(HttpStatus.FORBIDDEN.value());
    model.addAttribute("status", HttpStatus.FORBIDDEN.value());
    model.addAttribute("titulo", "No puedes hacer esta accion");
    model.addAttribute("mensaje", ex.getMessage());

    return "error";
  }

  @ExceptionHandler(UsernameRepetidoException.class)
  public String usernameRepetido(UsernameRepetidoException ex, Model model, HttpServletResponse response) {
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
    model.addAttribute("titulo", "No se ha podido guardar");
    model.addAttribute("mensaje", ex.getMessage());

    return "error";
  }

  @ExceptionHandler(Exception.class)
  public String excepcion(Exception ex, Model model, HttpServletResponse response) {
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    model.addAttribute("titulo", "Ha ocurrido un error");
    model.addAttribute("mensaje", "Algo ha fallado mientras cargabamos la pagina.");

    return "error";
  }
}

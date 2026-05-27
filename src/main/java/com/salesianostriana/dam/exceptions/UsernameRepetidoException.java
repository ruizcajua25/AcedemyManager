package com.salesianostriana.dam.exceptions;

public class UsernameRepetidoException extends RuntimeException {
  public UsernameRepetidoException(String username) {
    super("El nombre de usuario ya existe: " + username);
  }
    
}
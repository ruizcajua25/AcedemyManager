package com.salesianostriana.dam.academymanager.exceptions;

public class UsernameRepetidoException extends RuntimeException {

  public UsernameRepetidoException(String username) {
    super("El usuario " + username + " ya existe");
  }
}

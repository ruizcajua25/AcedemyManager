package com.salesianostriana.dam.academymanager.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.academymanager.modules.Usuario;
import com.salesianostriana.dam.academymanager.repositories.UsuarioRepository;
import com.salesianostriana.dam.academymanager.exceptions.UsernameRepetidoException;

@Service
public class UsuarioService extends BaseService<Usuario, String, UsuarioRepository> implements UserDetailsService {
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return (UserDetails) usuarioRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
  }

  @Override
  public Usuario save(Usuario usuario) {
    usuarioRepository.findByUsername(usuario.getUsername()).ifPresent(u -> {
      throw new UsernameRepetidoException(usuario.getUsername());
    });
    return usuarioRepository.save(usuario);
  }
}

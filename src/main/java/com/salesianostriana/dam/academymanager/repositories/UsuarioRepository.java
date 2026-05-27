package com.salesianostriana.dam.academymanager.repositories;

import java.util.Optional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.academymanager.modules.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
  Optional<Usuario> findByUsername(String username);
} 

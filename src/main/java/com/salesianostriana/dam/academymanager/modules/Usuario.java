package com.salesianostriana.dam.academymanager.modules;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "usuarios")
@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private String id;
  private String dni;
  private String nombre;
  private String apellidos;
  private String username;
  @ToString.Exclude
  private String password;
  private String email;
  @Builder.Default
  private String role = "ROLE_USER";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role != null ? role : "ROLE_USER"));
  }    
}

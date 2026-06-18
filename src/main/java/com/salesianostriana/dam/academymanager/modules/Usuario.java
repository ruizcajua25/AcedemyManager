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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

  @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El DNI no tiene un formato valido")
  private String dni;

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  private String nombre;

  @NotBlank(message = "Los apellidos son obligatorios")
  @Size(min = 2, max = 150, message = "Los apellidos deben tener entre 2 y 150 caracteres")
  private String apellidos;

  @NotBlank(message = "El nombre de usuario es obligatorio")
  @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
  private String username;

  @ToString.Exclude
  private String password;

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El email no tiene un formato valido")
  private String email;

  @Builder.Default
  private String role = "ROLE_USER";

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role != null ? role : "ROLE_USER"));
  }    
}

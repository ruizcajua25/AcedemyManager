package com.salesianostriana.dam.academymanager.services;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T, ID> {
  List<T> findAll();
  Optional<T> findById(ID id);
  
  T save(T entity);
  T edit(T entity);
  
  void delete(T entity);
  void deleteById(ID id);
}
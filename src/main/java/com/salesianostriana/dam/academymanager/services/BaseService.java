package com.salesianostriana.dam.academymanager.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public class BaseService<T, ID, R extends JpaRepository<T, ID>> implements IBaseService<T, ID> {
  
  @Autowired
  protected R repository;

  @Override
  public List<T> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<T> findById(ID id) {
    return repository.findById(id);
  }

  @Override
  public T save(T entity) {
    return repository.save(entity);
  }

  @Override
  public T edit(T entity) {
    return repository.save(entity);
  }

  @Override
  public void delete(T entity) {
    repository.delete(entity);
  }

  @Override
  public void deleteById(ID id) {
    repository.deleteById(id);
  }

  public List<T> findAllByPredicate(Predicate<T> predicate) {
    return repository.findAll().stream().filter(predicate).toList();
  }
    
}
package org.pmtool.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.pmtool.model.Proyecto;

public interface ProyectoRepository {
  Proyecto save(Proyecto proyecto);
  
  Optional<Proyecto> findById(UUID id);
  
  Optional<Proyecto> findByNombre(String nombre);
  
  List<Proyecto> findAll();
  
  void delete(UUID id);
}

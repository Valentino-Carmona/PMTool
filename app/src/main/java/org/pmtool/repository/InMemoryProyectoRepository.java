package org.pmtool.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.pmtool.model.Proyecto;

public class InMemoryProyectoRepository implements ProyectoRepository {
  private final Map<UUID, Proyecto> database = new ConcurrentHashMap<>();

  @Override
  public Proyecto save(Proyecto proyecto) {
    if (proyecto == null) {
      throw new IllegalArgumentException("El proyecto no puede ser nulo");
    }
    database.put(proyecto.getId(), proyecto);
    return proyecto;
  }

  @Override
  public Optional<Proyecto> findById(UUID id) {
    return Optional.ofNullable(database.get(id));
  }

  @Override
  public Optional<Proyecto> findByNombre(String nombre) {
    return database.values().stream()
        .filter(p -> p.getNombre().equals(nombre))
        .findFirst();
  }

  @Override
  public List<Proyecto> findAll() {
    return new ArrayList<>(database.values());
  }

  @Override
  public void delete(UUID id) {
    database.remove(id);
  }
}

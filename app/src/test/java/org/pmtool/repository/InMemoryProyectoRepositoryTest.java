package org.pmtool.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pmtool.model.Proyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProyectoRepositoryTest {

  private InMemoryProyectoRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryProyectoRepository();
  }

  @Test
  void testSaveAndFindById() {
    Proyecto p = new Proyecto("Test Proy", 100, 5000.0);
    repository.save(p);

    Optional<Proyecto> retrieved = repository.findById(p.getId());
    assertTrue(retrieved.isPresent());
    assertEquals(p.getNombre(), retrieved.get().getNombre());
  }

  @Test
  void testFindByIdNotFound() {
    Optional<Proyecto> retrieved = repository.findById(UUID.randomUUID());
    assertFalse(retrieved.isPresent());
  }

  @Test
  void testFindAll() {
    Proyecto p1 = new Proyecto("Proy 1", 100, 1000.0);
    Proyecto p2 = new Proyecto("Proy 2", 200, 2000.0);
    repository.save(p1);
    repository.save(p2);

    List<Proyecto> all = repository.findAll();
    assertEquals(2, all.size());
    assertTrue(all.contains(p1));
    assertTrue(all.contains(p2));
  }

  @Test
  void testDelete() {
    Proyecto p = new Proyecto("Test Proy", 100, 5000.0);
    repository.save(p);
    
    repository.delete(p.getId());
    
    Optional<Proyecto> retrieved = repository.findById(p.getId());
    assertFalse(retrieved.isPresent());
  }
}

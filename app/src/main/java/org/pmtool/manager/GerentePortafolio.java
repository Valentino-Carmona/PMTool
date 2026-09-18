package org.pmtool.manager;

import java.util.List;
import org.pmtool.model.Proyecto;
import org.pmtool.repository.InMemoryProyectoRepository;
import org.pmtool.repository.ProyectoRepository;

public class GerentePortafolio {
  private final ProyectoRepository proyectoRepository;

  // Por retrocompatibilidad con tests actuales que hacen new GerentePortafolio()
  public GerentePortafolio() {
    this.proyectoRepository = new InMemoryProyectoRepository();
  }

  public GerentePortafolio(ProyectoRepository proyectoRepository) {
    this.proyectoRepository = proyectoRepository;
  }

  public Proyecto crearProyecto(String nombre, int horas, double presupuesto) {
    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre del proyecto no puede ser nulo o vacío");
    }

    if (proyectoRepository.findByNombre(nombre).isPresent()) {
      throw new IllegalArgumentException("El nombre del proyecto ya existe");
    }
    
    Proyecto proyecto = new Proyecto(nombre, horas, presupuesto);
    return proyectoRepository.save(proyecto);
  }

  public List<Proyecto> getProyectos() {
    return proyectoRepository.findAll();
  }
}

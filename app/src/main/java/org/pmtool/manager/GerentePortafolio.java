package org.pmtool.manager;

import java.util.ArrayList;
import java.util.List;
import org.pmtool.model.Proyecto;

public class GerentePortafolio {
  private List<Proyecto> proyectos = new ArrayList<>();

  public Proyecto crearProyecto(String nombre, int horas, double presupuesto) {
    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre del proyecto no puede ser nulo o vacío");
    }

    for (Proyecto proyecto : proyectos) {
      if (proyecto.igualNombre(nombre)) {
        throw new IllegalArgumentException("El nombre del proyecto ya existe");
      }
    }
    Proyecto proyecto = new Proyecto(nombre, horas, presupuesto);
    proyectos.add(proyecto);
    return proyecto;
  }

  public List<Proyecto> getProyectos() {
    return proyectos;
  }
}

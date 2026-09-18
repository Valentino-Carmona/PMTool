package org.pmtool.manager;

import java.time.LocalDate;
import org.pmtool.model.*;
import org.pmtool.repository.InMemoryProyectoRepository;
import org.pmtool.repository.ProyectoRepository;

public class GerenteProyecto {
  private final ProyectoRepository proyectoRepository;

  public GerenteProyecto() {
    this.proyectoRepository = new InMemoryProyectoRepository();
  }

  public GerenteProyecto(ProyectoRepository proyectoRepository) {
    this.proyectoRepository = proyectoRepository;
  }

  public void planificarProyecto(Proyecto proyecto, LocalDate inicio) {
    proyecto.planificarProyecto(inicio);
    proyectoRepository.save(proyecto);
  }

  public void finalizarProyecto(Proyecto proyecto) {
    proyecto.finalizar();
    proyectoRepository.save(proyecto);
  }

  public Actividad crearActividad(Proyecto proyecto, String nombre, int duracionDias) {
    Actividad actividad = proyecto.agregarActividad(nombre, duracionDias);
    proyectoRepository.save(proyecto);
    return actividad;
  }

  public Actividad crearSubActividad(Proyecto proyecto, Actividad actividad, String nombre, int duracionDias) {
    Actividad subActividad = actividad.agregarSubactividad(nombre, duracionDias);
    proyectoRepository.save(proyecto);
    return subActividad;
  }

  // Mantenemos este por retrocompatibilidad con tests actuales que no pasan el proyecto
  public Actividad crearSubActividad(Actividad actividad, String nombre, int duracionDias) {
    return actividad.agregarSubactividad(nombre, duracionDias);
  }

  public void configurarDependencia(Actividad actividad, Actividad predecesora, int leadLag) {
    actividad.setDependencia(new FinishToStart(predecesora, leadLag));
    // Ideally we would save the project here, but we don't have its reference in this legacy method signature
  }
}

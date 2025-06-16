package org.pmtool.manager;

import java.time.LocalDate;
import org.pmtool.model.*;

public class GerenteProyecto {

  public void planificarProyecto(Proyecto proyecto, LocalDate inicio) {
    proyecto.planificarProyecto(inicio);
  }

  public void finalizarProyecto(Proyecto proyecto) {
    proyecto.finalizar();
  }

  public Actividad crearActividad(Proyecto proyecto, String nombre, int duracionDias) {
    return proyecto.agregarActividad(nombre, duracionDias);
  }

  public Actividad crearSubActividad(Actividad actividad, String nombre, int duracionDias) {
    return actividad.agregarSubactividad(nombre, duracionDias);
  }

  public void configurarDependencia(Actividad actividad, Actividad predecesora, int leadLag) {
    actividad.setDependencia(new FinishToStart(predecesora, leadLag));
  }
}

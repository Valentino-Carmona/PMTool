package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class FinishToStart implements IDependencia {
  private final Actividad predecesora;
  private final int leadLag;

  public FinishToStart(Actividad predecesora, int leadLag) {
    if (predecesora == null) {
      throw new IllegalArgumentException("La predecesora no puede ser nula");
    }

    this.predecesora = Objects.requireNonNull(predecesora, "La predecesora no puede ser nula");
    this.leadLag = leadLag;
  }

  @Override
  public LocalDate calcularInicioDependiente(int duracionDias) {
    return this.predecesora.calcularFechaDependienteFin(1 + leadLag);
  }

  @Override
  public LocalDate calcularFinDependiente(int duracionDias) {
    LocalDate inicioDependiente = calcularInicioDependiente(duracionDias);
    return inicioDependiente.plusDays(duracionDias);
  }

  @Override
  public void verificarActivacion(Actividad actividad) {
    if (!predecesora.isCompletada()) {
      throw new IllegalStateException(
          "No se puede activar la actividad '"
              + actividad.getNombre()
              + "' porque su predecesora '"
              + predecesora.getNombre()
              + "' no está completada.");
    }
  }

  @Override
  public Actividad getPredecesora() {
    return predecesora;
  }

  @Override
  public int getLeadLag() {
    return leadLag;
  }
}

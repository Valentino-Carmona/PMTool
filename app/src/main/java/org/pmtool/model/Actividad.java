package org.pmtool.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.pmtool.validation.ValidadorCiclosDependencias;

public class Actividad {
  private String numeroEDT;
  private String nombre;
  private int duracionDias;
  private IDependencia dependencia;
  private LocalDate fechaInicioPlanificada;
  private LocalDate fechaFinPlanificada;
  private LocalDate fechaInicioReal;
  private LocalDate fechaFinReal;
  private EstadoActividad estado;
  private List<Actividad> subactividades;

  public enum EstadoActividad {
    PLANIFICADA,
    EN_EJECUCION,
    COMPLETADA;
  }

  public Actividad(String numeroEDT, String nombre, int duracionDias) {
    this.numeroEDT = Objects.requireNonNull(numeroEDT, "El número EDT no puede ser nulo");
    this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo");
    this.duracionDias = duracionDias;
    this.estado = EstadoActividad.PLANIFICADA;
    this.subactividades = new ArrayList<>();
  }

  public void calcularFechasPlanificadas(LocalDate fechaInicioProyecto) {
    if (fechaInicioProyecto == null) {
      throw new IllegalArgumentException("La fecha de inicio del proyecto no puede ser nula");
    }

    if (!subactividades.isEmpty()) {
      for (Actividad sub : subactividades) {
        sub.calcularFechasPlanificadas(fechaInicioProyecto);
      }
      this.fechaInicioPlanificada =
          subactividades.stream()
              .map(Actividad::getFechaInicioPlanificada)
              .filter(Objects::nonNull)
              .min(LocalDate::compareTo)
              .orElseThrow(
                  () ->
                      new IllegalStateException(
                          "No se encontraron fechas de inicio en subactividades"));
      this.fechaFinPlanificada =
          subactividades.stream()
              .map(Actividad::getFechaFinPlanificada)
              .filter(Objects::nonNull)
              .max(LocalDate::compareTo)
              .orElseThrow(
                  () ->
                      new IllegalStateException(
                          "No se encontraron fechas de fin en subactividades"));
    } else {
      LocalDate inicio;
      LocalDate fin;
      if (dependencia != null) {
        inicio = dependencia.calcularInicioDependiente(duracionDias);
        fin = dependencia.calcularFinDependiente(duracionDias);
      } else {
        inicio = fechaInicioProyecto;
        fin = inicio.plusDays(duracionDias);
      }
      this.fechaInicioPlanificada = inicio;
      this.fechaFinPlanificada = fin;
    }
  }

  public void setDependencia(IDependencia dependencia) {
    if (EstadoActividad.COMPLETADA.equals(estado)) {
      throw new IllegalStateException(
          "No se puede definir una dependencia para una actividad completada.");
    }

    ValidadorCiclosDependencias.validarSinCiclos(this, dependencia);
    this.dependencia = dependencia;
  }

  public Actividad agregarSubactividad(String nombre, int duracionDias) {
    if (EstadoActividad.COMPLETADA.equals(this.estado)) {
      throw new IllegalStateException(
          "No se pueden agregar subactividades a una actividad completada");

    } else if (duracionDias < 0) {
      throw new IllegalArgumentException("La duración de la subactividad debe ser mayor que cero.");
    }

    int subNivel = this.subactividades.size() + 1;
    Actividad subactividad = new Actividad((this.numeroEDT + "." + subNivel), nombre, duracionDias);
    subactividades.add(subactividad);
    return subactividad;
  }

  public void activar() {
    if (EstadoActividad.COMPLETADA.equals(estado)) {
      throw new IllegalStateException("No se puede activar una actividad ya completada.");

    } else if (EstadoActividad.EN_EJECUCION.equals(estado)) {
      throw new IllegalStateException("La actividad ya está en ejecución.");

    } else if (dependencia != null) {
      dependencia.verificarActivacion(this);
    }
    this.estado = EstadoActividad.EN_EJECUCION;
    this.fechaInicioReal = LocalDate.now();
  }

  public void desactivar() {
    if (EstadoActividad.PLANIFICADA.equals(this.estado)) {
      throw new IllegalStateException(
          "No se puede desactivar una actividad que no está en ejecución.");

    } else if (EstadoActividad.COMPLETADA.equals(this.estado)) {
      throw new IllegalStateException("No se puede desactivar una actividad ya completada.");

    } else if (!subactividades.isEmpty()
        && !subactividades.stream().allMatch(Actividad::isCompletada)) {
      throw new IllegalStateException(
          "No se puede desactivar una actividad hasta que todas sus subactividades estén completadas.");
    }
    this.fechaFinReal = LocalDate.now();
    this.estado = EstadoActividad.COMPLETADA;
  }

  public LocalDate calcularFechaDependienteInicio(int duracionDias) {
    if (this.fechaInicioPlanificada == null) {
      throw new IllegalStateException("La actividad no tiene una fecha de inicio planificada.");
    }
    return this.fechaInicioPlanificada.plusDays(duracionDias);
  }

  public LocalDate calcularFechaDependienteFin(int duracionDias) {
    if (this.fechaFinPlanificada == null) {
      throw new IllegalStateException("La actividad no tiene una fecha de fin planificada.");
    }
    return this.fechaFinPlanificada.plusDays(duracionDias);
  }

  public void setFechaInicioReal(LocalDate fechaInicioReal) {
    if (fechaInicioReal == null) {
      throw new IllegalArgumentException("La fecha de inicio real no puede ser nula.");
    }
    this.fechaInicioReal = fechaInicioReal;
  }

  public void setFechaFinReal(LocalDate fechaFinReal) {
    if (fechaFinReal == null) {
      throw new IllegalArgumentException("La fecha de fin real no puede ser nula.");
    }
    if (fechaFinReal != null && fechaFinReal.isBefore(fechaInicioReal)) {
      throw new IllegalArgumentException(
          "La fecha de fin real no puede ser anterior a la fecha de inicio real.");
    }
    this.fechaFinReal = fechaFinReal;
  }

  public String getNumeroEDT() {
    return numeroEDT;
  }

  public List<Actividad> getSubactividades() {
    return subactividades;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public LocalDate getFechaInicioPlanificada() {
    return fechaInicioPlanificada;
  }

  public LocalDate getFechaFinPlanificada() {
    return fechaFinPlanificada;
  }

  public LocalDate getFechaInicioReal() {
    return fechaInicioReal;
  }

  public LocalDate getFechaFinReal() {
    return fechaFinReal;
  }

  public int getDuracionDias() {
    return duracionDias;
  }

  public EstadoActividad getEstado() {
    return estado;
  }

  public IDependencia getDependencia() {
    return dependencia;
  }

  public boolean isCompletada() {
    return EstadoActividad.COMPLETADA.equals(estado)
        && fechaFinReal != null
        && (subactividades.isEmpty() || subactividades.stream().allMatch(Actividad::isCompletada));
  }

  public boolean isPlanificada() {
    return EstadoActividad.PLANIFICADA.equals(estado);
  }

  public boolean isEnEjecucion() {
    return EstadoActividad.EN_EJECUCION.equals(estado);
  }

  @Override
  public String toString() {
    return "\nActividad {\n"
        + "  numeroEDT = "
        + numeroEDT
        + '\n'
        + "  nombre = "
        + nombre
        + '\n'
        + "  estado = "
        + estado
        + '\n'
        + "  duracionDias = "
        + duracionDias
        + '\n'
        + "  fechaInicioPlanificada = "
        + fechaInicioPlanificada
        + '\n'
        + "  fechaFinPlanificada = "
        + fechaFinPlanificada
        + '\n'
        + "  dependencia = "
        + (dependencia != null ? dependencia.getPredecesora().getNumeroEDT() : "N/A")
        + '\n'
        + "  leadLag = "
        + (dependencia != null ? dependencia.getLeadLag() : "N/A")
        + "\n}";
  }
}

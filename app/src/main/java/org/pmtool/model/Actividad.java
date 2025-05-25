package org.pmtool.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Actividad {
    private String numeroEDT;
    private String nombre;
    private int duracionDias;
    private Dependencia dependencia;
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
        if (duracionDias <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0 días");
        }
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
            this.fechaInicioPlanificada = subactividades.stream()
                    .map(Actividad::getFechaInicioPlanificada)
                    .filter(Objects::nonNull)
                    .min(LocalDate::compareTo)
                    .orElseThrow(() -> new IllegalStateException("No se encontraron fechas de inicio en subactividades"));
            this.fechaFinPlanificada = subactividades.stream()
                    .map(Actividad::getFechaFinPlanificada)
                    .filter(Objects::nonNull)
                    .max(LocalDate::compareTo)
                    .orElseThrow(() -> new IllegalStateException("No se encontraron fechas de fin en subactividades"));
        } else {
            LocalDate inicio;
            LocalDate fin;
            if (dependencia != null) {
                Actividad pre = dependencia.getPredecesora();
                if (pre.getFechaInicioPlanificada() == null || pre.getFechaFinPlanificada() == null) {
                    throw new IllegalStateException("La predecesora debe tener fechas planificadas definidas");
                }
                inicio = dependencia.calcularInicioDependiente(pre.getFechaInicioPlanificada(), pre.getFechaFinPlanificada(), duracionDias);
                fin = dependencia.calcularFinDependiente(pre.getFechaInicioPlanificada(), pre.getFechaFinPlanificada(), duracionDias);
            } else {
                inicio = fechaInicioProyecto;
                fin = inicio.plusDays(duracionDias);
            }
            this.fechaInicioPlanificada = inicio;
            this.fechaFinPlanificada = fin;
        }
    }

    public void setDependencia(Dependencia dependencia) {
        if (dependencia != null && dependencia.getPredecesora() == this) {
            throw new IllegalArgumentException("Una actividad no puede ser su propia predecesora");
        }
        this.dependencia = dependencia;
    }

    public void agregarSubactividad(Actividad subactividad) {
        Objects.requireNonNull(subactividad, "La subactividad no puede ser nula");
        int subNivel = subactividades.size() + 1;
        subactividad.setNumeroEDT(this.numeroEDT + "." + subNivel);
        subactividades.add(subactividad);
    }

    public void activar() {
        if (EstadoActividad.COMPLETADA.equals(estado)) {
            throw new IllegalStateException("No se puede activar una actividad ya completada.");
        }
        if (dependencia != null) {
            dependencia.verificarActivacion(this);
        }
        this.estado = EstadoActividad.EN_EJECUCION;
        this.fechaInicioReal = LocalDate.now();
    }

    public void desactivar() {
        if (EstadoActividad.PLANIFICADA.equals(estado)) {
            throw new IllegalStateException("No se puede desactivar una actividad que no está en ejecución.");
        }
        if (!subactividades.isEmpty() && !subactividades.stream().allMatch(Actividad::isCompletada)) {
                throw new IllegalStateException("No se puede desactivar una actividad hasta que todas sus subactividades estén completadas.");
            }
        
        if (fechaFinReal == null) {
            this.estado = EstadoActividad.COMPLETADA;
            this.fechaFinReal = LocalDate.now();
        } else {
            this.estado = EstadoActividad.COMPLETADA; // Solo cambia estado si ya tiene fechaFinReal
        }
    }

    public String getNumeroEDT() {
        return numeroEDT;
    }

    private void setNumeroEDT(String numeroEDT) {
        this.numeroEDT = numeroEDT;
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

    public void setFechaInicioPlanificada(LocalDate fechaInicioPlanificada) {
        this.fechaInicioPlanificada = fechaInicioPlanificada;
    }

    public LocalDate getFechaFinPlanificada() {
        return fechaFinPlanificada;
    }

    public void setFechaFinPlanificada(LocalDate fechaFinPlanificada) {
        this.fechaFinPlanificada = fechaFinPlanificada;
    }

    public LocalDate getFechaInicioReal() {
        return fechaInicioReal;
    }

    public void setFechaInicioReal(LocalDate fechaInicioReal) {
        this.fechaInicioReal = fechaInicioReal;
    }

    public LocalDate getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(LocalDate fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public boolean isCompletada() {
        return EstadoActividad.COMPLETADA.equals(estado) && fechaFinReal != null && (subactividades.isEmpty() || subactividades.stream().allMatch(Actividad::isCompletada));
    }

    public boolean isPlanificada() {
        return EstadoActividad.PLANIFICADA.equals(estado);
    }

    public boolean isEnEjecucion() {
        return EstadoActividad.EN_EJECUCION.equals(estado);
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "numeroEDT='" + numeroEDT + '\'' +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
} 

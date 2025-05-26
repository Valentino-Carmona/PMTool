package org.pmtool.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private static int contadorProyectos = 1;
    private int numero;
    private String nombre;
    private LocalDate fechaInicioPlanificada;
    private LocalDate fechaFinPlanificada;
    private LocalDate fechaInicioReal;
    private LocalDate fechaFinReal;
    private int totalHorasEstimadas;
    private double presupuesto;
    private EstadoProyecto estado;
    private List<Actividad> actividades;

    public enum EstadoProyecto {
        PLANIFICADO,
        EN_CURSO,
        FINALIZADO;
    }

    public Proyecto(String nombre, int totalHorasEstimadas, double presupuesto) {
        this.numero = ++contadorProyectos;
        this.nombre = nombre;
        this.totalHorasEstimadas = totalHorasEstimadas;
        this.presupuesto = presupuesto;
        this.estado = EstadoProyecto.PLANIFICADO;
        this.actividades = new ArrayList<>();
    }

    public void planificarProyecto(LocalDate fechaInicio) {
        if (this.estado != EstadoProyecto.PLANIFICADO) {
            throw new IllegalStateException("El proyecto debe estar planificado para poder estar en curso");
        }

        if (fechaInicio == null) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser nula");
        }
        
        this.fechaInicioPlanificada = fechaInicio;
        this.fechaInicioReal = fechaInicio;
        this.estado = EstadoProyecto.EN_CURSO;

        // Calcular fechas para todas las actividades
        for (Actividad actividad : actividades) {
            actividad.calcularFechasPlanificadas(fechaInicio);
        }

        // Calcular la fecha de fin del proyecto como la mayor fecha de fin de las actividades
        this.fechaFinPlanificada = actividades.stream()
                .map(Actividad::getFechaFinPlanificada)
                .max(LocalDate::compareTo)
                .orElse(fechaInicio); // Si no hay actividades, usar fechaInicio como fallback
    }

    public void finalizar() {
        if (this.estado != EstadoProyecto.EN_CURSO) {
            throw new IllegalStateException("El proyecto debe estar en curso para poder finalizarlo");
        }

        for (Actividad actividad : actividades) {
            if (!actividad.isCompletada()) {
                throw new IllegalArgumentException("Para finalizar un proyecto todas sus actividades deben estar completadas");
            }
        }
        this.fechaFinReal = LocalDate.now();
        this.estado = EstadoProyecto.FINALIZADO;
    }

    public boolean igualNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    public void agregarActividad(Actividad actividad) {
        if (this.estado == EstadoProyecto.FINALIZADO) {
            throw new IllegalStateException("No se pueden agregar actividades a un proyecto finalizado");
        }
        actividades.add(actividad);
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public LocalDate getFechaFinReal() {
        return fechaFinReal;
    }

    public void setFechaFinReal(LocalDate fechaFinReal) {
        this.fechaFinReal = fechaFinReal;
    }

    public int getTotalHorasEstimadas() {
        return totalHorasEstimadas;
    }

    public void setTotalHorasEstimadas(int totalHorasEstimadas) {
        this.totalHorasEstimadas = totalHorasEstimadas;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }

    public boolean isPlanificado() {
        return EstadoProyecto.PLANIFICADO.equals(this.estado);
    }

    public boolean isEnCurso() {
        return EstadoProyecto.EN_CURSO.equals(this.estado);
    }

    public boolean isFinalizado() {
        return EstadoProyecto.FINALIZADO.equals(this.estado);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "numero=" + numero +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}

package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class StartToStart implements IDependencia {
    private final Actividad predecesora;
    private final int leadLag;

    public StartToStart(Actividad predecesora, int leadLag) {
        this.predecesora = Objects.requireNonNull(predecesora, "La predecesora no puede ser nula");
        this.leadLag = leadLag;
    }

    @Override
    public LocalDate calcularInicioDependiente(int duracionDias) {
        LocalDate inicioPredecesora = this.predecesora.getFechaInicioPlanificada();
        if (inicioPredecesora == null) {
            throw new IllegalStateException("La predecesora debe tener una fecha de inicio planificada");
        }
        return this.predecesora.calcularFechaDependienteInicio(leadLag);
    }

    @Override
    public LocalDate calcularFinDependiente(int duracionDias) {
        LocalDate inicio = calcularInicioDependiente(duracionDias);
        return inicio.plusDays(duracionDias);
    }

    @Override
    public Actividad getPredecesora() {
        return predecesora;
    }

    @Override
    public void verificarActivacion(Actividad actividad) {
        if (!predecesora.isEnEjecucion() && !predecesora.isCompletada()) {
            throw new IllegalStateException("No se puede activar la actividad '" + actividad.getNombre() +
                    "' porque su predecesora '" + predecesora.getNombre() + "' no está en ejecución ni completada.");
        }
    }

    @Override
    public int getLeadLag() {
        return leadLag;
    }
}

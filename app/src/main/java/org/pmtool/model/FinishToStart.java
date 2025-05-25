package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class FinishToStart implements Dependencia {
    private final Actividad predecesora;
    private final int leadLag;

    public FinishToStart(Actividad predecesora, int leadLag) {
        this.predecesora = Objects.requireNonNull(predecesora, "La predecesora no puede ser nula");
        this.leadLag = leadLag;
    }

    @Override
    public LocalDate calcularInicioDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias) {
        if (finPredecesora == null) {
            throw new IllegalStateException("La predecesora debe tener una fecha de fin planificada");
        }
        return finPredecesora.plusDays(1).plusDays(leadLag);
    }

    @Override
    public LocalDate calcularFinDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias) {
        LocalDate inicio = calcularInicioDependiente(inicioPredecesora, finPredecesora, duracionDias);
        return inicio.plusDays(duracionDias);
    }

    @Override
    public void verificarActivacion(Actividad actividad) {
        if (!predecesora.isCompletada()) {
            throw new IllegalStateException("No se puede activar la actividad '" + actividad.getNombre() +
                    "' porque su predecesora '" + predecesora.getNombre() + "' no está completada.");
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

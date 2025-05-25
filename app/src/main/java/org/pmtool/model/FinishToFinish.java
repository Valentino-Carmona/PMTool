package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class FinishToFinish implements Dependencia {
    private final Actividad predecesora;
    private final int leadLag;

    public FinishToFinish(Actividad predecesora, int leadLag) {
        this.predecesora = Objects.requireNonNull(predecesora, "La predecesora no puede ser nula");
        this.leadLag = leadLag;
    }

    @Override
    public LocalDate calcularInicioDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias) {
        LocalDate fin = calcularFinDependiente(inicioPredecesora, finPredecesora, duracionDias);
        return fin.minusDays(duracionDias);
    }

    @Override
    public LocalDate calcularFinDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias) {
        if (finPredecesora == null) {
            throw new IllegalStateException("La predecesora debe tener una fecha de fin planificada");
        }
        return finPredecesora.plusDays(leadLag);
    }

    @Override
    public Actividad getPredecesora() {
        return predecesora;
    }

    @Override
    public void verificarActivacion(Actividad actividad) {
        if (!predecesora.isCompletada()) {
            throw new IllegalStateException("No se puede activar '" + actividad.getNombre() + 
                    "' porque su predecesora '" + predecesora.getNombre() + "' no está completada.");
        }
    }

    @Override
    public int getLeadLag() {
        return leadLag;
    }
}

package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class StartToFinish implements Dependencia {
    private final Actividad predecesora;
    private final int leadLag;

    public StartToFinish(Actividad predecesora, int leadLag) {
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
        if (inicioPredecesora == null) {
            throw new IllegalStateException("La predecesora debe tener una fecha de inicio planificada");
        }
        return inicioPredecesora.plusDays(1).plusDays(leadLag);
    }

    @Override
    public Actividad getPredecesora() {
        return predecesora;
    }

    @Override
    public void verificarActivacion(Actividad actividad) {
        if (!predecesora.isEnEjecucion() && !predecesora.isCompletada()) {
            throw new IllegalStateException("No se puede activar '" + actividad.getNombre() + 
                    "' porque su predecesora '" + predecesora.getNombre() + "' no está en ejecución ni completada.");
        }
    }

    @Override
    public int getLeadLag() {
        return leadLag;
    }
}

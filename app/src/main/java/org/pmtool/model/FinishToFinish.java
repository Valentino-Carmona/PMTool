package org.pmtool.model;

import java.time.LocalDate;
import java.util.Objects;

public class FinishToFinish implements IDependencia {
    private final Actividad predecesora;
    private final int leadLag;

    public FinishToFinish(Actividad predecesora, int leadLag) {
        this.predecesora = Objects.requireNonNull(predecesora, "La predecesora no puede ser nula");
        this.leadLag = leadLag;
    }

    @Override
    public LocalDate calcularInicioDependiente(int duracionDias) {
        LocalDate fin = calcularFinDependiente(duracionDias);
        return fin.minusDays(duracionDias);
    }

    @Override
    public LocalDate calcularFinDependiente(int duracionDias) {
        return this.predecesora.calcularFechaDependienteFin(leadLag);
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

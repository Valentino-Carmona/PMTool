package org.pmtool.model;

import java.time.LocalDate;

public interface Dependencia {
    LocalDate calcularInicioDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias);
    LocalDate calcularFinDependiente(LocalDate inicioPredecesora, LocalDate finPredecesora, int duracionDias);
    Actividad getPredecesora();
    void verificarActivacion(Actividad actividad);
    int getLeadLag();
}

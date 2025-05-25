package org.pmtool.model;

import java.time.LocalDate;

public interface IDependencia {
    LocalDate calcularInicioDependiente(int duracionDias);
    LocalDate calcularFinDependiente(int duracionDias);
    Actividad getPredecesora();
    void verificarActivacion(Actividad actividad);
    int getLeadLag();
}

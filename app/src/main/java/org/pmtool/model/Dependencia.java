package org.pmtool.model;

import java.time.LocalDate;

public interface Dependencia {
    LocalDate calcularInicioDependiente(int duracionDias);
    Actividad getPredecesora();
    void verificarActivacion(Actividad actividad);
    int getLeadLag();
}

package org.pmtool.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class StartToStartTest {

    @Test
    void testCalcularInicioDependiente() {
        Actividad predecesora = new Actividad("1", "Predecesora", 5);
        predecesora.calcularFechasPlanificadas(LocalDate.of(2025, 4, 1));

        StartToStart dependencia = new StartToStart(predecesora, 3);
        LocalDate fechaInicio = dependencia.calcularInicioDependiente(3);

        Assertions.assertEquals(LocalDate.of(2025, 4, 4), fechaInicio);
    }

    @Test
    void testNoActivarActividadConPredecesoraIncompleta() {
        Actividad predecesora = new Actividad("1", "Predecesora", 5);
        Actividad actividad = new Actividad("2", "Dependiente", 3);

        actividad.setDependencia(new StartToStart(predecesora, 0));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            actividad.activar();
        });
    }
}

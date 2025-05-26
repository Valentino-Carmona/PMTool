package org.pmtool.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class StartToFinishTest {

    @Test
    void testCalcularFinDependiente() {
        Actividad predecesora = new Actividad("1", "Predecesora", 5);
        predecesora.calcularFechasPlanificadas(LocalDate.of(2025, 4, 1));

        StartToFinish dependencia = new StartToFinish(predecesora, 2);
        LocalDate fechaFin = dependencia.calcularFinDependiente(3);

        Assertions.assertEquals(LocalDate.of(2025, 4, 4), fechaFin);
    }

    @Test
    void testNoActivarActividadConPredecesoraIncompleta() {
        Actividad predecesora = new Actividad("1", "Predecesora", 5);
        Actividad actividad = new Actividad("2", "Dependiente", 3);

        actividad.setDependencia(new StartToFinish(predecesora, 0));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            actividad.activar();
        });
    }
}

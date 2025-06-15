package org.pmtool.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FinishToStartTest {

  @Test
  void testCalcularInicioDependiente() {
    Actividad predecesora = new Actividad("1", "Predecesora", 5);
    predecesora.calcularFechasPlanificadas(LocalDate.of(2025, 4, 1));

    FinishToStart dependencia = new FinishToStart(predecesora, 2);
    LocalDate fechaInicio = dependencia.calcularInicioDependiente(3);

    Assertions.assertEquals(LocalDate.of(2025, 4, 9), fechaInicio);
  }

  @Test
  void testNoActivarActividadConPredecesoraIncompleta() {
    Actividad predecesora = new Actividad("1", "Predecesora", 5);
    Actividad actividad = new Actividad("2", "Dependiente", 3);

    actividad.setDependencia(new FinishToStart(predecesora, 0));

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          actividad.activar();
        });
  }
}

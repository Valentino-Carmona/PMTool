package org.pmtool.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pmtool.model.Actividad;
import org.pmtool.model.FinishToStart;

public class ValidadorCiclosDependenciasTest {

  @Test
  void testConstructor() {
    // Para cubrir el constructor utilitario por defecto y evitar la fuga de cobertura.
    ValidadorCiclosDependencias validador = new ValidadorCiclosDependencias();
    Assertions.assertNotNull(validador);
  }

  @Test
  void testNoExisteCicloEnGrafoComplejo() {
    Actividad a1 = new Actividad("A1", "1", 5);
    Actividad a2 = new Actividad("A2", "2", 5);
    Actividad a3 = new Actividad("A3", "3", 5);

    a2.setDependencia(new FinishToStart(a1, 0));
    a3.setDependencia(new FinishToStart(a2, 0));

    Assertions.assertDoesNotThrow(() -> {
      ValidadorCiclosDependencias.validarSinCiclos(a3, new FinishToStart(a1, 0));
    });
  }

  @Test
  void testExisteCicloLanzaExcepcion() {
    Actividad a1 = new Actividad("A1", "1", 5);
    Actividad a2 = new Actividad("A2", "2", 5);

    a2.setDependencia(new FinishToStart(a1, 0));

    IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
      ValidadorCiclosDependencias.validarSinCiclos(a1, new FinishToStart(a2, 0));
    });

    Assertions.assertEquals("La dependencia genera un ciclo", ex.getMessage());
  }
}

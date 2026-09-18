package org.pmtool.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

  @Test
  void testMainEjecutaSinErrores() {
    // Al probar la ejecución del Main, validamos que los ejemplos integrados funcionen
    // sin arrojar excepciones (NullPointerException, StateException, etc.)
    // Esto provee un smoke test (prueba de humo) valioso y eleva radicalmente la cobertura de las líneas procedimentales.
    assertDoesNotThrow(() -> {
      Main.main(new String[]{});
    }, "La ejecución de Main.main() no debería lanzar ninguna excepción.");
  }
}

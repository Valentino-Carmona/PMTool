package org.pmtool.model.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DuracionTest {

  @Test
  void testCreacionValida() {
    Duracion d1 = Duracion.of(5);
    assertEquals(5, d1.getDias());
  }

  @Test
  void testCreacionDuracionCero() {
    Duracion d = Duracion.of(0);
    assertEquals(0, d.getDias());
  }

  @Test
  void testCreacionDuracionNegativa() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      Duracion.of(-1);
    });
    assertEquals("La duración debe ser mayor o igual a cero.", exception.getMessage());
  }

  @Test
  void testEqualsYHashCode() {
    Duracion d1 = Duracion.of(5);
    Duracion d2 = Duracion.of(5);
    Duracion d3 = Duracion.of(10);

    assertEquals(d1, d2);
    assertEquals(d1.hashCode(), d2.hashCode());
    assertNotEquals(d1, d3);
    assertNotEquals(d1, null);
    assertNotEquals(d1, new Object());
  }

  @Test
  void testToString() {
    Duracion d = Duracion.of(5);
    assertEquals("5 días", d.toString());
  }
}

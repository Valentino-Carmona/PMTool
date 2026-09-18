package org.pmtool.model.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumeroEDTTest {

  @Test
  void testCreacionValida() {
    NumeroEDT edt = NumeroEDT.of("1.1");
    assertEquals("1.1", edt.getValor());
  }

  @Test
  void testCreacionInvalidaNuloOVacio() {
    IllegalArgumentException exceptionNull = assertThrows(IllegalArgumentException.class, () -> {
      NumeroEDT.of(null);
    });
    assertEquals("El número EDT no puede ser nulo o vacío.", exceptionNull.getMessage());

    IllegalArgumentException exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> {
      NumeroEDT.of("   ");
    });
    assertEquals("El número EDT no puede ser nulo o vacío.", exceptionEmpty.getMessage());
  }

  @Test
  void testGenerarSubNivel() {
    NumeroEDT edtPadre = NumeroEDT.of("1.1");
    NumeroEDT edtHijo = edtPadre.generarSubNivel(2);
    assertEquals("1.1.2", edtHijo.getValor());
  }

  @Test
  void testEqualsYHashCode() {
    NumeroEDT e1 = NumeroEDT.of("1.1");
    NumeroEDT e2 = NumeroEDT.of("1.1");
    NumeroEDT e3 = NumeroEDT.of("1.2");

    assertEquals(e1, e2);
    assertEquals(e1.hashCode(), e2.hashCode());
    assertNotEquals(e1, e3);
    assertNotEquals(e1, null);
    assertNotEquals(e1, new Object());
  }

  @Test
  void testToString() {
    NumeroEDT edt = NumeroEDT.of("2.5");
    assertEquals("2.5", edt.toString());
  }
}

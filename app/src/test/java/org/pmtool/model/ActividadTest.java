package org.pmtool.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActividadTest {
  private Actividad actividad;

  @BeforeEach
  void setUp() {
    actividad = new Actividad("1.1", "Actividad Test", 40);
  }

  @Test
  void testCreacionActividad() {
    Assertions.assertNotNull(actividad);
    Assertions.assertEquals("1.1", actividad.getNumeroEDT());
    Assertions.assertEquals("Actividad Test", actividad.getNombre());
    Assertions.assertEquals(40, actividad.getDuracionDias());
    Assertions.assertTrue(actividad.isPlanificada());
  }

  @Test
  void testPlanificarFechas() {
    LocalDate inicio = LocalDate.of(2024, 1, 1);
    LocalDate fin = LocalDate.of(2024, 2, 10);

    actividad.calcularFechasPlanificadas(inicio);

    Assertions.assertEquals(inicio, actividad.getFechaInicioPlanificada());
    Assertions.assertEquals(fin, actividad.getFechaFinPlanificada());
  }

  @Test
  void testAgregarSubactividad() {
    Actividad subactividad = actividad.agregarSubactividad("Subactividad", 20);

    Assertions.assertEquals(1, actividad.getSubactividades().size());
    Assertions.assertEquals("1.1.1", subactividad.getNumeroEDT());
  }

  @Test
  void testActivarActividad() {
    actividad.activar();

    Assertions.assertTrue(actividad.isEnEjecucion());
    Assertions.assertNotNull(actividad.getFechaInicioReal());
    Assertions.assertTrue(actividad.isEnEjecucion());
  }

  @Test
  void testDesactivarActividad() {
    actividad.activar();
    actividad.desactivar();

    Assertions.assertTrue(actividad.isCompletada());
    Assertions.assertNotNull(actividad.getFechaFinReal());
    Assertions.assertTrue(actividad.isCompletada());
  }

  @Test
  void testActivarActividadCompletada() {
    actividad.activar();
    actividad.desactivar();

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          actividad.activar();
        });
  }

  @Test
  void testDesactivarActividadNoEnEjecucion() {
    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          actividad.desactivar();
        });
  }

  @Test
  void testAgregarSubactividadAActividadCompletada() {
    actividad.activar();
    actividad.desactivar();

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          actividad.agregarSubactividad("Subactividad", 20);
        });
  }

  @Test
  void testCalcularFechasPlanificadasConDependenciaCorrectamente() {
    Actividad predecesora = new Actividad("1", "Predecesora", 5);
    predecesora.calcularFechasPlanificadas(LocalDate.of(2025, 4, 1));

    actividad.setDependencia(new FinishToStart(predecesora, 2));
    actividad.calcularFechasPlanificadas(LocalDate.of(2025, 4, 1));

    Assertions.assertEquals(LocalDate.of(2025, 4, 9), actividad.getFechaInicioPlanificada());
    Assertions.assertEquals(LocalDate.of(2025, 5, 19), actividad.getFechaFinPlanificada());
  }

  @Test
  void testNoActivarActividadConDependenciaPredecesoraNoFinalizada() {
    Actividad predecesora = new Actividad("1", "Predecesora", 5);
    actividad.setDependencia(new FinishToStart(predecesora, 0));

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          actividad.activar();
        });
  }

  @Test
  void testToStringYAccesorios() {
    actividad.setNombre("Nuevo Nombre");
    Assertions.assertEquals("Nuevo Nombre", actividad.getNombre());
    
    actividad.setFechaInicioReal(LocalDate.of(2025, 1, 1));
    actividad.setFechaFinReal(LocalDate.of(2025, 1, 10));
    
    Assertions.assertEquals(LocalDate.of(2025, 1, 1), actividad.getFechaInicioReal());
    Assertions.assertEquals(LocalDate.of(2025, 1, 10), actividad.getFechaFinReal());
    
    String toString = actividad.toString();
    Assertions.assertTrue(toString.contains("Nuevo Nombre"));
    Assertions.assertTrue(toString.contains("1.1"));
  }

  @Test
  void testActivarActividadYaEnEjecucion() {
    actividad.activar();
    Assertions.assertThrows(IllegalStateException.class, () -> {
      actividad.activar();
    });
  }

  @Test
  void testDesactivarActividadYaCompletada() {
    actividad.activar();
    actividad.desactivar();
    Assertions.assertThrows(IllegalStateException.class, () -> {
      actividad.desactivar();
    });
  }

  @Test
  void testIsCompletadaRecursivo() {
    Actividad sub1 = actividad.agregarSubactividad("Sub1", 5);
    actividad.activar();
    sub1.activar();
    
    // Como tiene subactividades, no está completada hasta que todas las sub lo estén.
    Assertions.assertFalse(actividad.isCompletada());
    
    sub1.desactivar();
    // Aún no hemos desactivado la padre
    Assertions.assertFalse(actividad.isCompletada());
    
    actividad.desactivar();
    Assertions.assertTrue(actividad.isCompletada());
  }

  @Test
  void testCalcularFechasPlanificadasSinInicioValido() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      actividad.calcularFechasPlanificadas(null);
    });
  }
}

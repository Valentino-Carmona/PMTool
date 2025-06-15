package org.pmtool.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProyectoTest {
  private Proyecto proyecto;

  @BeforeEach
  void setUp() {
    proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
  }

  @Test
  void testCreacionProyecto() {
    Assertions.assertNotNull(proyecto);
    Assertions.assertEquals("Proyecto Test", proyecto.getNombre());
    Assertions.assertTrue(proyecto.isPlanificado());
    Assertions.assertTrue(proyecto.getTotalHorasEstimadas() > 0);
    Assertions.assertTrue(proyecto.getPresupuesto() > 0);
  }

  @Test
  void testAgregarActividad() {
    Actividad actividad = proyecto.agregarActividad("Actividad Test", 40);

    Assertions.assertEquals(1, proyecto.getActividades().size());
    Assertions.assertTrue(proyecto.getActividades().contains(actividad));
    Assertions.assertEquals("1", actividad.getNumeroEDT());
  }

  @Test
  void testActividadJerarquia() {
    Actividad actividad = proyecto.agregarActividad("Actividad Principal", 40);
    Actividad subactividad = actividad.agregarSubactividad("Subactividad", 20);

    Assertions.assertEquals(1, actividad.getSubactividades().size());
    Assertions.assertEquals("1.1", subactividad.getNumeroEDT());
    Assertions.assertEquals(subactividad, actividad.getSubactividades().get(0));
  }

  @Test
  void testFinalizarProyecto() {
    Actividad actividad = proyecto.agregarActividad("Actividad Test", 40);
    proyecto.planificarProyecto(LocalDate.of(2025, 9, 1));

    // Activar y desactivar la actividad
    actividad.activar();
    actividad.desactivar();

    // Finalizar el proyecto
    proyecto.finalizar();

    Assertions.assertEquals(Proyecto.EstadoProyecto.FINALIZADO, proyecto.getEstado());
  }

  @Test
  void testFinalizarProyectoConActividadIncompleta() {
    proyecto.agregarActividad("Actividad Test", 40);
    proyecto.planificarProyecto(LocalDate.now());

    // Intentar finalizar el proyecto sin completar la actividad
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          proyecto.finalizar();
        });
  }

  @Test
  void testNoAgregarActividadAProyectoFinalizado() {
    proyecto.planificarProyecto(LocalDate.now());
    proyecto.finalizar();

    Assertions.assertThrows(
        IllegalStateException.class,
        () -> {
          proyecto.agregarActividad("Nueva Actividad", 20);
        });
  }

  @Test
  void testPlanificarProyectoSinFechaInicio() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          proyecto.planificarProyecto(null);
        });
  }

  @Test
  void testFinalizarProyectoSinActividades() {
    proyecto.planificarProyecto(LocalDate.now());
    proyecto.agregarActividad("Nueva Actividad", 20);

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          proyecto.finalizar();
        });
  }
}

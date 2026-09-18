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

  @Test
  void testToStringYAccesorios() {
    proyecto.setNombre("Renombrado");
    proyecto.setTotalHorasEstimadas(200);
    proyecto.setPresupuesto(10000.0);
    proyecto.setFechaInicioPlanificada(LocalDate.of(2025, 1, 1));
    proyecto.setFechaFinPlanificada(LocalDate.of(2025, 2, 1));
    proyecto.setFechaFinReal(LocalDate.of(2025, 3, 1));

    Assertions.assertEquals("Renombrado", proyecto.getNombre());
    Assertions.assertEquals(200, proyecto.getTotalHorasEstimadas());
    Assertions.assertEquals(10000.0, proyecto.getPresupuesto());
    Assertions.assertEquals(LocalDate.of(2025, 1, 1), proyecto.getFechaInicioPlanificada());
    Assertions.assertEquals(LocalDate.of(2025, 2, 1), proyecto.getFechaFinPlanificada());
    Assertions.assertNull(proyecto.getFechaInicioReal()); // Depende de las actividades
    Assertions.assertEquals(LocalDate.of(2025, 3, 1), proyecto.getFechaFinReal());

    String toString = proyecto.toString();
    Assertions.assertTrue(toString.contains("Renombrado"));
  }

  @Test
  void testEstadosYValidaciones() {
    Assertions.assertTrue(proyecto.igualNombre("Proyecto Test"));
    Assertions.assertFalse(proyecto.igualNombre("Otro"));
    
    // Inicia planificado
    Assertions.assertFalse(proyecto.isEnCurso());
    Assertions.assertFalse(proyecto.isFinalizado());
    
    proyecto.planificarProyecto(LocalDate.of(2025, 1, 1));
    Assertions.assertTrue(proyecto.isEnCurso());
    
    // Si no hay actividades y forzamos finalizar (omitiendo la restricción o probando directamente)
    // El test de finalizacion ya se encarga, así que solo probamos estado en curso
  }

  @Test
  void testPlanificarProyectoConActividadesDependientes() {
    Actividad a1 = proyecto.agregarActividad("A1", 10);
    Actividad a2 = proyecto.agregarActividad("A2", 5);
    a2.setDependencia(new FinishToStart(a1, 2));

    proyecto.planificarProyecto(LocalDate.of(2025, 1, 1));

    Assertions.assertEquals(LocalDate.of(2025, 1, 1), proyecto.getFechaInicioPlanificada());
    // A1 termina el 2025-01-11. A2 empieza el 2025-01-13 + 1. Y termina el 19.
    Assertions.assertEquals(LocalDate.of(2025, 1, 19), proyecto.getFechaFinPlanificada());
  }
}

package org.pmtool.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad;
import java.time.LocalDate;

public class GerenteProyectoTest {
    private GerenteProyecto gerente;
    private Proyecto proyecto;

    @BeforeEach
    void setUp() {
        gerente = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto Test", 200, 75000.0);
    }

    @Test
    void testPlanificarProyecto() {
        LocalDate inicio = LocalDate.of(2024, 1, 1);
        
        gerente.planificarProyecto(proyecto, inicio);        
        Assertions.assertEquals(Proyecto.EstadoProyecto.EN_CURSO, proyecto.getEstado());
    }

    @Test
    void testCrearActividad() {
        Actividad actividad = proyecto.agregarActividad( "Nueva Actividad", 80);
        
        Assertions.assertNotNull(actividad);
        Assertions.assertEquals("1", actividad.getNumeroEDT());
        Assertions.assertEquals("Nueva Actividad", actividad.getNombre());
    }

    @Test
    void testConfigurarDependenciaConAdelanto() {
        Actividad actividadA = proyecto.agregarActividad( "Actividad A", 5);
        Actividad actividadB = proyecto.agregarActividad( "Actividad B", 3);

        gerente.configurarDependencia(actividadB, actividadA, -1);

        Assertions.assertNotNull(actividadB.getDependencia());
        Assertions.assertEquals(-1, actividadB.getDependencia().getLeadLag());
    }
} 

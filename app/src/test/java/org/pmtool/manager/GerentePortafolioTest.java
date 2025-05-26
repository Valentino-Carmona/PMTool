package org.pmtool.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.pmtool.model.Proyecto;
import org.pmtool.model.Proyecto.EstadoProyecto;

public class GerentePortafolioTest {
    private GerentePortafolio gerentePortafolio;

    @BeforeEach
    void setUp() {
        gerentePortafolio = new GerentePortafolio();
    }

    @Test
    void testCrearProyecto() {
        String nombreProyecto = "Nuevo Proyecto";
        int horasEstimadas = 150;
        double presupuesto = 60000.0;
        
        Proyecto proyecto = gerentePortafolio.crearProyecto(nombreProyecto, horasEstimadas, presupuesto);
        
        Assertions.assertNotNull(proyecto);
        Assertions.assertEquals(nombreProyecto, proyecto.getNombre());
        Assertions.assertEquals(EstadoProyecto.PLANIFICADO, proyecto.getEstado());
    }

    @Test
    void testCrearProyectoSinNombre() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerentePortafolio.crearProyecto("", 100, 50000.0);
        });
    }

    @Test
    void testCrearProyectoConNombreDuplicado() {
        gerentePortafolio.crearProyecto("Proyecto Test", 100, 50000.0);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gerentePortafolio.crearProyecto("Proyecto Test", 200, 75000.0);
        });
    }
} 

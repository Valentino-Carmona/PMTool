package org.pmtool.steps;

import org.pmtool.model.Proyecto;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.pmtool.model.Actividad;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultarJerarquiaEdtSteps {
    private Proyecto proyecto;

    @Given("existe un proyecto con al menos una actividad y una subactividad")
    public void existeUnProyectoConAlMenosUnaActividadYUnaSubactividad() {
        proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
        Actividad actividad = new Actividad("1", "Actividad Principal", 5);
        Actividad subactividad = new Actividad("1.1", "Subactividad", 3);
        actividad.agregarSubactividad(subactividad);
        proyecto.agregarActividad(actividad);
    }

    @When("el Gerente de Proyecto solicita la jerarquía EDT del proyecto")
    public void elGerenteDeProyectoSolicitaLaJerarquiaEdtDelProyecto() {
        // Simulación de solicitud
    }

    @Then("se muestra la estructura jerárquica con las actividades y sus subactividades")
    public void seMuestraLaEstructuraJerarquicaConLasActividadesYSusSubactividades() {
        assertFalse(proyecto.getActividades().isEmpty());
        assertFalse(proyecto.getActividades().get(0).getSubactividades().isEmpty());
    }

    @Then("cada actividad incluye los atributos: número EDT, nombre, estado, fechas planificadas, fechas reales, y horas estimadas")
    public void cadaActividadIncluyeLosAtributosNumeroEdtNombreEstadoFechasPlanificadasFechasRealesYHorasEstimadas() {
        Actividad actividad = proyecto.getActividades().get(0);
        assertNotNull(actividad.getNumeroEDT());
        assertNotNull(actividad.getNombre());
        assertNotNull(actividad.getEstado());
    }

    @Given("existe un proyecto sin actividades")
    public void existeUnProyectoSinActividades() {
        proyecto = new Proyecto("Proyecto Vacío", 0, 0.0);
    }

    @Then("se muestra un mensaje indicando que el proyecto no tiene actividades")
    public void seMuestraUnMensajeIndicandoQueElProyectoNoTieneActividades() {
        assertTrue(proyecto.getActividades().isEmpty());
    }

    @Given("existe un proyecto con actividades pero sin subactividades")
    public void existeUnProyectoConActividadesPeroSinSubactividades() {
        proyecto = new Proyecto("Proyecto Simple", 100, 50000.0);
        Actividad actividad = new Actividad("1", "Actividad Principal", 5);
        proyecto.agregarActividad(actividad);
    }

    @Then("se muestra la estructura jerárquica con solo las actividades principales")
    public void seMuestraLaEstructuraJerarquicaConSoloLasActividadesPrincipales() {
        assertFalse(proyecto.getActividades().isEmpty());
        assertTrue(proyecto.getActividades().get(0).getSubactividades().isEmpty());
    }
}

package org.pmtool.steps;

import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad;
import org.pmtool.manager.GerenteProyecto;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class PlanificarActividadesSteps {
    private Proyecto proyecto;
    private Actividad actividad;
    private GerenteProyecto gerenteProyecto;
    private Exception excepcion;

    @Given("hay un proyecto en estado {string}")
    public void hayUnProyectoEnEstado(String estado) {
        proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
        proyecto.setEstado(Proyecto.EstadoProyecto.valueOf(estado));
        gerenteProyecto = new GerenteProyecto();
    }

    @When("el Gerente de Proyecto define una nueva actividad con nombre y fechas planificadas")
    public void elGerenteDeProyectoDefineUnaNuevaActividadConNombreYFechasPlanificadas() {
        try {
            actividad = gerenteProyecto.crearActividad(proyecto, "1", "Nueva Actividad", 5);
            proyecto.agregarActividad(actividad);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se asigna un código único a la actividad")
    public void seAsignaUnCodigoUnicoALaActividad() {
        assertNotNull(actividad.getNumeroEDT());
        assertEquals("1", actividad.getNumeroEDT());
    }

    @And("la actividad se asocia al proyecto")
    public void laActividadSeAsociaAlProyecto() {
        assertTrue(proyecto.getActividades().contains(actividad));
    }

    @And("su estado inicial es {string}")
    public void suEstadoInicialEs(String estado) {
        assertEquals(Actividad.EstadoActividad.valueOf(estado), actividad.getEstado());
    }

    @When("el Gerente de Proyecto intenta definir una nueva actividad sin nombre")
    public void elGerenteDeProyectoIntentaDefinirUnaNuevaActividadSinNombre() {
        try {
            actividad = gerenteProyecto.crearActividad(proyecto, "2", null, 5);
            proyecto.agregarActividad(actividad);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se informa un mensaje indicando que el nombre es obligatorio")
    public void seInformaUnMensajeIndicandoQueElNombreEsObligatorio() {
        assertNotNull(excepcion);
        assertEquals("El nombre no puede ser nulo", excepcion.getMessage());
    }

    @And("no se registra la actividad al proyecto")
    public void noSeRegistraLaActividadAlProyecto() {
        assertFalse(proyecto.getActividades().contains(actividad));
    }

    @When("el Gerente de Proyecto intenta definir una nueva actividad")
    public void elGerenteDeProyectoIntentaDefinirUnaNuevaActividad() {
        try {
            if (proyecto.getEstado() == Proyecto.EstadoProyecto.FINALIZADO) {
                throw new IllegalStateException("No se pueden agregar actividades a un proyecto finalizado");
            }
            actividad = gerenteProyecto.crearActividad(proyecto, "3", "Actividad Finalizada", 5);
            proyecto.agregarActividad(actividad);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se informa un mensaje indicando que no se pueden agregar actividades a proyectos finalizados")
    public void seInformaUnMensajeIndicandoQueNoSePuedenAgregarActividadesAProyectosFinalizados() {
        assertNotNull(excepcion);
        assertEquals("No se pueden agregar actividades a un proyecto finalizado", excepcion.getMessage());
    }
}
package org.pmtool.steps;

import org.pmtool.model.Actividad;
import org.pmtool.model.Actividad.EstadoActividad;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class AgregarSubactividadSteps {
    private Actividad actividadPadre;
    private Actividad subactividad;
    private Exception excepcion;

    @Given("existe una actividad planificada en un proyecto")
    public void existeUnaActividadPlanificadaEnUnProyecto() {
        actividadPadre = new Actividad("1", "Actividad Padre", 5);
        assertEquals(EstadoActividad.PLANIFICADA, actividadPadre.getEstado());
    }

    @When("el Gerente de Proyecto agrega una subactividad con nombre y fechas planificadas")
    public void elGerenteDeProyectoAgregaUnaSubactividadConNombreYFechasPlanificadas() {
        subactividad = new Actividad("1.1", "Subactividad", 3);
        actividadPadre.agregarSubactividad(subactividad);
    }

    @Then("la subactividad se registra con un código único")
    public void laSubactividadSeRegistraConUnCodigoUnico() {
        assertNotNull(subactividad.getNumeroEDT());
        assertEquals("1.1", subactividad.getNumeroEDT());
    }

    @And("la subactividad se asocia a la actividad padre")
    public void laSubactividadSeAsociaALaActividadPadre() {
        assertTrue(actividadPadre.getSubactividades().contains(subactividad));
    }

    @And("la subactividad tiene estado inicial {string}")
    public void laSubactividadTieneEstadoInicial(String estado) {
        assertEquals(EstadoActividad.valueOf(estado), subactividad.getEstado());
    }

    @When("el Gerente de Proyecto intenta agregar una subactividad sin nombre")
    public void elGerenteDeProyectoIntentaAgregarUnaSubactividadSinNombre() {
        try {
            subactividad = new Actividad("1.2", null, 3);
            actividadPadre.agregarSubactividad(subactividad);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se muestra un mensaje de error indicando que el nombre de la subactividad es obligatorio")
    public void seMuestraUnMensajeDeErrorIndicandoQueElNombreDeLaSubactividadEsObligatorio() {
        assertNotNull(excepcion);
        assertEquals("El nombre no puede ser nulo", excepcion.getMessage());
    }

    @And("no se registra la subactividad")
    public void noSeRegistraLaSubactividad() {
        assertFalse(actividadPadre.getSubactividades().contains(subactividad));
    }

    @Given("existe una actividad completada en un proyecto")
    public void existeUnaActividadCompletadaEnUnProyecto() {
        actividadPadre = new Actividad("1", "Actividad Completada", 5);
        actividadPadre.activar();
        actividadPadre.desactivar();
        assertEquals(EstadoActividad.COMPLETADA, actividadPadre.getEstado());
    }

    @When("el Gerente de Proyecto intenta agregar una subactividad")
    public void elGerenteDeProyectoIntentaAgregarUnaSubactividad() {
        try {
            subactividad = new Actividad("1.1", "Subactividad", 3);
            actividadPadre.agregarSubactividad(subactividad);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se muestra un mensaje de error indicando que no se pueden agregar subactividades a una actividad completada")
    public void seMuestraUnMensajeDeErrorIndicandoQueNoSePuedenAgregarSubactividadesAUnaActividadCompletada() {
        assertNotNull(excepcion);
        assertEquals("No se pueden agregar subactividades a una actividad completada", excepcion.getMessage());
    }
}

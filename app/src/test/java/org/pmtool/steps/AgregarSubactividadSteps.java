package org.pmtool.steps;

import org.pmtool.manager.GerenteProyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad.EstadoActividad;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class AgregarSubactividadSteps {
    private GerenteProyecto gerenteProyecto;
    private Proyecto proyecto;
    private Actividad actividadPadre;
    private Actividad subactividad;
    private Exception excepcion;

    @Before
    public void setup() {
        gerenteProyecto = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto Ejemplo", 500, 100000);
        actividadPadre = null;
        subactividad = null;
        excepcion = null;
    }

    @Given("existe una actividad en un proyecto")
    public void existeUnaActividadEnUnProyecto() {
        actividadPadre = gerenteProyecto.crearActividad(proyecto, "Actividad Padre", 5);
        assertEquals(EstadoActividad.PLANIFICADA, actividadPadre.getEstado());
    }

    @When("el Gerente de Proyecto agrega una subactividad con nombre y dias estimados")
    public void elGerenteDeProyectoAgregaUnaSubactividadConNombreYDiasEstimados() {
        subactividad = gerenteProyecto.crearSubActividad(actividadPadre, "Subactividad", 3);
        assertNotNull(subactividad);
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
            gerenteProyecto.crearSubActividad(actividadPadre, null, 3);
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
        actividadPadre = gerenteProyecto.crearActividad(proyecto, "Actividad Completada", 5);
        actividadPadre.activar();
        actividadPadre.desactivar();
        assertEquals(EstadoActividad.COMPLETADA, actividadPadre.getEstado());
    }

    @When("el Gerente de Proyecto intenta agregar una subactividad")
    public void elGerenteDeProyectoIntentaAgregarUnaSubactividad() {
        try {
            gerenteProyecto.crearSubActividad(actividadPadre, "Subactividad", 3);
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

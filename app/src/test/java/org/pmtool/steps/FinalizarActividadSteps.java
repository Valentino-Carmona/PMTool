package org.pmtool.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.pmtool.model.Actividad;
import org.pmtool.model.Actividad.EstadoActividad;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FinalizarActividadSteps {
    private Actividad actividad;
    private Exception exception;

    @Given("existe una actividad en estado {string}")
    public void existeUnaActividadEnEstado(String estado) {
        actividad = new Actividad("1", "Actividad", 5);
        if (estado.equals("EN_EJECUCION")) {
            actividad.activar();
        }
        assertEquals(EstadoActividad.valueOf(estado), actividad.getEstado());
    }

    @When("el Gerente de Proyecto informa la finalización de la actividad")
    public void elGerenteDeProyectoInformaLaFinalizacionDeLaActividad() {
        try {
            actividad.desactivar();
        } catch (IllegalStateException e) {
            exception = e;
        }
    }

    @Then("el estado de la actividad cambia a {string}")
    public void elEstadoDeLaActividadCambiaA(String estado) {
        assertEquals(EstadoActividad.valueOf(estado), actividad.getEstado());
    }

    @And("la fecha real de finalización se actualizo automaticamente")
    public void laFechaRealDeFinalizacionSeActualizoAutomaticamente() {
        assertNotNull(actividad.getFechaFinReal());
    }

    @When("el Gerente de Proyecto intenta modificar el estado a {string}")
    public void elGerenteDeProyectoIntentaModificarElEstadoA(String estado) {
        try {
            if (estado.equals("COMPLETADA")) {
                actividad.desactivar();
            }
        } catch (IllegalStateException e) {
            exception = e;
        }
    }

    @Then("se informa un mensaje indicando que la actividad debe estar EN_EJECUCION para poder finalizarla")
    public void seInformaUnMensajeIndicandoQueLaActividadDebeEstarEnEjecucionParaPoderFinalizarla() {
        assertNotNull(exception);
        assertEquals("No se puede desactivar una actividad que no está en ejecución.", exception.getMessage());
    }

    @And("la actividad mantiene su estado en {string}")
    public void laActividadMantieneSuEstadoEn(String estado) {
        assertEquals(EstadoActividad.valueOf(estado), actividad.getEstado());
    }
}

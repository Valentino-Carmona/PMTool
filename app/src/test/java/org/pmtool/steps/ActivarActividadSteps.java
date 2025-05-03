package org.pmtool.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class ActivarActividadSteps {
    
    @Given("existe una actividad en estado {string}")
    public void existeUnaActividadEnEstado(String estado) {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto activa la actividad")
    public void elGerenteDeProyectoActivaLaActividad() {
        // Implementación pendiente
    }

    @Then("el estado de la actividad cambia a {string}")
    public void elEstadoDeLaActividadCambiaA(String estado) {
        // Implementación pendiente
    }

    @And("se registra la fecha real de inicio")
    public void seRegistraLaFechaRealDeInicio() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto intenta activar la actividad")
    public void elGerenteDeProyectoIntentaActivarLaActividad() {
        // Implementación pendiente
    }

    @Then("se informa un mensaje indicando que no se puede activar una actividad completada")
    public void seInformaUnMensajeIndicandoQueNoSePuedeActivarUnaActividadCompletada() {
        // Implementación pendiente
    }

    @Then("se informa un mensaje indicando que la actividad ya está en ejecución")
    public void seInformaUnMensajeIndicandoQueLaActividadYaEstaEnEjecucion() {
        // Implementación pendiente
    }
}

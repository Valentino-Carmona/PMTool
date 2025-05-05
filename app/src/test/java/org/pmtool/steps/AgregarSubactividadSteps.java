package org.pmtool.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class AgregarSubactividadSteps {
    
    @Given("existe una actividad planificada en un proyecto")
    public void existeUnaActividadPlanificadaEnUnProyecto() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto agrega una subactividad con nombre y fechas planificadas")
    public void elGerenteDeProyectoAgregaUnaSubactividadConNombreYFechasPlanificadas() {
        // Implementación pendiente
    }

    @Then("la subactividad se registra con un código único")
    public void laSubactividadSeRegistraConUnCodigoUnico() {
        // Implementación pendiente
    }

    @And("la subactividad se asocia a la actividad padre")
    public void laSubactividadSeAsociaALaActividadPadre() {
        // Implementación pendiente
    }

    @And("la subactividad tiene estado inicial {string}")
    public void laSubactividadTieneEstadoInicial(String estado) {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto intenta agregar una subactividad sin nombre")
    public void elGerenteDeProyectoIntentaAgregarUnaSubactividadSinNombre() {
        // Implementación pendiente
    }

    @Then("se muestra un mensaje de error indicando que el nombre de la subactividad es obligatorio")
    public void seMuestraUnMensajeDeErrorIndicandoQueElNombreDeLaSubactividadEsObligatorio() {
        // Implementación pendiente
    }

    @And("no se registra la subactividad")
    public void noSeRegistraLaSubactividad() {
        // Implementación pendiente
    }

    @Given("existe una actividad completada en un proyecto")
    public void existeUnaActividadCompletadaEnUnProyecto() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto intenta agregar una subactividad")
    public void elGerenteDeProyectoIntentaAgregarUnaSubactividad() {
        // Implementación pendiente
    }

    @Then("se muestra un mensaje de error indicando que no se pueden agregar subactividades a una actividad completada")
    public void seMuestraUnMensajeDeErrorIndicandoQueNoSePuedenAgregarSubactividadesAUnaActividadCompletada() {
        // Implementación pendiente
    }
}

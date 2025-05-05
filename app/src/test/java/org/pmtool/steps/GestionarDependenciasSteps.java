package org.pmtool.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class GestionarDependenciasSteps {

    @Given("existe un proyecto con dos actividades planificadas")
    public void existeUnProyectoConDosActividadesPlanificadas() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo entre ellas")
    public void elGerenteDeProyectoDefineUnaDependenciaFinAComienzoEntreEllas() {
        // Implementación pendiente
    }

    @Then("la dependencia se registra correctamente")
    public void laDependenciaSeRegistraCorrectamente() {
        // Implementación pendiente
    }

    @And("la actividad dependiente no puede comenzar hasta que la primera termine")
    public void laActividadDependienteNoPuedeComenzarHastaQueLaPrimeraTermine() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de 2 días")
    public void elGerenteDeProyectoDefineUnaDependenciaFinAComienzoConRetraso() {
        // Implementación pendiente
    }

    @Then("la dependencia se registra con el retraso especificado")
    public void laDependenciaSeRegistraConElRetrasoEspecificado() {
        // Implementación pendiente
    }

    @And("la actividad dependiente no puede comenzar hasta 2 días después de que la primera termine")
    public void laActividadDependienteNoPuedeComenzarHastaDespuesDeQueLaPrimeraTermine() {
        // Implementación pendiente
    }

    @Given("existe un proyecto con una actividad completada y otra planificada")
    public void existeUnProyectoConUnaActividadCompletadaYOtraPlanificada() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto intenta definir una dependencia Fin-a-Comienzo")
    public void elGerenteDeProyectoIntentaDefinirUnaDependenciaFinAComienzo() {
        // Implementación pendiente
    }

    @Then("se muestra un mensaje de error indicando que no se pueden agregar dependencias a actividades completadas")
    public void seMuestraUnMensajeDeErrorIndicandoQueNoSePuedenAgregarDependencias() {
        // Implementación pendiente
    }
}

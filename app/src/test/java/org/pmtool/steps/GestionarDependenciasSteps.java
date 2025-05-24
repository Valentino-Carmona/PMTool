package org.pmtool.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class GestionarDependenciasSteps {

    @Given("existe un proyecto con dos actividades planificadas, A y B, donde A termina el {int} de abril y B no tiene fecha de inicio definida")
    public void existeProyectoConDosActividadesPlanificadasAB(int dia) {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo desde A hacia B")
    public void gerenteDefineDependenciaFinAComienzo() {
        // Implementación pendiente
    }

    @Then("la dependencia se registra correctamente")
    public void dependenciaSeRegistraCorrectamente() {
        // Implementación pendiente
    }

    @And("la fecha de inicio de B se establece como el {int} de abril")
    public void fechaInicioBEstablecida(int dia) {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de {int} días desde A hacia B")
    public void gerenteDefineDependenciaConRetraso(int dias) {
        // Implementación pendiente
    }

    @Then("la dependencia se registra correctamente con un retraso de {int} días")
    public void dependenciaConRetrasoSeRegistra(int dias) {
        // Implementación pendiente
    }

    @Given("existe un proyecto con una actividad A completada y otra actividad B planificada")
    public void proyectoConActividadCompletadaYPlanificada() {
        // Implementación pendiente
    }

    @When("el Gerente de Proyecto intenta definir una dependencia Fin-a-Comienzo desde B hacia A")
    public void gerenteIntentaDefinirDependenciaHaciaCompletada() {
        // Implementación pendiente
    }

    @Then("se muestra un mensaje de error indicando que no se pueden definir dependencias hacia actividades completadas")
    public void seMuestraErrorPorDependenciaCompletada() {
        // Implementación pendiente
    }
}

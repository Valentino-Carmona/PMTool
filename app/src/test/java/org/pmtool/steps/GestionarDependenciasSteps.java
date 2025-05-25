package org.pmtool.steps;

import org.pmtool.model.Actividad;
import org.pmtool.model.FinishToStart;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class GestionarDependenciasSteps {
    private Actividad actividadA;
    private Actividad actividadB;
    private Exception excepcion;

    @Given("existe un proyecto con dos actividades planificadas, A y B, donde A termina el {int} de abril y B no tiene fecha de inicio definida")
    public void existeProyectoConDosActividadesPlanificadasAB(int dia) {
        actividadA = new Actividad("1", "Actividad A", 5);
        actividadB = new Actividad("2", "Actividad B", 3);
        actividadA.setFechaFinPlanificada(LocalDate.of(2025, 4, dia));
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo desde A hacia B")
    public void gerenteDefineDependenciaFinAComienzo() {
        actividadB.setDependencia(new FinishToStart(actividadA, 0));
    }

    @Then("la dependencia se registra correctamente")
    public void dependenciaSeRegistraCorrectamente() {
        assertNotNull(actividadB.getDependencia());
        assertEquals(actividadA, actividadB.getDependencia().getPredecesora());
    }

    @And("la fecha de inicio de B se establece como el {int} de abril")
    public void fechaInicioBEstablecida(int dia) {
        LocalDate fechaInicioEsperada = LocalDate.of(2025, 4, dia);
        assertEquals(fechaInicioEsperada, actividadB.getDependencia().calcularInicioDependiente(actividadB.getDuracionDias()));
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de {int} días desde A hacia B")
    public void gerenteDefineDependenciaConRetraso(int dias) {
        actividadB.setDependencia(new FinishToStart(actividadA, dias));
    }

    @Then("la dependencia se registra correctamente con un retraso de {int} días")
    public void dependenciaConRetrasoSeRegistra(int dias) {
        assertEquals(dias, actividadB.getDependencia().getLeadLag());
    }

    @Given("existe un proyecto con una actividad A completada y otra actividad B planificada")
    public void proyectoConActividadCompletadaYPlanificada() {
        actividadA = new Actividad("1", "Actividad A", 5);
        actividadB = new Actividad("2", "Actividad B", 3);
        actividadA.activar();
        actividadA.desactivar();
        assertEquals(Actividad.EstadoActividad.COMPLETADA, actividadA.getEstado());
        assertEquals(Actividad.EstadoActividad.PLANIFICADA, actividadB.getEstado());
    }

    @When("el Gerente de Proyecto intenta definir una dependencia Fin-a-Comienzo desde B hacia A")
    public void gerenteIntentaDefinirDependenciaHaciaCompletada() {
        try {
            actividadA.setDependencia(new FinishToStart(actividadB, 0));
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se muestra un mensaje de error indicando que no se pueden definir dependencias hacia actividades completadas")
    public void seMuestraErrorPorDependenciaCompletada() {
        assertNotNull(excepcion);
        assertEquals("No se puede definir una dependencia para una actividad completada.", excepcion.getMessage());
    }
}

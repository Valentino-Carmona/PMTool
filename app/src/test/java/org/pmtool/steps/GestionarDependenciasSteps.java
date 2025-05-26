package org.pmtool.steps;

import org.pmtool.manager.GerenteProyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto;

import io.cucumber.java.Before;
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
    private GerenteProyecto gerenteProyecto;
    private Proyecto proyecto;

    @Before
    public void setup() {
        actividadA = null;
        actividadB = null;
        excepcion = null;
        gerenteProyecto = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto test", 100, 50000.0);
    }

    @Given("existe un proyecto con dos actividades planificadas A y B")
    public void existeProyectoConDosActividadesPlanificadasAB() {
        actividadA = gerenteProyecto.crearActividad(proyecto, "1", "Actividad A", 0);
        actividadB = gerenteProyecto.crearActividad(proyecto, "2", "Actividad B", 0);
        proyecto.agregarActividad(actividadA);
        proyecto.agregarActividad(actividadB);
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo desde A hacia B")
    public void gerenteDefineDependenciaFinAComienzo() {
        try {
            gerenteProyecto.configurarDependencia(actividadB, actividadA, "FS", 0);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un retraso de {int} días desde A hacia B")
    public void gerenteDefineDependenciaConRetraso(int dias) {
        try {
            gerenteProyecto.configurarDependencia(actividadB, actividadA, "FS", dias);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @When("el Gerente de Proyecto define una dependencia Fin-a-Comienzo con un adelanto de {int} día desde A hacia B")
    public void elGerenteDeProyectoDefineDependenciaConAdelanto(int dias) {
        try {
            gerenteProyecto.configurarDependencia(actividadB, actividadA, "FS", -dias);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @And("el Gerente de Proyecto define la fecha de inicio del proyecto en el {int} de abril")
    public void gerenteDefineFechaInicioProyecto(int dia) {
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 4, dia));
    }

    @Then("la dependencia se registra correctamente")
    public void dependenciaSeRegistraCorrectamente() {
        assertNotNull(actividadB.getDependencia());
        assertEquals(actividadA, actividadB.getDependencia().getPredecesora());
    }

    @Then("la dependencia se registra correctamente con un retraso de {int} días")
    public void dependenciaConRetrasoSeRegistra(int dias) {
        assertNotNull(actividadB.getDependencia());
        assertEquals(dias, actividadB.getDependencia().getLeadLag());
    }

    @Then("la dependencia se registra correctamente con un adelanto de {int} día")
    public void dependenciaConAdelantoSeRegistra(int dias) {
        assertNotNull(actividadB.getDependencia());
        assertEquals(-dias, actividadB.getDependencia().getLeadLag());
    }

    @And("la fecha de inicio de B se establece como el {int} de abril")
    public void fechaInicioBEstablecida(int dia) {
        LocalDate fechaInicioEsperada = LocalDate.of(2025, 4, dia);
        assertEquals(fechaInicioEsperada, actividadB.getFechaInicioPlanificada());
    }

    @Given("existe un proyecto con una actividad A completada y otra actividad B planificada")
    public void proyectoConActividadCompletadaYPlanificada() {
        actividadA = gerenteProyecto.crearActividad(proyecto, "1", "Actividad A", 5);
        actividadB = gerenteProyecto.crearActividad(proyecto, "2", "Actividad B", 3);
        proyecto.agregarActividad(actividadA);
        proyecto.agregarActividad(actividadB);
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 4, 1));
        actividadB.activar();
        actividadB.desactivar();
        assertEquals(Actividad.EstadoActividad.COMPLETADA, actividadB.getEstado());
        assertEquals(Actividad.EstadoActividad.PLANIFICADA, actividadA.getEstado());
    }

    @Then("se muestra un mensaje de error indicando que no se pueden definir dependencias hacia actividades completadas")
    public void seMuestraErrorPorDependenciaCompletada() {
        assertNotNull(excepcion);
        assertEquals("No se puede definir una dependencia para una actividad completada.", excepcion.getMessage());
    }
}
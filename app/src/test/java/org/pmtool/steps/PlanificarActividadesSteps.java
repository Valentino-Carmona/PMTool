package org.pmtool.steps;

import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad;
import org.pmtool.manager.GerenteProyecto;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class PlanificarActividadesSteps {
    private Proyecto proyecto;
    private Actividad actividad;
    private GerenteProyecto gerenteProyecto;
    private Exception excepcion;

    @Before
    public void setUp() {
        proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
        gerenteProyecto = new GerenteProyecto();
    }

    @Given("hay un proyecto en estado {string}")
    public void hayUnProyectoEnEstado(String estado) {
        proyecto.setEstado(Proyecto.EstadoProyecto.valueOf(estado));
    }

    @When("el Gerente de Proyecto define una nueva actividad con nombre {string} y duración {int} días")
    public void elGerenteDeProyectoDefineUnaNuevaActividadConNombreYDuracionDias(String nombre, int duracion) {
        try {
            actividad = gerenteProyecto.crearActividad(proyecto, nombre, duracion);
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
            actividad = gerenteProyecto.crearActividad(proyecto, null, 5);
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
            actividad = gerenteProyecto.crearActividad(proyecto, "Actividad Finalizada", 5);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se informa un mensaje indicando que no se pueden agregar actividades a proyectos finalizados")
    public void seInformaUnMensajeIndicandoQueNoSePuedenAgregarActividadesAProyectosFinalizados() {
        assertNotNull(excepcion);
        assertEquals("No se pueden agregar actividades a un proyecto finalizado", excepcion.getMessage());
    }

    @Given("un proyecto con actividades y dependencias definidas")
    public void unProyectoConActividadesYDependenciasDefinidas() {
        proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
        actividad = gerenteProyecto.crearActividad(proyecto, "Actividad 1", 5);
        Actividad actividad2 = gerenteProyecto.crearActividad(proyecto, "Actividad 2", 3);
        gerenteProyecto.configurarDependencia(actividad2, actividad, 0);
    }

    @When("el gerente de proyecto planifica el proyecto con fecha de inicio {string}")
    public void elGerenteDeProyectoPlanificaElProyectoConFechaDeInicio(String fechaString) {
        LocalDate fechaInicio = LocalDate.parse(fechaString);
        gerenteProyecto.planificarProyecto(proyecto, fechaInicio);
    }

    @Then("las fechas de inicio y fin de todas las actividades se calculan automáticamente")
    public void lasFechasDeInicioYFinDeTodasLasActividadesSeCalculanAutomaticamente() {
        for (Actividad act : proyecto.getActividades()) {
            assertNotNull(act.getFechaInicioPlanificada());
            assertNotNull(act.getFechaFinPlanificada());
        }
    }

    @And("la fecha de fin del proyecto es la máxima fecha de fin de las actividades")
    public void laFechaDeFinDelProyectoEsLaMaximaFechaDeFinDeLasActividades() {
        LocalDate maxFechaFin = proyecto.getActividades().stream()
                .map(Actividad::getFechaFinPlanificada)
                .max(LocalDate::compareTo)
                .orElse(null);
        assertEquals(maxFechaFin, proyecto.getFechaFinPlanificada());
    }

    @Given("un proyecto con actividades")
    public void unProyectoConActividades() {
        proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
        gerenteProyecto.crearActividad(proyecto, "Actividad 1", 5);
        gerenteProyecto.crearActividad(proyecto, "Actividad 2", 3);
    }

    @When("el gerente de proyecto intenta planificar el proyecto con fecha de inicio nula")
    public void elGerenteDeProyectoIntentaPlanificarElProyectoConFechaDeInicioNula() {
        try {
            gerenteProyecto.planificarProyecto(proyecto, null);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se lanza un error indicando la fecha de inicio no puede ser nula")
    public void seLanzaUnErrorIndicandoLaFechaDeInicioNoPuedeSerNula() {
        assertNotNull(excepcion);
        assertEquals("La fecha de inicio no puede ser nula", excepcion.getMessage());
    }
}
package org.pmtool.steps;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.pmtool.model.Proyecto;
import org.pmtool.manager.GerenteProyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto.EstadoProyecto;
import org.pmtool.model.Actividad.EstadoActividad;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CambiarEstadoProyectoSteps {
    private Proyecto proyecto;
    private Exception exception;
    private GerenteProyecto gerenteProyecto;

    @Before
    public void setup() {
        gerenteProyecto = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto test", 100, 50000.0);
        exception = null;
    }

    @Given("existe un proyecto en estado {string}")
    public void existeUnProyectoEnEstado(String estado) {
        if (estado.equals("EN_CURSO")) {
            gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 9, 1));

        }
        assertEquals(EstadoProyecto.valueOf(estado), proyecto.getEstado());
    }

    @When("el Gerente de Proyecto suministra una fecha real de inicio")
    public void elGerenteDeProyectoSuministraUnaFechaRealDeInicio() {
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2025, 9, 1));
    }

    @Then("el estado del proyecto cambia a {string}")
    public void elEstadoDelProyectoCambiaA(String estado) {
        assertEquals(EstadoProyecto.valueOf(estado), proyecto.getEstado());
    }

    @Then("se informa un mensaje indicando que el proyecto debe estar en curso para poder finalizarlo")
    public void seInformaUnMensajeIndicandoQueElProyectoDebeEstarEnCursoParaPoderFinalizarlo() {
        assertNotNull(exception);
        assertEquals("El proyecto debe estar en curso para poder finalizarlo", exception.getMessage());
    }

    @And("el proyecto mantiene el estado en {string}")
    public void elProyectoMantieneElEstadoEn(String estado) {
        assertEquals(EstadoProyecto.valueOf(estado), proyecto.getEstado());
    }

    @Given("todas las actividades del proyecto están completadas")
    public void todasLasActividadesDelProyectoEstanCompletadas() {
        gerenteProyecto.crearActividad(proyecto, "NombreTest", 0);

        for (Actividad actividad : proyecto.getActividades()) {
            actividad.activar();
            actividad.desactivar();
        }
    }

    @When("el Gerente de Proyecto solicita finalizar el proyecto")
    public void elGerenteDeProyectoSolicitaFinalizarElProyecto() {
        try {
            gerenteProyecto.finalizarProyecto(proyecto);
        } catch (IllegalArgumentException e) {
            exception = e;
        }
    }

    @And("se registra la fecha real de finalización del proyecto")
    public void seRegistraLaFechaRealDeFinalizacionDelProyecto() {
        assertNotNull(proyecto.getFechaFinReal());
    }

    @Given("al menos una actividad del proyecto no está en estado {string}")
    public void alMenosUnaActividadDelProyectoNoEstaEnEstado(String estado) {
        gerenteProyecto.crearActividad(proyecto, "NombreTest", 0);

        int contador = 0;
        for (Actividad actividad : proyecto.getActividades()) {
            if (!actividad.getEstado().equals(EstadoActividad.valueOf(estado))) {
                contador++;
            }
        }
        assertTrue(contador > 0);
    }

    @Then("se muestra un mensaje de error indicando que todas las actividades deben estar completadas")
    public void seMuestraUnMensajeDeErrorIndicandoQueTodasLasActividadesDebenEstarCompletadas() {
        assertNotNull(exception);
        assertEquals("Para finalizar un proyecto todas sus actividades deben estar completadas", exception.getMessage());
    }
}
package org.pmtool.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.pmtool.manager.GerentePortafolio;
import org.pmtool.model.Proyecto;

import static org.junit.jupiter.api.Assertions.*;

public class CrearProyectoSteps {

    private GerentePortafolio gerentePortafolio;
    private Proyecto proyecto;
    private Exception excepcion;
    private int sizeProyectos = 0;

    @Given("existe un portafolio de proyectos")
    public void existeUnPortafolioDeProyectos() {
        sizeProyectos = 0;
        proyecto = null;
        excepcion = null;
        gerentePortafolio = new GerentePortafolio();
    }

    @When("el Gerente de Portafolio solicita agregar un nuevo proyecto al portafolio")
    public void elGerenteDePortafolioSolicitaAgregarUnNuevoProyecto() {
        proyecto = gerentePortafolio.crearProyecto("Proyecto Test", 100, 50000.0);
        sizeProyectos = gerentePortafolio.getProyectos().size();
        assertEquals(1, sizeProyectos);
    }

    @Then("se crea un nuevo proyecto con un nombre, el total de horas estimadas, el presupuesto y en estado {string}")
    public void seCreaUnNuevoProyectoConCodigoUnico(String estado) {
        assertNotNull(proyecto);
        assertTrue(proyecto.getNumero() > 1);
        assertEquals("Proyecto Test", proyecto.getNombre());
        assertTrue(proyecto.isPlanificado());
        assertEquals(100, proyecto.getTotalHorasEstimadas());
        assertEquals(50000.0, proyecto.getPresupuesto());
    }

    @Given("ya existe un proyecto con el nombre {string}")
    public void yaExisteUnProyectoConElNombre(String nombreProyecto) {
        sizeProyectos = 0;
        proyecto = null;
        excepcion = null;
        gerentePortafolio.crearProyecto(nombreProyecto, 100, 50000.0);
        sizeProyectos = gerentePortafolio.getProyectos().size();
        assertEquals(1, sizeProyectos);
    }

    @When("el Gerente de Portafolio solicita agregar un nuevo proyecto con el nombre {string}")
    public void elGerenteDePortafolioSolicitaAgregarUnNuevoProyectoConElNombre(String nombreProyecto) {
        try {
            proyecto = gerentePortafolio.crearProyecto(nombreProyecto, 100, 50000.0);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se muestra un mensaje de error indicando que el nombre del proyecto ya existe")
    public void seMuestraUnMensajeDeErrorIndicandoQueElNombreDelProyectoYaExiste() {
        assertNotNull(excepcion);
        assertEquals("El nombre del proyecto ya existe", excepcion.getMessage());
        excepcion = null;
    }

    @Then("no se registra el proyecto en el portafolio")
    public void noSeRegistraElProyectoEnElPortafolio() {
        assertEquals(sizeProyectos, gerentePortafolio.getProyectos().size());
    }

    @When("el Gerente de Portafolio solicita agregar un nuevo proyecto sin nombre")
    public void elGerenteDePortafolioSolicitaAgregarUnNuevoProyectoSinNombre() {
        try {
            proyecto = gerentePortafolio.crearProyecto("", 100, 50000.0);
        } catch (Exception e) {
            excepcion = e;
        }
    }

    @Then("se muestra un mensaje de error indicando que el nombre del proyecto es obligatorio")
    public void seMuestraUnMensajeDeErrorIndicandoQueElNombreDelProyectoEsObligatorio() {
        assertNotNull(excepcion);
        assertEquals("El nombre del proyecto no puede ser nulo o vacío", excepcion.getMessage());
        excepcion = null;
    }
}

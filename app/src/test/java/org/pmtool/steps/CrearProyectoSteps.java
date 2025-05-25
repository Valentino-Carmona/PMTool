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

    @Given("existe un portafolio de proyectos")
    public void existeUnPortafolioDeProyectos() {
        gerentePortafolio = new GerentePortafolio();
    }

    @When("el Gerente de Portafolio solicita agregar un nuevo proyecto al portafolio")
    public void elGerenteDePortafolioSolicitaAgregarUnNuevoProyecto() {
        proyecto = gerentePortafolio.crearProyecto("Proyecto Test", 100, 50000.0);
    }

    @Then("se crea un nuevo proyecto con un nombre, el total de horas estimadas, el presupuesto y en estado {string}")
    public void seCreaUnNuevoProyectoConCodigoUnico(String estado) {
        assertNotNull(proyecto);
        assertTrue(proyecto.getNumero() > 1);
        assertEquals("Proyecto Test", proyecto.getNombre());
        assertTrue(proyecto.isPlanificado());
        assertTrue(proyecto.getTotalHorasEstimadas() > 0);
        assertTrue(proyecto.getPresupuesto() > 0);
    }

    @Given("ya existe un proyecto con el nombre {string}")
    public void yaExisteUnProyectoConElNombre(String nombreProyecto) {
        gerentePortafolio.crearProyecto(nombreProyecto, 100, 50000.0);
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
    }

    @Then("no se registra el proyecto en el portafolio")
    public void noSeRegistraElProyectoEnElPortafolio() {
        assertEquals(1, gerentePortafolio.getProyectos().size());
    }
}

package org.pmtool.steps;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pmtool.model.Actividad;
import org.pmtool.model.Actividad.EstadoActividad;

public class ActivarActividadSteps {
  private Actividad actividad;
  private Exception exception;

  @Given("existe una actividad planificada lista para iniciar")
  public void existeUnaActividadPlanificadaListaParaIniciar() {
    actividad = new Actividad("1", "Actividad Planificada", 5);
    assertEquals(EstadoActividad.PLANIFICADA, actividad.getEstado());
  }

  @When("el Gerente de Proyecto inicia la ejecución de la actividad")
  public void elGerenteDeProyectoIniciaLaEjecucionDeLaActividad() {
    try {
      actividad.activar();
    } catch (Exception e) {
      exception = e;
    }
  }

  @Then("la actividad pasa a estar en ejecución")
  public void laActividadPasaAEstarEnEjecucion() {
    assertEquals(EstadoActividad.EN_EJECUCION, actividad.getEstado());
  }

  @And("se registra la fecha de inicio real de la actividad automaticamente")
  public void seRegistraLaFechaDeInicioRealDeLaActividadAutomaticamente() {
    assertNotNull(actividad.getFechaInicioReal());
  }

  @Given("existe una actividad que ya está completada")
  public void existeUnaActividadQueYaEstaCompletada() {
    actividad = new Actividad("1", "Actividad Completada", 5);
    actividad.activar();
    actividad.desactivar();
    assertEquals(EstadoActividad.COMPLETADA, actividad.getEstado());
  }

  @Then("se muestra un mensaje de error indicando que la actividad está completada")
  public void seMuestraUnMensajeDeErrorIndicandoQueLaActividadEstaCompletada() {
    assertEquals("No se puede activar una actividad ya completada.", exception.getMessage());
  }

  @Given("existe una actividad que ya está en ejecución")
  public void existeUnaActividadQueYaEstaEnEjecucion() {
    actividad = new Actividad("1", "Actividad en Ejecución", 5);
    actividad.activar();
    assertEquals(EstadoActividad.EN_EJECUCION, actividad.getEstado());
  }

  @Then("se muestra un mensaje de error indicando que la actividad ya está en ejecución")
  public void seMuestraUnMensajeDeErrorIndicandoQueLaActividadYaEstaEnEjecucion() {
    assertEquals("La actividad ya está en ejecución.", exception.getMessage());
  }
}

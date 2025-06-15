package org.pmtool.steps;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.pmtool.manager.GerenteProyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto;

public class ConsultarJerarquiaEdtSteps {
  private Proyecto proyecto;
  private Actividad actividad;
  private Actividad subactividad;
  private GerenteProyecto gerenteProyecto;

  @Before
  public void setup() {
    proyecto = null;
    actividad = null;
    subactividad = null;
    gerenteProyecto = new GerenteProyecto(); // Inicializar el GerenteProyecto
  }

  @Given("existe un proyecto con una actividad y una subactividad")
  public void existeUnProyectoConAlMenosUnaActividadYUnaSubactividad() {
    proyecto = new Proyecto("Proyecto Test", 100, 50000.0);
    actividad = gerenteProyecto.crearActividad(proyecto, "Actividad Principal", 5);
    subactividad = actividad.agregarSubactividad("Subactividad", 3);
  }

  @When("el Gerente de Proyecto solicita la jerarquía EDT del proyecto")
  public void elGerenteDeProyectoSolicitaLaJerarquiaEdtDelProyecto() {
    if (!proyecto.getActividades().isEmpty()) {
      proyecto
          .getActividades()
          .forEach(
              act -> {
                act.getSubactividades();
              });
    }
  }

  @Then("se muestra la estructura jerárquica con las actividades y sus subactividades")
  public void seMuestraLaEstructuraJerarquicaConLasActividadesYSusSubactividades() {
    assertFalse(proyecto.getActividades().isEmpty());
    assertFalse(proyecto.getActividades().get(0).getSubactividades().isEmpty());
  }

  @Then("la actividad y subactividad tiene el numero EDT correcto")
  public void laActividadYSubactividadTieneElNumeroEdtCorrecto() {
    // Actividad principal
    assertNotNull(actividad.getNumeroEDT());
    assertEquals("1", actividad.getNumeroEDT());

    // Subactividad
    assertNotNull(subactividad.getNumeroEDT());
    assertEquals("1.1", subactividad.getNumeroEDT());
  }

  @Given("existe un proyecto sin actividades")
  public void existeUnProyectoSinActividades() {
    proyecto = new Proyecto("Proyecto Vacío", 0, 0.0);
  }

  @Then("se ve que el proyecto no tiene actividades")
  public void seVeQueElProyectoNoTieneActividades() {
    assertTrue(proyecto.getActividades().isEmpty());
  }

  @Given("existe un proyecto con actividades pero sin subactividades")
  public void existeUnProyectoConActividadesPeroSinSubactividades() {
    proyecto = new Proyecto("Proyecto Simple", 100, 50000.0);
    gerenteProyecto.crearActividad(proyecto, "Actividad 1", 5);
    gerenteProyecto.crearActividad(proyecto, "Actividad 2", 5);
    gerenteProyecto.crearActividad(proyecto, "Actividad 3", 5);
    assertFalse(proyecto.getActividades().isEmpty());
    assertTrue(proyecto.getActividades().get(0).getSubactividades().isEmpty());
  }

  @Then("se muestra la estructura jerárquica con solo las actividades principales")
  public void seMuestraLaEstructuraJerarquicaConSoloLasActividadesPrincipales() {
    for (int i = 0; i < proyecto.getActividades().size(); i++) {
      Actividad actividadActual = proyecto.getActividades().get(i);
      assertNotNull(actividadActual.getNumeroEDT());
      assertEquals(String.valueOf(i + 1), actividadActual.getNumeroEDT());
      assertTrue(actividadActual.getSubactividades().isEmpty());
    }
  }
}

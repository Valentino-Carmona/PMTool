package org.pmtool.app;

import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad;
import org.pmtool.manager.GerenteProyecto;
import org.pmtool.manager.GerentePortafolio;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();

        // Crear un proyecto
        Proyecto proyecto = gerentePortafolio.crearProyecto("Proyecto A", 500, 100000);
        gerenteProyecto.planificarProyecto(proyecto, LocalDate.of(2024, 1, 1));

        // Agregar actividades con esquema EDT
        Actividad actividad1 = gerenteProyecto.crearActividad(proyecto, "1", "Actividad Principal", 100);
        proyecto.agregarActividad(actividad1);

        Actividad subActividad1 = gerenteProyecto.crearActividad(
            proyecto, 
            actividad1.getNumeroEDT(), 
            "Subactividad 1", 
            50
        );
        actividad1.agregarSubactividad(subActividad1);

        Actividad subSubActividad1 = gerenteProyecto.crearActividad(
            proyecto, 
            subActividad1.getNumeroEDT(), 
            "Sub-subactividad 1", 
            25
        );
        subActividad1.agregarSubactividad(subSubActividad1);

        // Mostrar actividades con numeración EDT
        System.out.println(proyecto);
        for (Actividad actividad : proyecto.getActividades()) {
            mostrarActividadConJerarquia(actividad, "");
        }
    }

    private static void mostrarActividadConJerarquia(Actividad actividad, String prefix) {
        System.out.println(prefix + actividad);
        for (Actividad subactividad : actividad.getSubactividades()) {
            mostrarActividadConJerarquia(subactividad, prefix + "  ");
        }
    }
} 

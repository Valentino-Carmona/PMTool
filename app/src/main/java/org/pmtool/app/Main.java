package org.pmtool.app;

import org.pmtool.model.Actividad;
import org.pmtool.model.Proyecto;
import org.pmtool.manager.GerenteProyecto;
import org.pmtool.manager.GerentePortafolio;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();

        // Crear un proyecto
        Proyecto proyecto = gerentePortafolio.crearProyecto("Proyecto Ejemplo", 500, 100000);
        LocalDate fechaInicioProyecto = LocalDate.of(2025, 9, 1);
        gerenteProyecto.planificarProyecto(proyecto, fechaInicioProyecto);
        
        // Crear actividades
        Actividad planificacion = gerenteProyecto.crearActividad(proyecto, "1", "Planificación del proyecto", 0);
        Actividad definicionReq = gerenteProyecto.crearActividad(proyecto, "1.1", "Definir requisitos", 3);
        Actividad disenoUI = gerenteProyecto.crearActividad(proyecto, "1.2", "Diseñar la UI/UX", 2);
        Actividad desarrollo = gerenteProyecto.crearActividad(proyecto, "2", "Desarrollo", 0);
        Actividad implementacion = gerenteProyecto.crearActividad(proyecto, "2.1", "Implementar funcionalidad", 5);
        Actividad entorno = gerenteProyecto.crearActividad(proyecto, "2.2", "Preparar entorno de despliegue", 2);
        Actividad cierre = gerenteProyecto.crearActividad(proyecto, "3", "Cierre del proyecto", 0);
        Actividad despliegue = gerenteProyecto.crearActividad(proyecto, "3.1", "Desplegar aplicación", 1);
        Actividad capacitacion = gerenteProyecto.crearActividad(proyecto, "3.2", "Capacitar a usuarios", 2);

        // Establecer jerarquía EDT
        proyecto.agregarActividad(planificacion);
        planificacion.agregarSubactividad(definicionReq);
        planificacion.agregarSubactividad(disenoUI);
        proyecto.agregarActividad(desarrollo);
        desarrollo.agregarSubactividad(implementacion);
        desarrollo.agregarSubactividad(entorno);
        proyecto.agregarActividad(cierre);
        cierre.agregarSubactividad(despliegue);
        cierre.agregarSubactividad(capacitacion);

        // Establecer dependencias
        gerenteProyecto.configurarDependencia(disenoUI, definicionReq, "FS", 0); // Diseño depende de Definir requisitos
        gerenteProyecto.configurarDependencia(implementacion, disenoUI, "FS", 0); // Implementación depende de Diseño
        gerenteProyecto.configurarDependencia(entorno, definicionReq, "FS", 2); // Entorno depende de Definir requisitos con 2 días de retraso
        gerenteProyecto.configurarDependencia(despliegue, entorno, "FS", 0); // Despliegue depende de Entorno
        gerenteProyecto.configurarDependencia(capacitacion, despliegue, "FS", -1); // Capacitación depende de Despliegue con 1 día de adelanto

        // Recalcular fechas tras agregar dependencias
        for (Actividad actividad : proyecto.getActividades()) {
            actividad.calcularFechasPlanificadas(fechaInicioProyecto);
        }

        // Mostrar resultados
        System.out.println("Proyecto: " + proyecto);
        for (Actividad actividad : proyecto.getActividades()) {
            mostrarActividadConJerarquia(actividad, "");
        }

        // // Ejemplo de activación/desactivación (simulación)
        // try {
        //     definicionReq.activar(); // Activar primero
        //     definicionReq.desactivar(); // Completar
        //     disenoUI.activar(); // Depende de definicionReq, debería funcionar
        //     disenoUI.desactivar();
        //     implementacion.activar(); // Depende de disenoUI
        //     implementacion.desactivar();
        //     entorno.activar(); // Depende de definicionReq con leadLag
        //     entorno.desactivar();
        //     despliegue.activar();
        //     despliegue.desactivar();
        //     capacitacion.activar();
        //     capacitacion.desactivar();
        //     planificacion.desactivar(); // Debería funcionar si todas las subactividades están completadas
        //     desarrollo.desactivar();
        //     cierre.desactivar();
        //     proyecto.finalizar(null); // Finalizar proyecto
        //     System.out.println("Proyecto finalizado exitosamente el: " + proyecto.getFechaFinReal());
        // } catch (IllegalStateException e) {
        //     System.out.println("Error: " + e.getMessage());
        // }
    }

    private static void mostrarActividadConJerarquia(Actividad actividad, String prefix) {
        System.out.println(prefix + actividad);
        for (Actividad subactividad : actividad.getSubactividades()) {
            mostrarActividadConJerarquia(subactividad, prefix + "  ");
        }
    }
}

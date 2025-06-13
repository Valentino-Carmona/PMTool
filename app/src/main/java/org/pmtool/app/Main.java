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
        
        // Crear actividades y subactividades
        Actividad planificacion = gerenteProyecto.crearActividad(proyecto, "Planificación del proyecto", 0);
        Actividad definicionReq = gerenteProyecto.crearSubActividad(planificacion, "Definir requisitos", 3);
        Actividad disenoUI = gerenteProyecto.crearSubActividad(planificacion, "Diseñar la UI/UX", 2);

        Actividad desarrollo = gerenteProyecto.crearActividad(proyecto, "Desarrollo", 0);
        Actividad implementacion = gerenteProyecto.crearSubActividad(desarrollo, "Implementar funcionalidad", 5);
        Actividad entorno = gerenteProyecto.crearSubActividad(desarrollo, "Preparar entorno de despliegue", 2);
        
        Actividad cierre = gerenteProyecto.crearActividad(proyecto, "Cierre del proyecto", 0);
        Actividad despliegue = gerenteProyecto.crearSubActividad(cierre, "Desplegar aplicación", 1);
        Actividad capacitacion = gerenteProyecto.crearSubActividad(cierre, "Capacitar a usuarios", 2);

        // Establecer dependencias
        gerenteProyecto.configurarDependencia(disenoUI, definicionReq,  0); // Diseño depende de Definir requisitos
        gerenteProyecto.configurarDependencia(implementacion, disenoUI,  0); // Implementación depende de Diseño
        gerenteProyecto.configurarDependencia(entorno, definicionReq,  2); // Entorno depende de Definir requisitos con 2 días de retraso
        gerenteProyecto.configurarDependencia(despliegue, entorno,  0); // Despliegue depende de Entorno
        gerenteProyecto.configurarDependencia(capacitacion, despliegue,  -1); // Capacitación depende de Despliegue con 1 día de adelanto

        // Planificar el proyecto después de definir actividades y dependencias
        gerenteProyecto.planificarProyecto(proyecto, fechaInicioProyecto);

        // Mostrar resultados
        System.out.println("Proyecto: " + proyecto);
        // System.out.println("Fecha de inicio del proyecto: " + proyecto.getFechaInicioPlanificada());
        // System.out.println("Fecha de fin del proyecto: " + proyecto.getFechaFinPlanificada());
        
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

package org.pmtool.manager;

import org.pmtool.model.*;

import java.time.LocalDate;

public class GerenteProyecto {
    
    public void planificarProyecto(Proyecto proyecto, LocalDate inicio) {
        proyecto.planificarProyecto(inicio);
    }

    public void finalizarProyecto(Proyecto proyecto) {
        proyecto.finalizar();
    }

    public Actividad crearActividad(Proyecto proyecto, String numeroPadre, String nombre, int duracionDias) {
        String numeroEDT;
        int size = proyecto.getActividades().size();
        if (size == 0) {
            numeroEDT = numeroPadre;
        }
        else {
            numeroEDT = numeroPadre + "." + (size + 1);
        }
        return new Actividad(numeroEDT, nombre, duracionDias);
    }

    public void configurarDependencia(Actividad actividad, Actividad predecesora, int leadLag) {
        actividad.setDependencia(new FinishToStart(predecesora, leadLag));
    }
}

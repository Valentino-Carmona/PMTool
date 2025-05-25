package org.pmtool.manager;

import org.pmtool.model.Proyecto;
import org.pmtool.model.Actividad;
import org.pmtool.model.FinishToStart;

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

    public void configurarDependencia(Actividad actividad, Actividad predecesora, String tipo, int leadLag) {
        switch (tipo) {
            case "FS":
                actividad.setDependencia(new FinishToStart(predecesora, leadLag));
                break;
            case "FF":
                throw new UnsupportedOperationException("Dependencia Finish to Finish pendiente de implementación");
            case "SS":
                throw new UnsupportedOperationException("Dependencia Start to Start pendiente de implementación");
            case "SF":
                throw new UnsupportedOperationException("Dependencia Start to Finish pendiente de implementación");
            default:
                throw new IllegalArgumentException("Tipo de dependencia no soportado: " + tipo);
        }
    }
}

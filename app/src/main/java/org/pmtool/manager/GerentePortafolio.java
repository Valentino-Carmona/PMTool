package org.pmtool.manager;

import org.pmtool.model.Proyecto;

import java.util.ArrayList;
import java.util.List;

public class GerentePortafolio {
    private List<Proyecto> proyectos = new ArrayList<>();

    public Proyecto crearProyecto(String nombre, int horas, double presupuesto) {
        // Verificar si ya existe un proyecto con el mismo nombre
        for (Proyecto proyecto : proyectos) {
            if (proyecto.igualNombre(nombre)) {
                throw new IllegalArgumentException("El nombre del proyecto ya existe");
            }
        }
        Proyecto proyecto = new Proyecto(nombre, horas, presupuesto);
        proyectos.add(proyecto);
        return proyecto;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }
}

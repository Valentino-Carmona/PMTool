package org.pmtool.validation;

import java.util.ArrayList;
import java.util.List;
import org.pmtool.model.Actividad;
import org.pmtool.model.IDependencia;

public class ValidadorCiclosDependencias {

  public static void validarSinCiclos(Actividad actividad, IDependencia dependencia) {
    if (dependencia == null) {
      return;
    }

    Actividad predecesora = dependencia.getPredecesora();
    if (predecesora == null) {
      return;
    }

    // Verificar auto-referencia directa
    if (predecesora == actividad) {
      throw new IllegalArgumentException("La actividad no puede depender de sí misma");
    }

    // Verificar ciclos indirectos
    List<Actividad> visitadas = new ArrayList<>();
    if (existeCiclo(predecesora, actividad, visitadas)) {
      throw new IllegalArgumentException("La dependencia genera un ciclo");
    }
  }

  private static boolean existeCiclo(
      Actividad predecesora, Actividad dependiente, List<Actividad> visitadas) {
    if (visitadas.contains(predecesora)) {
      return false;
    }

    visitadas.add(predecesora);

    IDependencia dependenciaPredecesora = predecesora.getDependencia();
    if (dependenciaPredecesora != null) {
      Actividad predecesora2 = dependenciaPredecesora.getPredecesora();
      if (predecesora2 != null) {
        if (predecesora2 == dependiente) {
          return true;
        }
        if (existeCiclo(predecesora2, dependiente, visitadas)) {
          return true;
        }
      }
    }

    return false;
  }
}

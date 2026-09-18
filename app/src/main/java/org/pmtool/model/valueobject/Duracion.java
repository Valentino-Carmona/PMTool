package org.pmtool.model.valueobject;

import java.util.Objects;

public final class Duracion {
  private final int dias;

  public Duracion(int dias) {
    if (dias < 0) {
      throw new IllegalArgumentException("La duración debe ser mayor o igual a cero.");
    }
    this.dias = dias;
  }

  public static Duracion of(int dias) {
    return new Duracion(dias);
  }

  public int getDias() {
    return dias;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Duracion duracion = (Duracion) o;
    return dias == duracion.dias;
  }

  @Override
  public int hashCode() {
    return Objects.hash(dias);
  }

  @Override
  public String toString() {
    return dias + " días";
  }
}

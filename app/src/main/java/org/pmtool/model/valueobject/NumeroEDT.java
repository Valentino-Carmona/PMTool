package org.pmtool.model.valueobject;

import java.util.Objects;

public final class NumeroEDT {
  private final String valor;

  public NumeroEDT(String valor) {
    if (valor == null || valor.trim().isEmpty()) {
      throw new IllegalArgumentException("El número EDT no puede ser nulo o vacío.");
    }
    this.valor = valor;
  }

  public static NumeroEDT of(String valor) {
    return new NumeroEDT(valor);
  }

  public NumeroEDT generarSubNivel(int index) {
    return new NumeroEDT(this.valor + "." + index);
  }

  public String getValor() {
    return valor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NumeroEDT numeroEDT = (NumeroEDT) o;
    return valor.equals(numeroEDT.valor);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valor);
  }

  @Override
  public String toString() {
    return valor;
  }
}

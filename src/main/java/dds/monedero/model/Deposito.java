package dds.monedero.model;

import java.time.LocalDate;

public class Deposito extends Movimiento{
  Deposito(LocalDate fecha, double monto, Cuenta cuenta){
    super(fecha, monto, cuenta, true);
  }

  public void ejecutar(){

  }
}

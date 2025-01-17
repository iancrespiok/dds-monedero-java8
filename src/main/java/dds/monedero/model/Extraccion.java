package dds.monedero.model;

import java.time.LocalDate;

public class Extraccion extends Movimiento{
  Extraccion(LocalDate fecha, double monto, Cuenta cuenta){
    super(fecha, monto, cuenta, false);
  }

  public void ejecutar(){
    double saldoPrevio = this.getCuenta().getSaldo();
    this.getCuenta().setSaldo(saldoPrevio-this.getMonto());
  }
}

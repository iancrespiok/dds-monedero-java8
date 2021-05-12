package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {
  private LocalDate fecha;
  private double monto;
  private Cuenta cuenta;
  private Boolean esDeposito;

  public Movimiento(LocalDate fecha, double monto, Cuenta cuenta, Boolean esDeposito) {
    this.fecha = fecha;
    this.monto = monto;
    this.cuenta = cuenta;
    this.esDeposito = esDeposito;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public Cuenta getCuenta() {
    return cuenta;
  }

  public Boolean esDeposito(){
    return esDeposito;
  }

  public Boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public abstract void ejecutar();
}

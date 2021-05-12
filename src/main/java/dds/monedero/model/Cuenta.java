package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;

public class Cuenta {

  private double saldo = 0;
  private int depositosDiarios = 3;
  private double limiteExtraccionDiario = 1000;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public int getDepositosDiarios() {
    return depositosDiarios;
  }

  public double getLimiteExtraccionDiario() {
    return limiteExtraccionDiario;
  }

  public void depositar(double monto) {
    validarDeposito(monto);
    Deposito deposito = new Deposito(LocalDate.now(), monto, this, true);
    new Movimiento(LocalDate.now(), monto, true).agregateA(this);
  }

  public void extraer(double monto) {
    validarExtraccion(monto);
    new Movimiento(LocalDate.now(), monto, false).agregateA(this);
  }

  private void validarDeposito(double monto) {
    validarSiElMontoEsValido(monto);
    validarSiSuperoDepositosDiarios();
  }

  private void validarSiElMontoEsValido(double monto) {
    if (monto <= 0) {
      throw new MontoNegativoException(monto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private  void validarSiSuperoDepositosDiarios(){
    if (getMovimientos()
        .stream()
        .filter(movimiento -> movimiento.esDeposito())
        .filter(deposito -> deposito.esDeLaFecha(LocalDate.now()))
        .count() >= this.getDepositosDiarios()) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  private void validarExtraccion(double monto) {
    validarSiElMontoEsValido(monto);
    validarSiQuiereSacarMasDeSuSaldo(monto);
    validarSiSuperoMontoDeExtraccionMaximo(monto);
  }

  private void validarSiSuperoMontoDeExtraccionMaximo(double monto) {
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limiteRestante = this.getLimiteExtraccionDiario() - montoExtraidoHoy;
    if (monto > limiteRestante) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000  + " diarios, límite restante: " + limiteRestante);
    }
  }

  private void validarSiQuiereSacarMasDeSuSaldo(double monto) {
    if (monto > getSaldo()) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

   public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.esDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}

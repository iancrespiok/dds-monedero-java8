package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta(0,3,1000);
  }

  @Test
  void ponerMontoNegativoArrojaExcepcionCorrecta() {
    assertThrows(MontoNegativoException.class, () -> cuenta.depositar(-1500));
  }

  @Test
  void tresDepositosSeSumanCorrectamente() {
    cuenta.depositar(1500);
    cuenta.depositar(456);
    cuenta.depositar(1900);
    assertEquals(3856, cuenta.getSaldo());
  }

  @Test
  void masDeTresDepositosArrojaExcepcionCorrecta() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.depositar(1500);
          cuenta.depositar(456);
          cuenta.depositar(1900);
          cuenta.depositar(245);
    });
  }

  @Test
  void extraerMasQueElSaldoArrojaExcepcionCorrecta() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.extraer(1001);
    });
  }

  @Test
  public void extraerMasDe1000ArrojaExcepcionCorrecta() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.extraer(1001);
    });
  }

  @Test
  public void extraerMontoNegativoArrojaExcepcionCorrecta() {
    assertThrows(MontoNegativoException.class, () -> cuenta.extraer(-500));
  }

}
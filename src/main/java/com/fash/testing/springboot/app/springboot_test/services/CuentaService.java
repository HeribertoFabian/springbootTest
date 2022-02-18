package com.fash.testing.springboot.app.springboot_test.services;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta> findAll();

    Cuenta save(Cuenta cuenta);

    void deleteById(Long id);

    Cuenta findById(Long id);

    int revisarTotalTransferencia(Long bancoId);

    BigDecimal revisarSaldo(Long cuentaId);

    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}

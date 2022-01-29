package com.fash.testing.springboot.app.springboot_test.services;

import com.fash.testing.springboot.app.springboot_test.models.Banco;
import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.BancoRepositorio;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class CuentaServiceImpl implements CuentaService{

    private CuentaRepositorio cuentaRepositorio;
    private BancoRepositorio bancoRepositorio;

    public CuentaServiceImpl(CuentaRepositorio cuentaRepositorio, BancoRepositorio bancoRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.bancoRepositorio = bancoRepositorio;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaRepositorio.findById(id);
    }

    @Override
    public int revisarTotalTransferencia(Long bancoId) {
        Banco banco = bancoRepositorio.findById(bancoId);
        return banco.getTotalTransferencia();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepositorio.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto) {
        Banco banco = bancoRepositorio.findById(1L);
        int totalTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepositorio.update(banco);

        Cuenta cuentaOrigen = cuentaRepositorio.findById(numCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepositorio.update(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepositorio.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaRepositorio.update(cuentaDestino);

    }
}

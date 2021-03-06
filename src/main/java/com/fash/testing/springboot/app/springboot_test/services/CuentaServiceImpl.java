package com.fash.testing.springboot.app.springboot_test.services;

import com.fash.testing.springboot.app.springboot_test.exceptions.DineroInsuficienteException;
import com.fash.testing.springboot.app.springboot_test.models.Banco;
import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.BancoRepositorio;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService{

    private CuentaRepositorio cuentaRepositorio;
    private BancoRepositorio bancoRepositorio;

    public CuentaServiceImpl(CuentaRepositorio cuentaRepositorio, BancoRepositorio bancoRepositorio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.bancoRepositorio = bancoRepositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepositorio.findAll();
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepositorio.save(cuenta);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cuentaRepositorio.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepositorio.findById(id).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencia(Long bancoId) {
        Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepositorio.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {

        Cuenta cuentaOrigen = cuentaRepositorio.findById(numCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        cuentaRepositorio.save(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepositorio.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepositorio.save(cuentaDestino);

        Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++totalTransferencias);
        bancoRepositorio.save(banco);
    }
}

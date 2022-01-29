package com.fash.testing.springboot.app.springboot_test.repository;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;

import java.util.List;

public interface CuentaRepositorio {

    List<Cuenta> findAll();
    Cuenta findById(Long id);
    void update(Cuenta cuenta);

}

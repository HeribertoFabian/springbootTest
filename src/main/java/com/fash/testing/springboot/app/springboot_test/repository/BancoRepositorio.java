package com.fash.testing.springboot.app.springboot_test.repository;

import com.fash.testing.springboot.app.springboot_test.models.Banco;

import java.util.List;

public interface BancoRepositorio {
    List<Banco> findAll();
    Banco findById(Long id);

    void update(Banco banco);
}

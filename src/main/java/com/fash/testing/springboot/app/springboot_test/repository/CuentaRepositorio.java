package com.fash.testing.springboot.app.springboot_test.repository;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta> findByPersona(String persona);


}

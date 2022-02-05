package com.fash.testing.springboot.app.springboot_test;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IntegracionJPATest {
    @Autowired
    CuentaRepositorio cuentaRepository;

    @Test
    void findById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getPersona());
    }

}

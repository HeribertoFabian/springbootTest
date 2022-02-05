package com.fash.testing.springboot.app.springboot_test;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
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

    @Test
    void findByPerson() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Andres");
        assertTrue(cuenta.isPresent());
        assertEquals("Andres", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());
    }

    @Test
    void findByPersonThrowException() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Rod");
        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertTrue(!cuenta.isPresent());
    }

    @Test
    void testFindAll(){
        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }

    @Test
    void save() {
        //Given
        Cuenta cuentaPepe = new Cuenta(null, "pepe", new BigDecimal("3000"));
        //when
        Cuenta pepe = cuentaRepository.save(cuentaPepe);

        //when
        //Cuenta pepe = cuentaRepository.findByPersona("pepe").orElseThrow();
        //Cuenta pepe = cuentaRepository.findById(save.getId()).orElseThrow();

        //then
        assertEquals("3000", pepe.getSaldo().toPlainString());
        assertEquals("pepe", pepe.getPersona());

    }
}

package com.fash.testing.springboot.app.springboot_test;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integracion_jpa")
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
    void testFindAll() {
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

    @Test
    void testUpdate() {

        //Given
        Cuenta cuentaPepe = new Cuenta(null, "pepe", new BigDecimal("3000"));

        //when
        Cuenta pepe = cuentaRepository.save(cuentaPepe);

        //then
        assertEquals("3000", pepe.getSaldo().toPlainString());
        assertEquals("pepe", pepe.getPersona());

        //when
        pepe.setSaldo(new BigDecimal("3800"));
        Cuenta cuentaActualizada = cuentaRepository.save(pepe);

        //then
        assertEquals("3800", cuentaActualizada.getSaldo().toPlainString());
        assertEquals("pepe", cuentaActualizada.getPersona());
    }

    @Test
    void testDelete() {
        //Given
        Cuenta cuenta= cuentaRepository.findById(2L).orElseThrow();
        assertEquals("John", cuenta.getPersona());

        //When
        cuentaRepository.delete(cuenta);

        //then
        assertThrows(NoSuchElementException.class, ()->{
            //cuentaRepository.findByPersona("John").orElseThrow();
            cuentaRepository.findById(2L).orElseThrow();
        });

        assertEquals(1, cuentaRepository.findAll().size());

    }
}

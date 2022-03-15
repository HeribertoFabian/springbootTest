package com.fash.testing.springboot.app.springboot_test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fash.testing.springboot.app.springboot_test.exceptions.DineroInsuficienteException;
import com.fash.testing.springboot.app.springboot_test.models.Banco;
import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.repository.BancoRepositorio;
import com.fash.testing.springboot.app.springboot_test.repository.CuentaRepositorio;
import com.fash.testing.springboot.app.springboot_test.services.CuentaService;
import com.fash.testing.springboot.app.springboot_test.services.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SpringbootTestApplicationTests {

    @MockBean
    CuentaRepositorio cuentaRepositorio;

    @MockBean
    BancoRepositorio bancoRepositorio;

    @Autowired
    CuentaService service;

    @BeforeEach
    void setUp() {
  //      cuentaRepositorio = mock(CuentaRepositorio.class);
  //      bancoRepositorio = mock(BancoRepositorio.class);
  //      service = new CuentaServiceImpl(cuentaRepositorio, bancoRepositorio);

 //       Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
 //       Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
//        Datos.BANCO.setTotalTransferencia(0);
    }

    @Test
    void contextLoads() {
        when(cuentaRepositorio.findById(1L)).thenReturn(Datos.crearCuenta001());
        when(cuentaRepositorio.findById(2L)).thenReturn(Datos.crearCuenta002());
        when(bancoRepositorio.findById(1L)).thenReturn(Datos.crearBanco());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        service.transferir(1L, 2L, new BigDecimal("100"), 1L);

        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("900", saldoOrigen.toPlainString());
        assertEquals("2100", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencia(1L);
        assertEquals(1, total);

        verify(cuentaRepositorio, times(3)).findById(1L);
        verify(cuentaRepositorio, times(3)).findById(2L);
        verify(cuentaRepositorio, times(2)).save(any(Cuenta.class));

        verify(bancoRepositorio, times(2)).findById(1L);
        verify(bancoRepositorio).save(any(Banco.class));

        verify(cuentaRepositorio, times(6)).findById(anyLong());
        verify(cuentaRepositorio, never()).findAll();
    }

    @Test
    void testLanzarExceptionDineroInsuficiente() {
        when(cuentaRepositorio.findById(1L)).thenReturn(Datos.crearCuenta001());
        when(cuentaRepositorio.findById(2L)).thenReturn(Datos.crearCuenta002());
        when(bancoRepositorio.findById(1L)).thenReturn(Datos.crearBanco());

        BigDecimal saldoOrigen = service.revisarSaldo(1L);
        BigDecimal saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        assertThrows(DineroInsuficienteException.class, () ->{
            service.transferir(1L, 2L, new BigDecimal("1200"), 1L);
        });


        saldoOrigen = service.revisarSaldo(1L);
        saldoDestino = service.revisarSaldo(2L);

        assertEquals("1000", saldoOrigen.toPlainString());
        assertEquals("2000", saldoDestino.toPlainString());

        int total = service.revisarTotalTransferencia(1L);
        assertEquals(0, total);

        verify(cuentaRepositorio, times(3)).findById(1L);
        verify(cuentaRepositorio, times(2)).findById(2L);
        verify(cuentaRepositorio, never()).save(any(Cuenta.class));

        verify(bancoRepositorio).findById(1L);
        verify(bancoRepositorio, never()).save(any(Banco.class));
        verify(cuentaRepositorio, never()).findAll();

        verify(cuentaRepositorio, times(5)).findById(anyLong());
        verify(cuentaRepositorio, never()).findAll();
    }

    @Test
    void testAssertSame() {
        when(cuentaRepositorio.findById(1L)).thenReturn(Datos.crearCuenta001());

        Cuenta cuenta1 = service.findById(1L);
        Cuenta cuenta2 = service.findById(1L);

        assertSame(cuenta1,  cuenta2);
        assertTrue(cuenta1 == cuenta2);
        assertEquals("Andres", cuenta1.getPersona());
        assertEquals("Andres", cuenta2.getPersona());

        verify(cuentaRepositorio, times(2)).findById(anyLong());

    }

    @Test
    void testFindAll() {
        //Given
        List<Cuenta> datos = Arrays.asList(Datos.crearCuenta001().orElseThrow(), Datos.crearCuenta002().orElseThrow());
        when(cuentaRepositorio.findAll()).thenReturn(datos);

        //when
        List<Cuenta> cuentas = service.findAll();

        //Then
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size() );
        assertTrue(cuentas.contains(Datos.crearCuenta002().orElseThrow()));

        verify(cuentaRepositorio).findAll();
    }

    @Test
    void testSave() {
        //Given
        Cuenta cuentapepe = new Cuenta(null, "pepe", new BigDecimal("3000"));
        when(cuentaRepositorio.save(any())).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
        });

        //When
        Cuenta cuenta = service.save(cuentapepe);

        //Then
        assertEquals("pepe", cuenta.getPersona());
        assertEquals(3L, cuenta.getId());
        assertEquals("3000", cuenta.getSaldo().toPlainString());

        //verify(cuentaRepositorio.save(any(Cuenta.class)));

    }
}

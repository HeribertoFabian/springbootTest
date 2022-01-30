package com.fash.testing.springboot.app.springboot_test;

import com.fash.testing.springboot.app.springboot_test.models.Banco;
import com.fash.testing.springboot.app.springboot_test.models.Cuenta;

import java.math.BigDecimal;

public class Datos {


//    public static final Cuenta CUENTA_001 = new Cuenta(1L,"Andres", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002 = new Cuenta(2L,"John", new BigDecimal("2000"));
//    public static final Banco BANCO = new Banco(1L,"El Banco Financiero",0);

    public static final Cuenta crearCuenta001(){
        return new Cuenta(1L,"Andres", new BigDecimal("1000"));
    }

    public static final Cuenta crearCuenta002(){
        return new Cuenta(2L,"John", new BigDecimal("2000"));
    }

    public static final Banco crearBanco(){
        return new Banco(1L,"El Banco Financiero",0);
    }
}

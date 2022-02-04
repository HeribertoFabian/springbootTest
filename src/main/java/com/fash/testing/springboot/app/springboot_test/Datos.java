package com.fash.testing.springboot.app.springboot_test;

import com.fash.testing.springboot.app.springboot_test.models.Banco;
import com.fash.testing.springboot.app.springboot_test.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class Datos {


//    public static final Cuenta CUENTA_001 = new Cuenta(1L,"Andres", new BigDecimal("1000"));
//    public static final Cuenta CUENTA_002 = new Cuenta(2L,"John", new BigDecimal("2000"));
//    public static final Banco BANCO = new Banco(1L,"El Banco Financiero",0);

    public static final Optional<Cuenta> crearCuenta001(){
        return Optional.of(new Cuenta(1L,"Andres", new BigDecimal("1000")));
    }

    public static final Optional<Cuenta> crearCuenta002(){

        return Optional.of(new Cuenta(2L,"John", new BigDecimal("2000")));
    }

    public static final Optional<Banco> crearBanco(){

        return Optional.of(new Banco(1L,"El Banco Financiero",0));
    }


}

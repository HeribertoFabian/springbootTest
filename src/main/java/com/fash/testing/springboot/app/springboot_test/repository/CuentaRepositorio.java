package com.fash.testing.springboot.app.springboot_test.repository;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {


}

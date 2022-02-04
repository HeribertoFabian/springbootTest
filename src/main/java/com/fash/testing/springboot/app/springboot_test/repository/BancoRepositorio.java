package com.fash.testing.springboot.app.springboot_test.repository;

import com.fash.testing.springboot.app.springboot_test.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BancoRepositorio extends JpaRepository<Banco, Long> {

}

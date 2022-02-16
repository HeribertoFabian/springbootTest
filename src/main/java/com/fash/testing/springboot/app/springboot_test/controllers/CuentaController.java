package com.fash.testing.springboot.app.springboot_test.controllers;

import com.fash.testing.springboot.app.springboot_test.models.Cuenta;
import com.fash.testing.springboot.app.springboot_test.models.TransaccionDTO;
import com.fash.testing.springboot.app.springboot_test.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    @ResponseStatus(OK)
    public List<Cuenta> listar(){
        return cuentaService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta){
        return cuentaService.save(cuenta);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalle(@PathVariable Long id) {
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    @ResponseStatus(CREATED)
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO transferencia) {
        cuentaService.transferir(transferencia.getCuentaOrigenId(), transferencia.getCuentaDestinoId(),
                transferencia.getMonto(), transferencia.getBancoId());

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con exito");
        response.put("transaccion", transferencia);

        return new ResponseEntity<>(response, OK);

    }

}

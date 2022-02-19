package com.fash.testing.springboot.app.springboot_test.controllers;

import com.fash.testing.springboot.app.springboot_test.models.TransaccionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuentaControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate client;

    private ObjectMapper mapper;

    @LocalServerPort
    private int puerto;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Order(1)
    @Test
    void testTransferir() {
        //Given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setMonto(new BigDecimal("100"));
        dto.setCuentaDestinoId(2L);
        dto.setCuentaOrigenId(1L);
        dto.setBancoId(1L);

        //When
        ResponseEntity<String> response = client.
                postForEntity("http://localhost:"+puerto+"/api/cuentas/transferir", dto, String.class);
                //postForEntity("/api/cuentas/transferir", dto, String.class);

        String json = response.getBody();
        System.out.println(json);
        assertNotNull(json);
        assertTrue(json.contains("Transferencia realizada con exito"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    }
}
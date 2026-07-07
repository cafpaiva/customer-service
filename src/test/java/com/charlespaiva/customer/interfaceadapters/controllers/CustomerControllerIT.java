package com.charlespaiva.customer.interfaceadapters.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste de integração ponta a ponta (HTTP real) — exercita
 * Controller -> Interactor -> Gateway JPA -> banco H2 em memória.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomerControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("deve registrar um cliente e depois consultá-lo por id")
    void deveRegistrarEConsultarCliente() {
        // given
        var request = Map.of(
                "name", "Carlos Pereira",
                "email", "carlos.pereira@example.com",
                "document", "529.982.247-25");

        // when
        ResponseEntity<Map> createResponse = restTemplate.postForEntity(
                url("/api/customers"), request, Map.class);

        // then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String id = (String) createResponse.getBody().get("id");

        // when
        ResponseEntity<Map> getResponse = restTemplate.getForEntity(
                url("/api/customers/" + id), Map.class);

        // then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().get("email")).isEqualTo("carlos.pereira@example.com");
    }

    @Test
    @DisplayName("deve recusar cadastro com email duplicado")
    void deveRecusarEmailDuplicado() {
        // given
        var request = Map.of(
                "name", "Duplicado Teste",
                "email", "duplicado@example.com",
                "document", "111.444.777-35");
        restTemplate.postForEntity(url("/api/customers"), request, Map.class);

        // when
        ResponseEntity<Map> secondResponse = restTemplate.postForEntity(
                url("/api/customers"), request, Map.class);

        // then
        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}

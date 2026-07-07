package com.charlespaiva.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Frameworks & Drivers (círculo 4) — ponto de entrada.
 * Não contém regra de negócio: apenas inicializa o container Spring.
 */
@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}

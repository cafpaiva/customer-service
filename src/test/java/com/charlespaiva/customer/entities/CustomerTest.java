package com.charlespaiva.customer.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Teste unitário puro da Entity — sem Spring, sem mocks. */
class CustomerTest {

    @Nested
    @DisplayName("Customer.register")
    class Register {

        @Test
        @DisplayName("deve criar cliente ACTIVE quando os dados são válidos")
        void deveCriarClienteValido() {
            // given
            Email email = Email.of("ana@example.com");
            Document document = Document.ofCpf("529.982.247-25");

            // when
            Customer customer = Customer.register("Ana Costa", email, document, Instant.now());

            // then
            assertThat(customer.status()).isEqualTo(CustomerStatus.ACTIVE);
            assertThat(customer.name()).isEqualTo("Ana Costa");
        }

        @Test
        @DisplayName("deve rejeitar nome em branco")
        void deveRejeitarNomeEmBranco() {
            assertThrows(IllegalArgumentException.class, () ->
                    Customer.register("  ", Email.of("ana@example.com"),
                            Document.ofCpf("529.982.247-25"), Instant.now()));
        }
    }

    @Nested
    @DisplayName("Customer.deactivate")
    class Deactivate {

        @Test
        @DisplayName("deve rejeitar desativar um cliente já inativo")
        void deveRejeitarDesativarDuasVezes() {
            // given
            Customer customer = Customer.register("Ana Costa", Email.of("ana@example.com"),
                    Document.ofCpf("529.982.247-25"), Instant.now()).deactivate();

            // when / then
            assertThrows(IllegalStateException.class, customer::deactivate);
        }
    }
}

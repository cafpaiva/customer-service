package com.charlespaiva.customer.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void deveNormalizarParaMinusculas() {
        // given / when
        Email email = Email.of("Ana@Example.COM");

        // then
        assertThat(email.value()).isEqualTo("ana@example.com");
    }

    @Test
    void deveRejeitarFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Email.of("nao-e-email"));
    }
}

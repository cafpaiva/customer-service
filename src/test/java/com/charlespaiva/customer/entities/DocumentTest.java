package com.charlespaiva.customer.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentTest {

    @Test
    void deveAceitarCpfComDigitosVerificadoresValidos() {
        // given / when
        Document document = Document.ofCpf("529.982.247-25");

        // then
        assertThat(document.digits()).isEqualTo("52998224725");
    }

    @Test
    void deveRejeitarCpfComTodosDigitosIguais() {
        assertThrows(IllegalArgumentException.class, () -> Document.ofCpf("111.111.111-11"));
    }

    @Test
    void deveRejeitarCpfComDigitoVerificadorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Document.ofCpf("529.982.247-00"));
    }
}

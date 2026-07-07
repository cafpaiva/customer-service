package com.charlespaiva.customer.usecases.registercustomer;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;
import com.charlespaiva.customer.usecases.boundaries.TransactionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Teste unitário do Interactor — sem Spring, sem banco.
 * As boundaries são substituídas por fakes em memória; o TransactionRunner
 * apenas executa o Supplier, simulando uma transação bem-sucedida.
 */
class RegisterCustomerInteractorTest {

    private InMemoryCustomerGateway gateway;
    private TransactionRunner tx;
    private RegisterCustomerInteractor interactor;
    private CapturingOutputPort output;

    @BeforeEach
    void setUp() {
        gateway = new InMemoryCustomerGateway();
        tx = new PassthroughTransactionRunner();
        interactor = new RegisterCustomerInteractor(gateway, tx);
        output = new CapturingOutputPort();
    }

    @Nested
    @DisplayName("quando os dados do cliente são válidos e o email é inédito")
    class NovoCadastro {

        @Test
        @DisplayName("deve registrar o cliente e apresentar o resultado com status ACTIVE")
        void deveRegistrarCliente() {
            // given
            var request = new RegisterCustomerRequestModel(
                    "Maria da Silva", "maria.silva@example.com", "529.982.247-25");

            // when
            interactor.register(request, output);

            // then
            assertThat(output.hasError()).isFalse();
            assertThat(output.response).isNotNull();
            assertThat(output.response.name()).isEqualTo("Maria da Silva");
            assertThat(output.response.email()).isEqualTo("maria.silva@example.com");
            assertThat(output.response.status()).isEqualTo("ACTIVE");
            assertThat(gateway.savedCount()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("quando já existe cliente cadastrado com o mesmo email")
    class EmailDuplicado {

        @Test
        @DisplayName("não deve salvar novamente e deve apresentar erro de duplicidade")
        void deveRecusarEmailDuplicado() {
            // given
            gateway.existingEmails.add("maria.silva@example.com");
            var request = new RegisterCustomerRequestModel(
                    "Maria da Silva", "maria.silva@example.com", "529.982.247-25");

            // when
            interactor.register(request, output);

            // then
            assertThat(output.hasError()).isTrue();
            assertThat(gateway.savedCount()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("quando os dados do cliente violam um invariante da Entity")
    class DadosInvalidos {

        @Test
        @DisplayName("deve propagar a exceção de validação sem persistir nada")
        void deveRecusarDocumentoInvalido() {
            // given
            var request = new RegisterCustomerRequestModel(
                    "Maria da Silva", "maria.silva@example.com", "111.111.111-11");

            // when / then
            org.junit.jupiter.api.Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> interactor.register(request, output));
            assertThat(gateway.savedCount()).isEqualTo(0);
        }
    }

    /** Fake de CustomerGateway — substitui a implementação JPA nos testes de unidade. */
    private static class InMemoryCustomerGateway implements CustomerGateway {
        private final Map<UUID, Customer> storage = new HashMap<>();
        private final java.util.Set<String> existingEmails = new java.util.HashSet<>();

        @Override
        public Customer save(Customer customer) {
            storage.put(customer.id(), customer);
            return customer;
        }

        @Override
        public Optional<Customer> findById(UUID id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public boolean existsByEmail(String email) {
            return existingEmails.contains(email) || storage.values().stream()
                    .anyMatch(c -> c.email().value().equals(email));
        }

        int savedCount() {
            return storage.size();
        }
    }

    /** TransactionRunner que apenas executa o Supplier — sem transação real. */
    private static class PassthroughTransactionRunner implements TransactionRunner {
        @Override
        public <T> T execute(Supplier<T> work) {
            return work.get();
        }
    }

    /** Output Port de captura — evita depender de Mockito para asserts simples. */
    private static class CapturingOutputPort implements RegisterCustomerOutputPort {
        RegisterCustomerResponseModel response;
        String duplicateEmail;

        @Override
        public void present(RegisterCustomerResponseModel response) {
            this.response = response;
        }

        @Override
        public void presentDuplicateEmail(String email) {
            this.duplicateEmail = email;
        }

        boolean hasError() {
            return duplicateEmail != null;
        }
    }
}

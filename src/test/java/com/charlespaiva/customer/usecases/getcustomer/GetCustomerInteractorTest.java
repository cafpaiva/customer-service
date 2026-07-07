package com.charlespaiva.customer.usecases.getcustomer;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.entities.Document;
import com.charlespaiva.customer.entities.Email;
import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GetCustomerInteractorTest {

    private InMemoryCustomerGateway gateway;
    private GetCustomerInteractor interactor;
    private CapturingOutputPort output;

    @BeforeEach
    void setUp() {
        gateway = new InMemoryCustomerGateway();
        interactor = new GetCustomerInteractor(gateway);
        output = new CapturingOutputPort();
    }

    @Nested
    @DisplayName("quando o cliente existe")
    class ClienteExistente {

        @Test
        @DisplayName("deve apresentar os dados do cliente")
        void deveApresentarCliente() {
            // given
            Customer customer = Customer.register(
                    "João Souza", Email.of("joao.souza@example.com"),
                    Document.ofCpf("529.982.247-25"), Instant.now());
            gateway.storage.put(customer.id(), customer);

            // when
            interactor.get(new GetCustomerRequestModel(customer.id()), output);

            // then
            assertThat(output.hasError()).isFalse();
            assertThat(output.response.id()).isEqualTo(customer.id());
            assertThat(output.response.email()).isEqualTo("joao.souza@example.com");
        }
    }

    @Nested
    @DisplayName("quando o cliente não existe")
    class ClienteInexistente {

        @Test
        @DisplayName("deve apresentar not found e não lançar exceção")
        void deveApresentarNotFound() {
            // given
            UUID idInexistente = UUID.randomUUID();

            // when
            interactor.get(new GetCustomerRequestModel(idInexistente), output);

            // then
            assertThat(output.hasError()).isTrue();
            assertThat(output.notFoundId).isEqualTo(idInexistente);
            assertThat(output.response).isNull();
        }
    }

    private static class InMemoryCustomerGateway implements CustomerGateway {
        private final Map<UUID, Customer> storage = new HashMap<>();

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
            return storage.values().stream().anyMatch(c -> c.email().value().equals(email));
        }
    }

    private static class CapturingOutputPort implements GetCustomerOutputPort {
        GetCustomerResponseModel response;
        UUID notFoundId;

        @Override
        public void present(GetCustomerResponseModel response) {
            this.response = response;
        }

        @Override
        public void presentNotFound(UUID id) {
            this.notFoundId = id;
        }

        boolean hasError() {
            return notFoundId != null;
        }
    }
}

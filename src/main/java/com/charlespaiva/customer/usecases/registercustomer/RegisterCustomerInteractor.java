package com.charlespaiva.customer.usecases.registercustomer;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.entities.Document;
import com.charlespaiva.customer.entities.Email;
import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;
import com.charlespaiva.customer.usecases.boundaries.TransactionRunner;

import java.time.Instant;

/**
 * Use Cases (círculo 2) — Application Business Rule.
 * POJO puro: nenhuma anotação de framework (@Service, @Transactional etc.).
 * Orquestra a Entity e as boundaries; termina entregando o resultado ao
 * Output Port (Presenter).
 */
public class RegisterCustomerInteractor implements RegisterCustomerInputPort {

    private final CustomerGateway gateway;
    private final TransactionRunner tx;

    public RegisterCustomerInteractor(CustomerGateway gateway, TransactionRunner tx) {
        this.gateway = gateway;
        this.tx = tx;
    }

    @Override
    public void register(RegisterCustomerRequestModel request, RegisterCustomerOutputPort out) {
        if (gateway.existsByEmail(request.email())) {
            out.presentDuplicateEmail(request.email());
            return;
        }

        RegisterCustomerResponseModel response = tx.execute(() -> {
            Customer customer = Customer.register(
                    request.name(),
                    Email.of(request.email()),
                    Document.ofCpf(request.document()),
                    Instant.now());

            Customer saved = gateway.save(customer);

            return new RegisterCustomerResponseModel(
                    saved.id(),
                    saved.name(),
                    saved.email().value(),
                    saved.status().name(),
                    saved.registeredAt());
        });

        out.present(response);
    }
}

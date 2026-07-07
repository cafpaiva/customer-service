package com.charlespaiva.customer.usecases.boundaries;

import com.charlespaiva.customer.entities.Customer;

import java.util.Optional;
import java.util.UUID;

/**
 * Boundary (porta de saída) usada pelos Interactors para persistir/consultar
 * clientes, sem conhecer JPA, SQL ou qualquer detalhe de infraestrutura.
 * Implementada em interfaceadapters.gateways.
 */
public interface CustomerGateway {

    Customer save(Customer customer);

    Optional<Customer> findById(UUID id);

    boolean existsByEmail(String email);
}

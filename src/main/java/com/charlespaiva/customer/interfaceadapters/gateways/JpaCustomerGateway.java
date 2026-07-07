package com.charlespaiva.customer.interfaceadapters.gateways;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface Adapters (círculo 3) — implementação da boundary CustomerGateway
 * usando Spring Data JPA. Todo o SQL/ORM fica isolado aqui dentro.
 */
@Component
class JpaCustomerGateway implements CustomerGateway {

    private final SpringDataCustomerRepository repository;
    private final CustomerMapper mapper;

    JpaCustomerGateway(SpringDataCustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Customer save(Customer customer) {
        var saved = repository.save(mapper.toJpaEntity(customer));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}

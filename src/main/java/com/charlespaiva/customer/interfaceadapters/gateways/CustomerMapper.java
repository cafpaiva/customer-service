package com.charlespaiva.customer.interfaceadapters.gateways;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.entities.CustomerStatus;
import com.charlespaiva.customer.entities.Document;
import com.charlespaiva.customer.entities.Email;

/**
 * Interface Adapters (círculo 3) — tradução domínio <-> entidade JPA.
 * Ponto único onde `entities.Customer` e `CustomerJpaEntity` se conhecem.
 */
class CustomerMapper {

    CustomerJpaEntity toJpaEntity(Customer customer) {
        return new CustomerJpaEntity(
                customer.id(),
                customer.name(),
                customer.email().value(),
                customer.document().digits(),
                customer.status().name(),
                customer.registeredAt());
    }

    Customer toDomain(CustomerJpaEntity jpaEntity) {
        return Customer.reconstitute(
                jpaEntity.getId(),
                jpaEntity.getName(),
                Email.of(jpaEntity.getEmail()),
                Document.ofCpf(jpaEntity.getDocument()),
                CustomerStatus.valueOf(jpaEntity.getStatus()),
                jpaEntity.getRegisteredAt());
    }
}

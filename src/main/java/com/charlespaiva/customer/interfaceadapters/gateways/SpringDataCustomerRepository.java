package com.charlespaiva.customer.interfaceadapters.gateways;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface SpringDataCustomerRepository extends JpaRepository<CustomerJpaEntity, UUID> {
    boolean existsByEmail(String email);
}

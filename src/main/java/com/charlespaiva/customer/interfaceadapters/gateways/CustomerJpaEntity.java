package com.charlespaiva.customer.interfaceadapters.gateways;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

/**
 * Interface Adapters (círculo 3) — entidade de persistência (JPA).
 * Vive aqui, nunca em `entities`. É a tradução do domínio para o modelo
 * relacional; possui getters/setters mutáveis por exigência do Hibernate.
 */
@Entity
@Table(name = "customers")
class CustomerJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String document;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private Instant registeredAt;

    protected CustomerJpaEntity() {
        // exigido pelo JPA
    }

    CustomerJpaEntity(UUID id, String name, String email, String document, String status, Instant registeredAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    UUID getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    String getDocument() {
        return document;
    }

    String getStatus() {
        return status;
    }

    Instant getRegisteredAt() {
        return registeredAt;
    }
}

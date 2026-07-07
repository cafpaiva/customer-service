package com.charlespaiva.customer.entities;

import java.time.Instant;
import java.util.UUID;

/**
 * Entities (círculo 1) — Enterprise Business Rules.
 * POJO puro: nenhum import de Spring, JPA, Jackson ou qualquer framework.
 * Concentra os invariantes do cliente e as transições de estado válidas.
 */
public final class Customer {

    private final UUID id;
    private final String name;
    private final Email email;
    private final Document document;
    private final CustomerStatus status;
    private final Instant registeredAt;

    private Customer(UUID id, String name, Email email, Document document,
                      CustomerStatus status, Instant registeredAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.document = document;
        this.status = status;
        this.registeredAt = registeredAt;
    }

    /** Fábrica de criação: aplica os invariantes de um novo cadastro. */
    public static Customer register(String name, Email email, Document document, Instant now) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (name.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter ao menos 3 caracteres");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (document == null) {
            throw new IllegalArgumentException("Documento é obrigatório");
        }
        return new Customer(UUID.randomUUID(), name.trim(), email, document, CustomerStatus.ACTIVE, now);
    }

    /** Reconstrói um Customer já existente (usado pelos Gateways ao ler do banco). */
    public static Customer reconstitute(UUID id, String name, Email email, Document document,
                                         CustomerStatus status, Instant registeredAt) {
        return new Customer(id, name, email, document, status, registeredAt);
    }

    public Customer deactivate() {
        if (status == CustomerStatus.INACTIVE) {
            throw new IllegalStateException("Cliente já está inativo");
        }
        return new Customer(id, name, email, document, CustomerStatus.INACTIVE, registeredAt);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public Document document() {
        return document;
    }

    public CustomerStatus status() {
        return status;
    }

    public Instant registeredAt() {
        return registeredAt;
    }
}

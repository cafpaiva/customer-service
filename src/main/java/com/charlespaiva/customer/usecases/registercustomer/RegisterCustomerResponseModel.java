package com.charlespaiva.customer.usecases.registercustomer;

import java.time.Instant;
import java.util.UUID;

/**
 * Use Cases (círculo 2) — estrutura de dados simples que cruza a fronteira
 * Interactor -> Presenter. Nunca é uma Entity.
 */
public record RegisterCustomerResponseModel(UUID id, String name, String email,
                                             String status, Instant registeredAt) {
}

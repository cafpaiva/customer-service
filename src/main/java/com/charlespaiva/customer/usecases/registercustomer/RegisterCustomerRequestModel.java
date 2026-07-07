package com.charlespaiva.customer.usecases.registercustomer;

/**
 * Use Cases (círculo 2) — estrutura de dados simples que cruza a fronteira
 * Controller -> Interactor. Nunca é uma Entity.
 */
public record RegisterCustomerRequestModel(String name, String email, String document) {
}

package com.charlespaiva.customer.usecases.boundaries;

import java.util.function.Supplier;

/**
 * Boundary que permite a um Interactor executar múltiplas operações de
 * Gateway de forma atômica, sem depender de @Transactional (anotação de
 * framework) dentro do núcleo da aplicação.
 */
public interface TransactionRunner {
    <T> T execute(Supplier<T> work);
}

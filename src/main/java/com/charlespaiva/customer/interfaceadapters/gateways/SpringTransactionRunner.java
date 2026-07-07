package com.charlespaiva.customer.interfaceadapters.gateways;

import com.charlespaiva.customer.usecases.boundaries.TransactionRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

/**
 * Interface Adapters (círculo 3) — implementação de TransactionRunner
 * usando o gerenciador de transações do Spring. Mantém @Transactional
 * fora do núcleo (usecases/entities).
 */
@Component
class SpringTransactionRunner implements TransactionRunner {

    private final TransactionTemplate template;

    SpringTransactionRunner(PlatformTransactionManager transactionManager) {
        this.template = new TransactionTemplate(transactionManager);
    }

    @Override
    public <T> T execute(Supplier<T> work) {
        return template.execute(status -> work.get());
    }
}

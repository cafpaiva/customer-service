package com.charlespaiva.customer.frameworksdrivers.config;

import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;
import com.charlespaiva.customer.usecases.boundaries.TransactionRunner;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerInputPort;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerInteractor;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerInputPort;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Frameworks & Drivers (círculo 4) — único lugar onde os Interactors
 * (POJOs puros, sem anotação) são instanciados e registrados como beans,
 * recebendo as implementações concretas das boundaries por injeção.
 */
@Configuration
class UseCaseConfig {

    @Bean
    RegisterCustomerInputPort registerCustomerInputPort(CustomerGateway gateway, TransactionRunner tx) {
        return new RegisterCustomerInteractor(gateway, tx);
    }

    @Bean
    GetCustomerInputPort getCustomerInputPort(CustomerGateway gateway) {
        return new GetCustomerInteractor(gateway);
    }
}

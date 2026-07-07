package com.charlespaiva.customer.usecases.getcustomer;

import com.charlespaiva.customer.entities.Customer;
import com.charlespaiva.customer.usecases.boundaries.CustomerGateway;

/**
 * Use Cases (círculo 2) — POJO puro. Consulta é apenas leitura, portanto
 * não precisa do TransactionRunner.
 */
public class GetCustomerInteractor implements GetCustomerInputPort {

    private final CustomerGateway gateway;

    public GetCustomerInteractor(CustomerGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void get(GetCustomerRequestModel request, GetCustomerOutputPort out) {
        gateway.findById(request.id())
                .map(this::toResponseModel)
                .ifPresentOrElse(out::present, () -> out.presentNotFound(request.id()));
    }

    private GetCustomerResponseModel toResponseModel(Customer customer) {
        return new GetCustomerResponseModel(
                customer.id(),
                customer.name(),
                customer.email().value(),
                customer.document().digits(),
                customer.status().name(),
                customer.registeredAt());
    }
}

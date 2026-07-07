package com.charlespaiva.customer.usecases.registercustomer;

/** Input Port — porta de entrada chamada pelo Controller. */
public interface RegisterCustomerInputPort {
    void register(RegisterCustomerRequestModel request, RegisterCustomerOutputPort outputPort);
}

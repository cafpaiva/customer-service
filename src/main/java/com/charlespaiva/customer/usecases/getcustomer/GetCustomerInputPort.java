package com.charlespaiva.customer.usecases.getcustomer;

public interface GetCustomerInputPort {
    void get(GetCustomerRequestModel request, GetCustomerOutputPort outputPort);
}

package com.charlespaiva.customer.usecases.getcustomer;

/** Output Port — implementado por um Presenter em interfaceadapters. */
public interface GetCustomerOutputPort {

    void present(GetCustomerResponseModel response);

    void presentNotFound(java.util.UUID id);
}

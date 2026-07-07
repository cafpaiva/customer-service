package com.charlespaiva.customer.usecases.registercustomer;

/**
 * Output Port — implementado por um Presenter em interfaceadapters.
 * O Interactor termina seu fluxo entregando o resultado aqui.
 */
public interface RegisterCustomerOutputPort {

    void present(RegisterCustomerResponseModel response);

    void presentDuplicateEmail(String email);
}

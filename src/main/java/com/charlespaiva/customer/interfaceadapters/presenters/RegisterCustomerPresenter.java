package com.charlespaiva.customer.interfaceadapters.presenters;

import com.charlespaiva.customer.interfaceadapters.viewmodels.CustomerViewModel;
import com.charlespaiva.customer.interfaceadapters.viewmodels.ErrorViewModel;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerOutputPort;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerResponseModel;

/**
 * Interface Adapters (círculo 3) — implementa o Output Port do caso de uso
 * e traduz o ResponseModel para o View Model consumido pelo Controller.
 */
public class RegisterCustomerPresenter implements RegisterCustomerOutputPort {

    private CustomerViewModel viewModel;
    private ErrorViewModel error;

    @Override
    public void present(RegisterCustomerResponseModel response) {
        this.viewModel = new CustomerViewModel(
                response.id(), response.name(), response.email(),
                response.status(), response.registeredAt());
    }

    @Override
    public void presentDuplicateEmail(String email) {
        this.error = new ErrorViewModel("DUPLICATE_EMAIL", "Já existe cliente cadastrado com o email " + email);
    }

    public CustomerViewModel viewModel() {
        return viewModel;
    }

    public ErrorViewModel error() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}

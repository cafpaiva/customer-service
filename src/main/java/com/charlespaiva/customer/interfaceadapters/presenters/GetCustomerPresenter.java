package com.charlespaiva.customer.interfaceadapters.presenters;

import com.charlespaiva.customer.interfaceadapters.viewmodels.CustomerDetailViewModel;
import com.charlespaiva.customer.interfaceadapters.viewmodels.ErrorViewModel;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerOutputPort;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerResponseModel;

import java.util.UUID;

public class GetCustomerPresenter implements GetCustomerOutputPort {

    private CustomerDetailViewModel viewModel;
    private ErrorViewModel error;

    @Override
    public void present(GetCustomerResponseModel response) {
        this.viewModel = new CustomerDetailViewModel(
                response.id(), response.name(), response.email(), response.document(),
                response.status(), response.registeredAt());
    }

    @Override
    public void presentNotFound(UUID id) {
        this.error = new ErrorViewModel("CUSTOMER_NOT_FOUND", "Cliente não encontrado: " + id);
    }

    public CustomerDetailViewModel viewModel() {
        return viewModel;
    }

    public ErrorViewModel error() {
        return error;
    }

    public boolean hasError() {
        return error != null;
    }
}

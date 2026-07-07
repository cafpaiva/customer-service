package com.charlespaiva.customer.interfaceadapters.controllers;

import com.charlespaiva.customer.interfaceadapters.presenters.GetCustomerPresenter;
import com.charlespaiva.customer.interfaceadapters.presenters.RegisterCustomerPresenter;
import com.charlespaiva.customer.interfaceadapters.viewmodels.ErrorViewModel;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerInputPort;
import com.charlespaiva.customer.usecases.getcustomer.GetCustomerRequestModel;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerInputPort;
import com.charlespaiva.customer.usecases.registercustomer.RegisterCustomerRequestModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

/**
 * Interface Adapters (círculo 3) — Controller.
 * Papel: montar o RequestModel, criar o Presenter, chamar o Input Port
 * e ler o View Model já pronto. Nenhuma regra de negócio mora aqui.
 */
@RestController
@RequestMapping("/api/customers")
class CustomerController {

    private final RegisterCustomerInputPort registerCustomer;
    private final GetCustomerInputPort getCustomer;

    CustomerController(RegisterCustomerInputPort registerCustomer, GetCustomerInputPort getCustomer) {
        this.registerCustomer = registerCustomer;
        this.getCustomer = getCustomer;
    }

    @PostMapping
    ResponseEntity<?> register(@Valid @RequestBody RegisterCustomerRequest body) {
        var presenter = new RegisterCustomerPresenter();
        registerCustomer.register(
                new RegisterCustomerRequestModel(body.name(), body.email(), body.document()),
                presenter);

        if (presenter.hasError()) {
            return ResponseEntity.status(409).body(presenter.error());
        }
        var viewModel = presenter.viewModel();
        return ResponseEntity.created(URI.create("/api/customers/" + viewModel.id())).body(viewModel);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable UUID id) {
        var presenter = new GetCustomerPresenter();
        getCustomer.get(new GetCustomerRequestModel(id), presenter);

        if (presenter.hasError()) {
            ErrorViewModel error = presenter.error();
            return ResponseEntity.status(404).body(error);
        }
        return ResponseEntity.ok(presenter.viewModel());
    }
}

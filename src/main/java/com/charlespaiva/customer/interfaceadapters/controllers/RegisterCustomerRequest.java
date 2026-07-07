package com.charlespaiva.customer.interfaceadapters.controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/** DTO web de entrada — só existe na borda HTTP (Interface Adapters). */
public record RegisterCustomerRequest(
        @Schema(example = "Maria da Silva") @NotBlank String name,
        @Schema(example = "maria.silva@example.com") @NotBlank String email,
        @Schema(example = "529.982.247-25") @NotBlank String document) {
}

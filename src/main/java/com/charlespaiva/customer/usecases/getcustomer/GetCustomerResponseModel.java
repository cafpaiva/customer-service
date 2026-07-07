package com.charlespaiva.customer.usecases.getcustomer;

import java.time.Instant;
import java.util.UUID;

public record GetCustomerResponseModel(UUID id, String name, String email,
                                        String document, String status, Instant registeredAt) {
}

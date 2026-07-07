package com.charlespaiva.customer.interfaceadapters.viewmodels;

import java.time.Instant;
import java.util.UUID;

public record CustomerDetailViewModel(UUID id, String name, String email, String document,
                                       String status, Instant registeredAt) {
}

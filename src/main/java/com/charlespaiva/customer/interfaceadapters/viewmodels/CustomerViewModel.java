package com.charlespaiva.customer.interfaceadapters.viewmodels;

import java.time.Instant;
import java.util.UUID;

/** View Model — formato final consumido pelo Controller/JSON de resposta. */
public record CustomerViewModel(UUID id, String name, String email, String status,
                                 Instant registeredAt) {
}

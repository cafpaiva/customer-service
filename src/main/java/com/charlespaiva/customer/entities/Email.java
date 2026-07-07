package com.charlespaiva.customer.entities;

import java.util.regex.Pattern;

/**
 * Entities (círculo 1) — Value Object. Java puro, sem framework.
 * Garante o invariante de formato de e-mail em qualquer ponto do sistema
 * onde um Email exista, independente de quem o criou.
 */
public final class Email {

    private static final Pattern FORMAT =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email of(String rawValue) {
        if (rawValue == null || rawValue.isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        String normalized = rawValue.trim().toLowerCase();
        if (!FORMAT.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Email inválido: " + rawValue);
        }
        return new Email(normalized);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}

package com.charlespaiva.customer.entities;

/**
 * Entities (círculo 1) — Value Object que representa o CPF do cliente.
 * Aplica o invariante de formato/dígitos verificadores. Java puro.
 */
public final class Document {

    private final String digits;

    private Document(String digits) {
        this.digits = digits;
    }

    public static Document ofCpf(String rawValue) {
        if (rawValue == null) {
            throw new IllegalArgumentException("CPF não pode ser vazio");
        }
        String digits = rawValue.replaceAll("\\D", "");
        if (digits.length() != 11 || allDigitsEqual(digits)) {
            throw new IllegalArgumentException("CPF inválido: " + rawValue);
        }
        if (!hasValidCheckDigits(digits)) {
            throw new IllegalArgumentException("CPF inválido: " + rawValue);
        }
        return new Document(digits);
    }

    private static boolean allDigitsEqual(String digits) {
        return digits.chars().distinct().count() == 1;
    }

    private static boolean hasValidCheckDigits(String digits) {
        int firstCheck = checkDigit(digits.substring(0, 9), 10);
        int secondCheck = checkDigit(digits.substring(0, 9) + firstCheck, 11);
        return digits.charAt(9) - '0' == firstCheck && digits.charAt(10) - '0' == secondCheck;
    }

    private static int checkDigit(String base, int firstWeight) {
        int sum = 0;
        int weight = firstWeight;
        for (char c : base.toCharArray()) {
            sum += (c - '0') * weight--;
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    public String digits() {
        return digits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Document other)) return false;
        return digits.equals(other.digits);
    }

    @Override
    public int hashCode() {
        return digits.hashCode();
    }

    @Override
    public String toString() {
        return digits;
    }
}

package com.itx.technicalTest.domain.models.valueObjects;

public record ProductName(String name) {
    public ProductName {
        checkNameLengthIsGreaterThanZero(name);
    }

    private void checkNameLengthIsGreaterThanZero(String name) {
        if (name == null || name.length() == 0) {
            throw new IllegalArgumentException("Name length must be greater than 0. Received: " + name);
        }

    }
}

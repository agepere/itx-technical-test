package com.itx.technicalTest.domain.models.valueObjects;

public record ProductSales(Integer sales) {
    public ProductSales {
        checkSalesIsGreaterThanZero(sales);
    }

    private void checkSalesIsGreaterThanZero(Integer sales) {
        if (sales == null || sales < 0) {
            throw new IllegalArgumentException("Sales number must be positive. Received: " + sales);
        }
    }
}

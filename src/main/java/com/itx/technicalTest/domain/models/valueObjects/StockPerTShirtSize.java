package com.itx.technicalTest.domain.models.valueObjects;

import java.util.Objects;


public record StockPerTShirtSize(TShirtSize size, Integer stock) {


    public StockPerTShirtSize {
        checkStockIsGreaterThanZero(stock);
    }

    private void checkStockIsGreaterThanZero(Integer stock) {
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock number must be positive. Received: " + stock);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockPerTShirtSize stock = (StockPerTShirtSize) o;
        return size == stock.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}

package com.itx.technicalTest.domain.models.valueObjects;

import lombok.Getter;

import java.util.Objects;


public record Stock(Size size, Integer stock) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return size == stock.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size);
    }

}

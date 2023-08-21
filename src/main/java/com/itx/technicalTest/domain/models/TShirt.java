package com.itx.technicalTest.domain.models;

import com.itx.technicalTest.domain.models.valueObjects.ProductType;
import com.itx.technicalTest.domain.models.valueObjects.TShirtSize;
import com.itx.technicalTest.domain.models.valueObjects.StockPerTShirtSize;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TShirt extends Product {
    public static final String TSHIRT_TYPE = ProductType.TSHIRT.name();

    private Set<StockPerTShirtSize> stockSet;

    public TShirt(String id, String name, Integer sales) {
        super(id, name, sales, TSHIRT_TYPE);
        this.stockSet = new HashSet<>();
    }

    public void addStockPerSize(String size, Integer stock) {
        StockPerTShirtSize stockPerSize = new StockPerTShirtSize(valueOfSize(size), stock);
        boolean added = this.stockSet.add(stockPerSize);
        checkStockIsAdded(added);
    }

    private void checkStockIsAdded(boolean added) {
        if (!added) {
            throw new IllegalArgumentException("All sizes must be different.");
        }
    }

    private TShirtSize valueOfSize(String size) {
        try {
            return TShirtSize.valueOf(size);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("This size is not allowed for this t-shirt. Received: " + size + ". Accepted sizes: " + Arrays.stream(TShirtSize.values()).toList());
        }
    }
}

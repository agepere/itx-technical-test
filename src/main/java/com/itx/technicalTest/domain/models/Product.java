package com.itx.technicalTest.domain.models;

import com.itx.technicalTest.domain.models.valueObjects.ProductName;
import com.itx.technicalTest.domain.models.valueObjects.ProductSales;
import com.itx.technicalTest.domain.models.valueObjects.Size;
import com.itx.technicalTest.domain.models.valueObjects.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Product {
    private final String id;
    private ProductName name;
    private ProductSales sales;
    private Set<Stock> stockSet;

    public Product(String id, String name, Integer sales) {
        this.id = id;
        this.name = new ProductName(name);
        this.sales = new ProductSales(sales);
        this.stockSet = new HashSet<>();
    }

    public void addStockPerSize(String size, Integer stock) {
        Stock stockPerSize = new Stock(Size.valueOf(size), stock);
        this.stockSet.add(stockPerSize);
    }

}

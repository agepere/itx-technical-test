package com.itx.technicalTest.domain.models;

import com.itx.technicalTest.domain.models.valueObjects.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Product {
    private final String id;
    private ProductName name;
    private ProductSales sales;
    private ProductType type;

    public Product(String id, String name, Integer sales, String type) {
        this.id = id;
        this.name = new ProductName(name);
        this.sales = new ProductSales(sales);
        this.type = ProductType.valueOf(type);
    }


}

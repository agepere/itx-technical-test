package com.itx.technicalTest.application.useCases;

import com.itx.technicalTest.domain.models.Product;

import java.util.List;

public interface ProductUseCases {
    Iterable<Product> getAll(Integer page, Integer size);

    Product create(Product product);
}

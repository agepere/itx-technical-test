package com.itx.technicalTest.application.useCases;

import com.itx.technicalTest.domain.models.Product;

public interface ProductUseCases {
    Iterable<Product> getAllSortedByScore(Integer page, Integer size);

    Product create(Product product);
}

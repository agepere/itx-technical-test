package com.itx.technicalTest.application.useCases;

import com.itx.technicalTest.domain.models.Product;

public interface ProductUseCases {
    Iterable<Product> getAllSortedByScore(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size);

    Iterable<Product> getAllSortedByScoreNative(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size);

    Product create(Product product);
}

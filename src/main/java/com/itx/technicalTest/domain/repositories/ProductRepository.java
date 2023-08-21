package com.itx.technicalTest.domain.repositories;

import com.itx.technicalTest.domain.models.Product;

public interface ProductRepository {

    Iterable<Product> findAllSortedByScore(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size);

    Iterable<Product> findAllSortedByScoreNative(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size);

    Product save(Product product);

}

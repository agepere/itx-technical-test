package com.itx.technicalTest.domain.repositories;

import com.itx.technicalTest.domain.models.Product;

public interface ProductRepository {

    Iterable<Product> findAllSortedByScore(Integer page, Integer size);

    Product save(Product product);

}

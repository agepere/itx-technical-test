package com.itx.technicalTest.domain.repositories;

import com.itx.technicalTest.domain.models.Product;

import java.util.List;

public interface ProductRepository {

    Iterable<Product> findAll(Integer page, Integer size);

    Product save(Product product);

}

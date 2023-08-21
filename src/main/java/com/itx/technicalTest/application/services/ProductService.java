package com.itx.technicalTest.application.services;

import com.itx.technicalTest.application.useCases.ProductUseCases;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ProductService implements ProductUseCases {

    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> getAllSortedByScore(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size) {
        return this.productRepository.findAllSortedByScore(salesScoreRatio, stockScoreRatio, page, size);
    }

    @Override
    public Iterable<Product> getAllSortedByScoreNative(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size) {
        return this.productRepository.findAllSortedByScoreNative(salesScoreRatio, stockScoreRatio, page, size);
    }

    @Override
    public Product create(Product product) {
        return this.productRepository.save(product);
    }
}

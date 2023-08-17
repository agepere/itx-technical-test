package com.itx.technicalTest.infrastructure.adapters;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.entities.mappers.ProductEntityMapper;
import com.itx.technicalTest.infrastructure.repositories.MongoProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoProductRepositoryAdapter implements ProductRepository {

    private final MongoProductRepository mongoProductRepository;

    @Autowired
    private ProductEntityMapper productEntityMapper;

    public MongoProductRepositoryAdapter(MongoProductRepository mongoProductRepository) {
        this.mongoProductRepository = mongoProductRepository;
    }


    @Override
    public List<Product> findAll(Integer page, Integer size) {
        Pageable pageParams = PageRequest.of(page,size);
        return this.mongoProductRepository.findAll(pageParams).stream().map(productEntityMapper::toDomain).toList();
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productEntityMapper.toDbo(product);
        ProductEntity savedProductEntity = this.mongoProductRepository.save(productEntity);

        return productEntityMapper.toDomain(savedProductEntity);

    }
}

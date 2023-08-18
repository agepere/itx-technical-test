package com.itx.technicalTest.infrastructure.adapters;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.entities.mappers.ProductEntityMapper;
import com.itx.technicalTest.infrastructure.repositories.MongoProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoProductRepositoryAdapter implements ProductRepository {

    private final MongoProductRepository mongoProductRepository;

    public MongoProductRepositoryAdapter(MongoProductRepository mongoProductRepository) {
        this.mongoProductRepository = mongoProductRepository;
    }


    @Override
    public List<Product> findAll(Integer page, Integer size) {
        Pageable pageParams = PageRequest.of(page, size);
        return this.mongoProductRepository.findAll(pageParams).stream().map(ProductEntityMapper::toDomainModel).toList();
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductEntityMapper.fromDomainModel(product);
        ProductEntity savedProductEntity = this.mongoProductRepository.save(productEntity);

        return ProductEntityMapper.toDomainModel(savedProductEntity);

    }
}

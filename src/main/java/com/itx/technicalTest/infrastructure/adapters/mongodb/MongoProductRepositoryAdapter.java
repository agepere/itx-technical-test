package com.itx.technicalTest.infrastructure.adapters.mongodb;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import com.itx.technicalTest.infrastructure.adapters.mongodb.aggregations.ProductScoreAggregationUtils;
import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.data.mappers.ProductMapper;
import com.itx.technicalTest.infrastructure.repositories.MongoProductRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoProductRepositoryAdapter implements ProductRepository {

    private final MongoProductRepository mongoProductRepository;
    private final MongoTemplate mongoTemplate;

    private static final String PRODUCT_COLLECTION = "products";

    public MongoProductRepositoryAdapter(MongoProductRepository mongoProductRepository, MongoTemplate mongoTemplate) {
        this.mongoProductRepository = mongoProductRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<Product> findAllSortedByScore(Double salesScoreRatio, Double stockScoreRatio, Integer page, Integer size) {
        Aggregation aggregation = Aggregation.newAggregation(
                ProductScoreAggregationUtils.removeStockLinesOutOfStock(),
                ProductScoreAggregationUtils.calculateTotalScore(salesScoreRatio, stockScoreRatio),
                ProductScoreAggregationUtils.sortByTotalScore(),
                Aggregation.skip(page * (long) size),
                Aggregation.limit(size)

        );

        return this.mongoTemplate.aggregate(aggregation, PRODUCT_COLLECTION, ProductEntity.class).getMappedResults()
                .stream().map(ProductMapper::toDomainModel).toList();

    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.fromDomainModelToDAO(product);
        ProductEntity savedProductEntity = this.mongoProductRepository.save(productEntity);

        return ProductMapper.toDomainModel(savedProductEntity);

    }
}

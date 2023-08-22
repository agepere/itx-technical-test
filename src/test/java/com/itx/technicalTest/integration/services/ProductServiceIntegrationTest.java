package com.itx.technicalTest.integration.services;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.infrastructure.adapters.mongodb.MongoProductRepositoryAdapter;
import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.repositories.MongoProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductServiceIntegrationTest {

    ProductService productService;

    MongoTemplate mongoTemplate;

    @BeforeEach
    void setup() {
        this.mongoTemplate = Mockito.mock(MongoTemplate.class);
        MongoProductRepository mongoProductRepository = Mockito.mock(MongoProductRepository.class);
        MongoProductRepositoryAdapter mongoProductRepositoryAdapter = new MongoProductRepositoryAdapter(mongoProductRepository, mongoTemplate);

        this.productService = new ProductService(mongoProductRepositoryAdapter);


        AggregationResults<ProductEntity> aggregationResults = mock(AggregationResults.class);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("tshirt");
        productEntity.setType("TSHIRT");
        productEntity.setId("1");
        productEntity.setSales(10);
        productEntity.setStockLines(new ArrayList<>());

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(productEntity);

        when(mongoTemplate.aggregate(any(Aggregation.class), eq("products"), eq(ProductEntity.class))).thenReturn(aggregationResults);
        when(aggregationResults.getMappedResults()).thenReturn(productEntities);

    }

    @Test
    void serviceMappersAdapterIntegration()  {
        List<Product> result = (List<Product>) this.productService.getAllSortedByScore(1d, 1d, 0, 10);

        assertEquals(1, result.size(), "Product Service - Unit Test - Incorrect size of response");
        assertEquals("tshirt", result.get(0).getName().name(), "Product Service - Unit Test - Incorrect name");


        verify(mongoTemplate, description("Product Service - Integration test - Wrong Aggregation call"))
                .aggregate(any(Aggregation.class), eq("products"), eq(ProductEntity.class));


    }
}

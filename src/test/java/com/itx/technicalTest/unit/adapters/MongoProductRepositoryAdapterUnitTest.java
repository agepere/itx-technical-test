package com.itx.technicalTest.unit.adapters;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.infrastructure.adapters.mongodb.MongoProductRepositoryAdapter;
import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.data.mappers.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MongoProductRepositoryAdapterUnitTest {

    @InjectMocks
    MongoProductRepositoryAdapter mongoProductRepositoryAdapter;

    @Mock
    MongoTemplate mongoTemplate;

    MockedStatic<ProductMapper> productMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        AggregationResults<ProductEntity> aggregationResults = mock(AggregationResults.class);
        this.productMapper = Mockito.mockStatic(ProductMapper.class);

        TShirt tShirt = new TShirt("1", "tshirt", 10);


        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("tshirt");
        productEntity.setId("1");
        productEntity.setSales(10);

        List<ProductEntity> productEntities = new ArrayList<>();
        productEntities.add(productEntity);


        when(mongoTemplate.aggregate(any(Aggregation.class), eq("products"), eq(ProductEntity.class))).thenReturn(aggregationResults);
        when(aggregationResults.getMappedResults()).thenReturn(productEntities);

        productMapper.when(() -> ProductMapper.toDomainModel(productEntity)).thenReturn(tShirt);

    }

    @AfterEach
    void close() {
        this.productMapper.close();

    }

    @Test
    void shouldReturnProducts() {
        List<Product> result = this.mongoProductRepositoryAdapter.findAllSortedByScore(1d, 1d, 0, 10);

        assertEquals(1, result.size(), "Product Service - Unit Test - Incorrect size of response");
        assertEquals("tshirt", result.get(0).getName().name(), "Product Service - Unit Test - Incorrect name");
    }


}

package com.itx.technicalTest.infrastructure.config;


import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import com.itx.technicalTest.infrastructure.adapters.MongoProductRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {


    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    public ProductRepository productRepository(MongoProductRepositoryAdapter mongoProductRepositoryAdapter) {
        return mongoProductRepositoryAdapter;
    }
}

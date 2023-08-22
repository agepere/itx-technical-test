package com.itx.technicalTest.unit.services;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.domain.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.verify;

class ProductServiceUnitTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        List<Product> products = new ArrayList<>();
        products.add(new TShirt("1", "tshirt", 10));

        Mockito.when(productRepository.findAllSortedByScore(1d, 1d, 0, 10)).thenReturn(products);
    }

    @Test
    void shouldReturnProducts() {
        List<Product> result = (List<Product>) this.productService.getAllSortedByScore(1d, 1d, 0, 10);

        verify(productRepository, description("Product Service - Unit test - Repository mock was not called"))
                .findAllSortedByScore(1d, 1d, 0, 10);

        assertEquals(1, result.size(), "Product Service - Unit Test - Incorrect size of response");
        assertEquals("tshirt", result.get(0).getName().name(), "Product Service - Unit Test - Incorrect name");
    }


}

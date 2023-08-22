package com.itx.technicalTest.unit.controllers;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.infrastructure.controllers.ProductController;
import com.itx.technicalTest.infrastructure.data.dto.ProductDto;
import com.itx.technicalTest.infrastructure.data.mappers.ProductMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerUnitTest {

    ProductController productController;

    MockedStatic<ProductMapper> productMapper;

    @BeforeEach
    void setup() {
        ProductService productService = Mockito.mock(ProductService.class);
        this.productMapper = Mockito.mockStatic(ProductMapper.class);
        this.productController = new ProductController(productService);

        TShirt tshirt = new TShirt("1", "t-shirt", 10);

        ProductDto productDto = new ProductDto();
        productDto.setId("1");
        productDto.setName("t-shirt");
        productDto.setSales(10);

        List<Product> products = new ArrayList<>();
        products.add(tshirt);

        when(productService.getAllSortedByScore(1d, 1d, 0, 10)).thenReturn(products);
        productMapper.when(() -> ProductMapper.fromDomainModelToDTO(tshirt)).thenReturn(productDto);


    }

    @AfterEach
    void close() {
        this.productMapper.close();
    }

    @Test
    void shouldGetAllProducts() {
        ResponseEntity<List<ProductDto>> response = productController.getAllProducts(0, 10, 1d, 1d);
        ProductDto responseProductDto = response.getBody().get(0);

        assertEquals("1", responseProductDto.getId(), "Product Controller - Unit Test - Incorrect id");
        assertEquals("t-shirt", responseProductDto.getName(), "Product Controller - Unit Test - Incorrect name");
        assertEquals(10, responseProductDto.getSales(), "Product Controller - Unit Test - Incorrect sales");


    }
}

package com.itx.technicalTest.integration.controllers;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final static Integer DEFAULT_PAGE_SIZE = 10;

    @Test
    void shouldUseDefaultParams() throws Exception {
        TShirt tshirt = new TShirt("1", "t-shirt", 10);
        List<Product> products = new ArrayList<>();
        products.add(tshirt);

        when(productService.getAllSortedByScore(1d, 1d, 0, DEFAULT_PAGE_SIZE)).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("t-shirt"))
                .andExpect(jsonPath("$[0].sales").value(10));

        verify(productService, description("Product Controller - Integration test - Wrong default params"))
                .getAllSortedByScore(1d, 1d, 0, DEFAULT_PAGE_SIZE);

    }


    @Test
    void shouldUseCustomParams() throws Exception {
        TShirt tshirt = new TShirt("1", "t-shirt", 10);
        List<Product> products = new ArrayList<>();
        products.add(tshirt);

        when(productService.getAllSortedByScore(2d, 3d, 40, 5)).thenReturn(products);

        mockMvc.perform(get("/api/products?salesScoreRatio=2.0&stockScoreRatio=3.0&page=40&size=5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("t-shirt"))
                .andExpect(jsonPath("$[0].sales").value(10));

        verify(productService, description("Product Controller - Integration test - Wrong custom params"))
                .getAllSortedByScore(2d, 3d, 40, 5);

    }


}

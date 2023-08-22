package com.itx.technicalTest.integration.controllers;

import com.itx.technicalTest.application.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.description;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ExceptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldThrowBadRequest() throws Exception {
        when(productService.getAllSortedByScore(1d, 1d, 0, 10)).thenThrow(new IllegalArgumentException("error message"));

        mockMvc.perform(get("/api/products?size=10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400 BAD_REQUEST"))
                .andExpect(jsonPath("$.errorMessage").value("error message"))
                .andExpect(jsonPath("$.path").value("/api/products"))
        ;

        verify(productService, description("Exception Controller - Integration test - Wrong service call"))
                .getAllSortedByScore(1d, 1d, 0, 10);

    }

    @Test
    void shouldThrowInternalServerError() throws Exception {
        when(productService.getAllSortedByScore(1d, 1d, 0, 10)).thenThrow(new InternalError("error message"));

        mockMvc.perform(get("/api/products?size=10"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("500 INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.errorMessage").value("Handler dispatch failed: java.lang.InternalError: " + "error message"))
                .andExpect(jsonPath("$.path").value("/api/products"))
        ;

        verify(productService, description("Exception Controller - Integration test - Wrong service call"))
                .getAllSortedByScore(1d, 1d, 0, 10);

    }


}

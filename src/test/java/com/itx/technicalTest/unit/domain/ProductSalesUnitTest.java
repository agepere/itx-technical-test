package com.itx.technicalTest.unit.domain;

import com.itx.technicalTest.domain.models.valueObjects.ProductSales;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductSalesUnitTest {

    @Test
    void shouldCallConstructorOK() {
        ProductSales productSales = new ProductSales(10);

        assertEquals(10, productSales.sales(), "ProductSales - UnitTest - Wrong sales");

    }

    @Test
    void shouldCreateZeroProductSales() {
        ProductSales productSales = new ProductSales(0);

        assertEquals(0, productSales.sales(), "ProductSales - UnitTest - Zero should be accepted");

    }

    @Test
    void shouldThrowInvalidNameLength() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductSales(-10)
        );
        String expectedMessage = "Sales number must be positive. Received: -10";

        assertTrue(exception.getMessage().contains(expectedMessage), "ProductSales - UnitTest -  Invalid exception message");
    }
}

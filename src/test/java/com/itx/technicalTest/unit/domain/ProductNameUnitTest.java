package com.itx.technicalTest.unit.domain;

import com.itx.technicalTest.domain.models.valueObjects.ProductName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductNameUnitTest {

    @Test
    void shouldCallConstructorOK() {
        ProductName productName = new ProductName("Product Name");

        assertEquals("Product Name", productName.name(), "ProductName- UnitTest - Wrong name.");

    }

    @Test
    void shouldThrowInvalidNameLength() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new ProductName("")
        );
        String expectedMessage = "Name length must be greater than 0. Received: ";

        assertTrue(exception.getMessage().contains(expectedMessage), "ProductName- UnitTest -  Invalid exception message");
    }
}

package com.itx.technicalTest.unit.domain;

import com.itx.technicalTest.domain.models.valueObjects.StockPerTShirtSize;
import com.itx.technicalTest.domain.models.valueObjects.TShirtSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockPerTShirtSizeUnitTest {

    @Test
    void shouldCallConstructorOK() {
        StockPerTShirtSize stockPerTShirtSize = new StockPerTShirtSize(TShirtSize.S, 10);

        assertEquals(10, stockPerTShirtSize.stock(), "StockPerTShirtSize - UnitTest -  Wrong stock number ");
        assertEquals("S", stockPerTShirtSize.size().name(), "StockPerTShirtSize - UnitTest -  Wrong size name ");

    }

    @Test
    void shouldCreateZeroProductSales() {
        StockPerTShirtSize stockPerTShirtSize = new StockPerTShirtSize(TShirtSize.S, 0);

        assertEquals(0, stockPerTShirtSize.stock(), "StockPerTShirtSize - UnitTest - Zero should be accepted. ");

    }

    @Test
    void shouldThrowInvalidNameLength() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new StockPerTShirtSize(TShirtSize.S, -10)
        );
        String expectedMessage = ("Stock number must be positive. Received: -10");

        assertTrue(exception.getMessage().contains(expectedMessage), "StockPerTShirtSize - UnitTest -  Invalid exception message");
    }
}

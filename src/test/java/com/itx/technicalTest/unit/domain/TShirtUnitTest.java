package com.itx.technicalTest.unit.domain;

import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.domain.models.valueObjects.StockPerTShirtSize;
import com.itx.technicalTest.domain.models.valueObjects.TShirtSize;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TShirtUnitTest {
    @Test
    void shouldCreateTShirt() {
        TShirt tShirt = new TShirt("1", "tshirt", 10);

        String actualId = tShirt.getId();
        String actualName = tShirt.getName().name();
        String actualType = tShirt.getType().name();
        Integer actualSales = tShirt.getSales().sales();
        Set<StockPerTShirtSize> stockLines = tShirt.getStockSet();

        assertEquals("1", actualId, "TShirt - UnitTest - Wrong id");
        assertEquals("tshirt", actualName, "TShirt - UnitTest - Wrong name");
        assertEquals("TSHIRT", actualType, "TShirt - UnitTest - Wrong type");
        assertEquals(10, actualSales, "TShirt - UnitTest - Wrong sales");
        assertEquals(0, stockLines.size(), "TShirt - UnitTest - Wrong stock lines size");
    }

    @Test
    void shouldAddStockLines() {
        TShirt tShirt = new TShirt("1", "tshirt", 10);

        Set<StockPerTShirtSize> stockLines = tShirt.getStockSet();
        tShirt.addStockPerSize("M", 1);
        tShirt.addStockPerSize("S", 3);

        assertEquals(2, stockLines.size(), "TShirt - UnitTest - Wrong stock lines size");

    }

    @Test
    void shouldThrowRepeatedSize() {
        TShirt tShirt = new TShirt("1", "tshirt", 10);
        tShirt.addStockPerSize("S", 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                tShirt.addStockPerSize("S", 3)
        );
        String expectedMessage = "All sizes must be different.";

        assertTrue(exception.getMessage().contains(expectedMessage), "TShirt - UnitTest -  Invalid exception message");
    }

    @Test
    void shouldThrowInvalidSize() {
        TShirt tShirt = new TShirt("1", "tshirt", 10);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                tShirt.addStockPerSize("a", 1)
        );
        String expectedMessage = "This size is not allowed for this t-shirt. Received: " + "a" + ". Accepted sizes: " + Arrays.stream(TShirtSize.values()).toList();

        assertTrue(exception.getMessage().contains(expectedMessage), "TShirt - UnitTest -  Invalid exception message");
    }
}

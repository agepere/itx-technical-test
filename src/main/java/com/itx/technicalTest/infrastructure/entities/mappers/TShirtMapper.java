package com.itx.technicalTest.infrastructure.entities.mappers;

import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.domain.models.valueObjects.StockPerTShirtSize;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.entities.StockLineEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TShirtMapper {
    public static ProductEntity fromDomainModel(TShirt tShirt) {
        List<StockLineEntity> stockLines = buildStockLinesFromDomainModel(tShirt.getStockSet());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(tShirt.getId());
        productEntity.setName(tShirt.getName().name());
        productEntity.setType(tShirt.getType().name());
        productEntity.setSales(tShirt.getSales().sales());
        productEntity.setStockLines(stockLines);

        return productEntity;
    }

    private static List<StockLineEntity> buildStockLinesFromDomainModel(Set<StockPerTShirtSize> stockLinesDomain) {
        List<StockLineEntity> stockLines = new ArrayList<>();
        stockLinesDomain.forEach(stock -> stockLines.add(buildStockLineEntity(stock)));

        return stockLines;
    }

    private static StockLineEntity buildStockLineEntity(StockPerTShirtSize stockPerSize) {
        return new StockLineEntity(stockPerSize.size().name(), stockPerSize.stock());
    }


    public static TShirt toDomainModel(ProductEntity productEntity) {
        TShirt tShirt = new TShirt(productEntity.getId(), productEntity.getName(), productEntity.getSales());
        addStockToDomainModel(productEntity.getStockLines(), tShirt);

        return tShirt;
    }

    private static void addStockToDomainModel(List<StockLineEntity> stockLines, TShirt tShirt) {
        stockLines.forEach(stockLine -> tShirt.addStockPerSize(stockLine.getSize(), stockLine.getStock()));

    }

}

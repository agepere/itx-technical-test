package com.itx.technicalTest.infrastructure.data.mappers;

import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.domain.models.valueObjects.StockPerTShirtSize;
import com.itx.technicalTest.infrastructure.data.dto.ProductDto;
import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.data.entities.StockLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TShirtMapper {
    private TShirtMapper() {
        throw new IllegalStateException("TShirtMapper is an utility class, it should not be instantiated.");
    }

    public static ProductEntity fromDomainModelToDAO(TShirt tShirt) {
        List<StockLine> stockLines = buildStockLinesFromDomainModel(tShirt.getStockSet());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(tShirt.getId());
        productEntity.setName(tShirt.getName().name());
        productEntity.setType(tShirt.getType().name());
        productEntity.setSales(tShirt.getSales().sales());
        productEntity.setStockLines(stockLines);

        return productEntity;
    }

    private static List<StockLine> buildStockLinesFromDomainModel(Set<StockPerTShirtSize> stockLinesDomain) {
        List<StockLine> stockLines = new ArrayList<>();
        stockLinesDomain.forEach(stock -> stockLines.add(buildStockLineEntity(stock)));

        return stockLines;
    }

    public static ProductDto fromDomainModelToDTO(TShirt tShirt) {
        List<StockLine> stockLines = buildStockLinesFromDomainModel(tShirt.getStockSet());

        ProductDto productDto = new ProductDto();
        productDto.setId(tShirt.getId());
        productDto.setName(tShirt.getName().name());
        productDto.setType(tShirt.getType().name());
        productDto.setSales(tShirt.getSales().sales());
        productDto.setStockLines(stockLines);

        return productDto;
    }

    private static StockLine buildStockLineEntity(StockPerTShirtSize stockPerSize) {
        return new StockLine(stockPerSize.size().name(), stockPerSize.stock());
    }


    public static TShirt toDomainModel(ProductEntity productEntity) {
        TShirt tShirt = new TShirt(productEntity.getId(), productEntity.getName(), productEntity.getSales());
        addStockToDomainModel(productEntity.getStockLines(), tShirt);

        return tShirt;
    }

    public static TShirt toDomainModel(ProductDto productDto) {
        TShirt tShirt = new TShirt(productDto.getId(), productDto.getName(), productDto.getSales());
        addStockToDomainModel(productDto.getStockLines(), tShirt);

        return tShirt;
    }


    private static void addStockToDomainModel(List<StockLine> stockLines, TShirt tShirt) {
        if (stockLines == null) {
            stockLines = new ArrayList<>();
        }
        stockLines.forEach(stockLine -> tShirt.addStockPerSize(stockLine.getSize(), stockLine.getStock()));

    }

}

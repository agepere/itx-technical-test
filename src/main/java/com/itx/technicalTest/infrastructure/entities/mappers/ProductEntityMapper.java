package com.itx.technicalTest.infrastructure.entities.mappers;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.valueObjects.Stock;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;

import java.util.*;

public class ProductEntityMapper {
    //TODO: Mejorar los nombres de las variables y parámetros, quizas queda más intuitivo con setters y no con el constructor directamente
    public static ProductEntity fromDomainModel(Product product) {
        Map<String, Integer> stock = buildStockMapFromDomainModel(product.getStockSet());
        return new ProductEntity(product.getId(), product.getName().name(), product.getSales().sales(), stock);
    }

    private static Map<String, Integer> buildStockMapFromDomainModel(Set<Stock> stockSet) {
        Map<String, Integer> stockMap = new HashMap<>();
        stockSet.forEach(stock -> stockMap.put(stock.size().name(), stock.stock()));

        return stockMap;
    }

    public static Product toDomainModel(ProductEntity productEntity) {
        Product product = new Product(productEntity.getId(), productEntity.getName(), productEntity.getSales());
        addStockToDomainModel(productEntity.getStock(), product);

        return product;
    }

    private static void addStockToDomainModel(Map<String, Integer> stockMap, Product product) {
        stockMap.forEach((stock, size) -> product.addStockPerSize(stock, size));

    }

}

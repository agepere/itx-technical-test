package com.itx.technicalTest.infrastructure.entities.mappers;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;


public class ProductMapper {
    public static ProductEntity fromDomainModel(Product product) {
        switch (product.getType()) {
            case TSHIRT: {
                return TShirtMapper.fromDomainModel((TShirt) product);
            }
            default:
                throw new IllegalArgumentException("That type of product does not exist or it has no mapper.");
        }
    }


    public static Product toDomainModel(ProductEntity productEntity) {
        switch (productEntity.getType()) {
            case "TSHIRT": {
                return TShirtMapper.toDomainModel(productEntity);
            }
            default:
                throw new IllegalArgumentException("That type of product does not exist or it has no mapper.");
        }
    }


}

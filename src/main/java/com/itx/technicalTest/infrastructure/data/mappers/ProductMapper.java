package com.itx.technicalTest.infrastructure.data.mappers;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.domain.models.TShirt;
import com.itx.technicalTest.infrastructure.data.dto.ProductDto;
import com.itx.technicalTest.infrastructure.data.entities.ProductEntity;


public class ProductMapper {

    private static final String EXCEPTION_TYPE_NOT_FOUND = "That type of product does not exist or it has no mapper.";

    private ProductMapper() {
        throw new IllegalStateException("ProductMapper is an utility class, it should not be instantiated.");
    }

    public static ProductEntity fromDomainModelToDAO(Product product) {
        switch (product.getType()) {
            case TSHIRT -> {
                return TShirtMapper.fromDomainModelToDAO((TShirt) product);
            }
            default -> throw new IllegalArgumentException(EXCEPTION_TYPE_NOT_FOUND);
        }
    }

    public static ProductDto fromDomainModelToDTO(Product product) {
        switch (product.getType()) {
            case TSHIRT -> {
                return TShirtMapper.fromDomainModelToDTO((TShirt) product);
            }
            default -> throw new IllegalArgumentException(EXCEPTION_TYPE_NOT_FOUND);
        }
    }


    public static Product toDomainModel(ProductEntity productEntity) {
        return convert(productEntity);
    }

    public static Product toDomainModel(ProductDto productDto) {
        return convert(productDto);
    }

    private static Product convert(Object object) {
        String type;
        boolean isFromDto = false;

        if (object instanceof ProductEntity productEntity) {
            type = productEntity.getType();
        } else if (object instanceof ProductDto productDto) {
            type = productDto.getType();
            isFromDto = true;
        } else {
            throw new IllegalArgumentException("Invalid product type.");
        }

        return castAndConvert(object, type, isFromDto);
    }

    private static Product castAndConvert(Object object, String type, boolean isFromDto) {
        switch (type) {
            case "TSHIRT" -> {
                if (isFromDto) {
                    return TShirtMapper.toDomainModel((ProductDto) object);
                } else {
                    return TShirtMapper.toDomainModel((ProductEntity) object);
                }
            }
            default -> throw new IllegalArgumentException(EXCEPTION_TYPE_NOT_FOUND);
        }
    }


}

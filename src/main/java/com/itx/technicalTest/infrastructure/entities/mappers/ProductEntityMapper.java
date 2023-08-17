package com.itx.technicalTest.infrastructure.entities.mappers;

import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProductEntityMapper {

    Product toDomain(ProductEntity productEntity);


    ProductEntity toDbo(Product product);
}

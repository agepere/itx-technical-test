package com.itx.technicalTest.infrastructure.controllers;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.infrastructure.entities.ProductEntity;
import com.itx.technicalTest.infrastructure.entities.mappers.ProductEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productDto) {
        Product product = ProductEntityMapper.toDomainModel(productDto);
        Product createdProduct = this.productService.create(product);
        ProductEntity createdProductDto = ProductEntityMapper.fromDomainModel(createdProduct);

        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    //TODO: El número de paginación debe ir a la configuración
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "20", required = false) Integer size) {
        List<Product> products = (List<Product>) this.productService.getAll(page, size);
        List<ProductEntity> productsDto = products.stream().map(ProductEntityMapper::fromDomainModel).toList();

        return new ResponseEntity<>(productsDto, HttpStatus.CREATED);
    }


}

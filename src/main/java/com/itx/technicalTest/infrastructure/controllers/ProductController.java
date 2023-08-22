package com.itx.technicalTest.infrastructure.controllers;

import com.itx.technicalTest.application.services.ProductService;
import com.itx.technicalTest.domain.models.Product;
import com.itx.technicalTest.infrastructure.data.dto.ProductDto;
import com.itx.technicalTest.infrastructure.data.mappers.ProductMapper;
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
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product product = ProductMapper.toDomainModel(productDto);
        Product createdProduct = this.productService.create(product);
        ProductDto createdProductDto = ProductMapper.fromDomainModelToDTO(createdProduct);

        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "${application.default.page.size}", required = false) Integer size,
            @RequestParam(defaultValue = "1.0", required = false) Double salesScoreRatio,
            @RequestParam(defaultValue = "1.0", required = false) Double stockScoreRatio) {
        List<Product> products = (List<Product>) this.productService.getAllSortedByScore(salesScoreRatio, stockScoreRatio, page, size);
        List<ProductDto> productsDto = products.stream().map(ProductMapper::fromDomainModelToDTO).toList();

        return new ResponseEntity<>(productsDto, HttpStatus.OK);
    }

}

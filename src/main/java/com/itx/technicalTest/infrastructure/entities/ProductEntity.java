package com.itx.technicalTest.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class ProductEntity {
    
    @Id
    private String id;
    private String name;
    private Integer sales;
    private Map<String, Integer> stock;

}

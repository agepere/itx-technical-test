package com.itx.technicalTest.infrastructure.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "products")
public class ProductEntity {

    @Id
    private String id;
    private String name;
    private String type;
    @Indexed(name = "sales_index", direction = IndexDirection.DESCENDING)
    private Integer sales;
    private List<StockLine> stockLines;


}

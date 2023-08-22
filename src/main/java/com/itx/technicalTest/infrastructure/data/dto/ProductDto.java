package com.itx.technicalTest.infrastructure.data.dto;

import com.itx.technicalTest.infrastructure.data.entities.StockLine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String id;
    private String name;
    private String type;
    private Integer sales;
    private List<StockLine> stockLines;
}

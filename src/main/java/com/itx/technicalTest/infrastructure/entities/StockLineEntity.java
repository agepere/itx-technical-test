package com.itx.technicalTest.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StockLineEntity {
    private String size;
    private Integer stock;
}
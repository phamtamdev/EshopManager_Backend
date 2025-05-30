package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private Double discountedPrice;
    private String slug;
    private Integer categoryId;
    private Integer brandId;
}

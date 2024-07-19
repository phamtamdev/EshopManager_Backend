package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDto {
    private Integer id;
    private String variantName;
    private String variantValue;
    private double additionalPrice;
    private Integer productId;
}

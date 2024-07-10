package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.entity.ProductImage;

public class ProductImageMapper {

    public static ProductImageDto mapToProductImageDto(ProductImage productImage) {
        return new ProductImageDto(productImage.getId(),productImage.getName(), productImage.getUrl());
    }

    public static ProductImage mapToProductImage(ProductImageDto productImageDto) {
        ProductImage productImage = new ProductImage();
        productImage.setId(productImageDto.getId());
        productImage.setName(productImageDto.getName());
        productImage.setUrl(productImageDto.getUrl());
        return productImage;
    }
}

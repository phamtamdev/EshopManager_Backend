package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductImage;

public class ProductImageMapper {

    public static ProductImageDto mapToProductImageDto(ProductImage productImage) {
        return new ProductImageDto(productImage.getId(),productImage.getName(), productImage.getUrl(),productImage.getData(), productImage.isIcon(), productImage.getProduct().getId());
    }

    public static ProductImage mapToProductImage(ProductImageDto productImageDto) {
        ProductImage productImage = new ProductImage();
        productImage.setId(productImageDto.getId());
        productImage.setName(productImageDto.getName());
        productImage.setData(productImageDto.getData());
        productImage.setUrl(productImageDto.getUrl());
        productImage.setIcon(productImageDto.isIcon());
        if(productImageDto.getProductId() != null){
            Product product = new Product();
            product.setId(productImageDto.getProductId());
            productImage.setProduct(product);
        }
        return productImage;
    }
}

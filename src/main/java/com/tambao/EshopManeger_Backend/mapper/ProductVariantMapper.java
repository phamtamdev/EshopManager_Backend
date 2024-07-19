package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductVariant;

public class ProductVariantMapper {
    public static ProductVariantDto mapToProductVariantDto(ProductVariant productVariant) {
        return new ProductVariantDto(
                productVariant.getId(),
                productVariant.getVariantName(),
                productVariant.getVariantValue(),
                productVariant.getAdditionalPrice(),
                productVariant.getProduct().getId()
        );
    }

    public static ProductVariant mapToProductVariant(ProductVariantDto productVariantDto) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantDto.getId());
        productVariant.setVariantName(productVariantDto.getVariantName());
        productVariant.setVariantValue(productVariantDto.getVariantValue());
        productVariant.setAdditionalPrice(productVariantDto.getAdditionalPrice());
        if(productVariantDto.getProductId() != null){
            Product product = new Product();
            product.setId(productVariantDto.getProductId());
            productVariant.setProduct(product);
        }
        return productVariant;
    }
}

package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductVariant;
import com.tambao.EshopManeger_Backend.entity.Variant;

public class ProductVariantMapper {
    public static ProductVariantDto mapToProductVariantDto(ProductVariant productVariant) {
        return new ProductVariantDto(
                productVariant.getId(),
                productVariant.getVariantValue(),
                productVariant.getAdditionalPrice(),
                productVariant.getProduct().getId(),
                productVariant.getVariant().getId(),
                productVariant.getVariant().getName()
        );
    }

    public static ProductVariant mapToProductVariant(ProductVariantDto productVariantDto) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setId(productVariantDto.getId());
        productVariant.setVariantValue(productVariantDto.getVariantValue());
        productVariant.setAdditionalPrice(productVariantDto.getAdditionalPrice());
        if(productVariantDto.getProductId() != null){
            Product product = new Product();
            product.setId(productVariantDto.getProductId());
            productVariant.setProduct(product);
        }
        if(productVariantDto.getVariantId() != null){
            Variant variant = new Variant();
            variant.setId(productVariantDto.getVariantId());
            productVariant.setVariant(variant);
        }
        return productVariant;
    }
}

package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;
import com.tambao.EshopManeger_Backend.entity.ProductVariant;

import java.util.List;

public interface IProductVariantService {
    List<ProductVariantDto> getAllProductVariants();
    List<ProductVariantDto> getProductVariantByProductId(Integer productId);
    List<ProductVariantDto> getByVariantId(Integer variantId);
    List<ProductVariantDto> findByVariantIdAndVariantValue(Integer variantId, String variantValue);
    ProductVariantDto addProductVariant(ProductVariantDto productVariantDto);
    ProductVariantDto updateProductVariant(Integer id, ProductVariantDto productVariantDto);
    ProductVariantDto getProductVariant(int id);
    void deleteProductVariant(int id);
}

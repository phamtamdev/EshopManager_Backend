package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;

import java.util.List;

public interface IProductVariantService {
    List<ProductVariantDto> getProductVariantByProductId(Integer productId);
    ProductVariantDto addProductVariant(ProductVariantDto productVariantDto);
    ProductVariantDto updateProductVariant(Integer id, ProductVariantDto productVariantDto);
    ProductVariantDto getProductVariant(int id);
    void deleteProductVariant(int id);
}

package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;

import java.util.List;

public interface IProductImageService {
    List<ProductImageDto> findByProductId(int id);
    ProductImageDto save(ProductImageDto productImageDto);
    ProductImageDto update(Integer id, ProductImageDto productImageDto);
    void deleteById(int id);
}

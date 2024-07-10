package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.entity.Product;

import java.util.List;

public interface IProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(int id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Integer id ,ProductDto productDto);
    void deleteProduct(int id);
}

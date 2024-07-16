package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    List<ProductDto> getAllProducts();
    Page<ProductDto> getProductsWithPage(int page, int size);
    Page<ProductDto> getProductsWithPageAndSearch(String keyword, int page, int size);
    Page<ProductDto> getProductsWithPageAndSorting(String field, int page, int size);
    Page<ProductDto> getProductsWithPageAndSortingAndSearch(String field, String keyword, int page, int size);
    ProductDto getProductById(int id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Integer id ,ProductDto productDto);
    void deleteProduct(int id);
}

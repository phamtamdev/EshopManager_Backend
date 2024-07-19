package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ProductMapper;
import com.tambao.EshopManeger_Backend.repository.CategoryRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> getProductsWithPage(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSearch(String keyword, int page, int size) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSorting(String field, int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field)));
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSortingAndSearch(String field, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, field));
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id= " + id));
        return ProductMapper.mapToProductDTO(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category not found with id= " + productDto.getCategoryId()));
        Product product = ProductMapper.mapToProduct(productDto);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDTO(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Integer id ,ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id= " + id));
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id= " + productDto.getCategoryId()));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setCategory(category);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id= " + id));
        productRepository.delete(product);
    }
}

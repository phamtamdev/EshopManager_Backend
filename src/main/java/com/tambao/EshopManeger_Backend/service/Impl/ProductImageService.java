package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductImage;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ProductImageMapper;
import com.tambao.EshopManeger_Backend.repository.ProductImageRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductImageDto> findByProductId(int id) {
        List<ProductImage> productImages = productImageRepository.findByProductId(id);
        return productImages.stream()
                .map(ProductImageMapper::mapToProductImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductImageDto save(ProductImageDto productImageDto) {
        Product product = productRepository.findById(productImageDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductImage productImage = ProductImageMapper.mapToProductImage(productImageDto);
        productImage.setProduct(product);
        return ProductImageMapper.mapToProductImageDto(productImageRepository.save(productImage));
    }

    @Override
    public ProductImageDto update(Integer id, ProductImageDto productImageDto) {
        ProductImage productImage = productImageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Image Not Found"));
        Product product = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
        productImage.setProduct(product);
        productImage.setName(productImageDto.getName());
        productImage.setData(productImageDto.getData());
        productImage.setUrl(productImageDto.getUrl());
        productImage.setIcon(productImageDto.isIcon());
        ProductImage updatedProductImage = productImageRepository.save(productImage);
        return ProductImageMapper.mapToProductImageDto(updatedProductImage);
    }

    @Override
    public void deleteById(int id) {
        ProductImage productImage = productImageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Image Not Found"));
        productImageRepository.delete(productImage);
    }
}

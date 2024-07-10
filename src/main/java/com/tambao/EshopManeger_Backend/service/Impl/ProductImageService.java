package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ProductImageDto;
import com.tambao.EshopManeger_Backend.entity.ProductImage;
import com.tambao.EshopManeger_Backend.mapper.ProductImageMapper;
import com.tambao.EshopManeger_Backend.repository.ProductImageRepository;
import com.tambao.EshopManeger_Backend.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService implements IProductImageService {
    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public List<ProductImageDto> findByProductId(int id) {
        List<ProductImage> productImages = productImageRepository.findByProductId(id);
        return productImages.stream()
                .map(ProductImageMapper::mapToProductImageDto)
                .collect(Collectors.toList());
    }
}

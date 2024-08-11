package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ProductVariantDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductVariant;
import com.tambao.EshopManeger_Backend.entity.Variant;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ProductVariantMapper;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.repository.ProductVariantRepository;
import com.tambao.EshopManeger_Backend.repository.VariantRepository;
import com.tambao.EshopManeger_Backend.service.IProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantService implements IProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Override
    public List<ProductVariantDto> getAllProductVariants() {
        List<ProductVariant> productVariants = productVariantRepository.findAll();
        return productVariants.stream()
                .map(ProductVariantMapper::mapToProductVariantDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariantDto> getProductVariantByProductId(Integer productId) {
        List<ProductVariant> productVariants = productVariantRepository.findByProductIdWithVariantName(productId);
        return productVariants.stream()
                .sorted(Comparator.comparing(ProductVariant::getVariantValue).reversed())
                .map(ProductVariantMapper::mapToProductVariantDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariantDto> getByVariantId(Integer variantId) {
        List<ProductVariant> productVariants = productVariantRepository.findByVariantId(variantId);
        return productVariants.stream()
                .sorted(Comparator.comparing(ProductVariant::getVariantValue).reversed())
                .map(ProductVariantMapper::mapToProductVariantDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariantDto addProductVariant(ProductVariantDto productVariantDto) {
        return ProductVariantMapper.mapToProductVariantDto(productVariantRepository.save(ProductVariantMapper.mapToProductVariant(productVariantDto)));
    }

    @Override
    public ProductVariantDto updateProductVariant(Integer id, ProductVariantDto productVariantDto) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product variant not found"));
        Product product = productRepository.findById(productVariantDto.getProductId()).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        Variant variant = variantRepository.findById(productVariantDto.getVariantId()).orElseThrow(()-> new ResourceNotFoundException("Variant not found"));
        productVariant.setVariantValue(productVariantDto.getVariantValue());
        productVariant.setAdditionalPrice(productVariantDto.getAdditionalPrice());
        productVariant.setProduct(product);
        productVariant.setVariant(variant);
        ProductVariant updatedProductVariant = productVariantRepository.save(productVariant);
        return ProductVariantMapper.mapToProductVariantDto(updatedProductVariant);
    }

    @Override
    public ProductVariantDto getProductVariant(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Variant not found"));
        return ProductVariantMapper.mapToProductVariantDto(productVariant);
    }

    @Override
    public void deleteProductVariant(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Variant not found"));
        productVariantRepository.delete(productVariant);
    }
}

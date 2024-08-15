package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.BrandDto;
import com.tambao.EshopManeger_Backend.entity.Brand;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.BrandMapper;
import com.tambao.EshopManeger_Backend.repository.BrandRepository;
import com.tambao.EshopManeger_Backend.repository.CategoryRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<BrandDto> findBrandByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        List<Brand> brands = brandRepository.findBrandByCategoryId(category.getId());
        return brands.stream()
                .map(BrandMapper::mapToBrandDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(BrandMapper::mapToBrandDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BrandDto> getBrandsWithPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Brand> brands = brandRepository.findAll(pageable);
        return brands.map(BrandMapper::mapToBrandDto);
    }

    @Override
    public Page<BrandDto> getBrandsWithPageAndSearch(int page, int size, String keyword) {
        Page<Brand> brands = brandRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        return brands.map(BrandMapper::mapToBrandDto);
    }

    @Override
    public Page<BrandDto> getBrandsWithPageAndSorting(int page, int size, String field, String sortOrder) {
        Pageable pageable = createPageable(page,size ,field,sortOrder);
        Page<Brand> brands = brandRepository.findAll(pageable);
        return brands.map(BrandMapper::mapToBrandDto);
    }

    @Override
    public Page<BrandDto> getBrandsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder) {
        Pageable pageable = createPageable(page,size ,field,sortOrder);
        Page<Brand> brands = brandRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return brands.map(BrandMapper::mapToBrandDto);
    }

    @Override
    public BrandDto save(BrandDto brandDto) {
        return BrandMapper.mapToBrandDto(brandRepository.save(BrandMapper.mapToBrand(brandDto)));
    }

    @Override
    public BrandDto findById(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Brand not found"));
        return BrandMapper.mapToBrandDto(brand);
    }

    @Override
    public BrandDto findByCategoryIdAndName(Integer categoryId, String name) {
        return BrandMapper.mapToBrandDto(brandRepository.findBrandByCategoryIdAndNameContainingIgnoreCase(categoryId, name));
    }

    @Override
    public BrandDto update(Integer id, BrandDto brandDto) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Brand not found"));
        Category category = categoryRepository.findById(brandDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        brand.setName(brandDto.getName());
        brand.setCategory(category);
        brand.setImage(brandDto.getImage());
        brand.setBanner(brandDto.getBanner());
        Brand updatedBrand = brandRepository.save(brand);
        return BrandMapper.mapToBrandDto(updatedBrand);
    }

    @Override
    public void delete(Integer id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Brand not found"));
        List<Product> products = productRepository.findByBrandId(id);
        for (Product product : products) {
            product.setBrand(null);
            productRepository.save(product);
        }
        brandRepository.delete(brand);
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }
}

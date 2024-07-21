package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.CategoryDto;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.CategoryMapper;
import com.tambao.EshopManeger_Backend.repository.CategoryRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::maptoCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCategoriesWithSorting(String field) {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, field));
        return categories.stream()
                .map(CategoryMapper::maptoCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryDto> getCategoriesWithPage(int page, int size) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page,size));
        return categories.map(CategoryMapper::maptoCategoryDto);
    }

    @Override
    public Page<CategoryDto> getCategoriesWithPageAndSearch(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCase(keyword,pageable);
        return categories.map(CategoryMapper::maptoCategoryDto);
    }

    @Override
    public Page<CategoryDto> getCategoriesWithPageAndSorting(int page, int size, String field) {
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, field)));
        return categories.map(CategoryMapper::maptoCategoryDto);
    }

    @Override
    public Page<CategoryDto> getCategoriesWithPageAndSortingAndSearch(int page, int size, String field, String keyword) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, field));
        Page<Category> categories = categoryRepository.findByNameContainingIgnoreCase(keyword,pageable);
        return categories.map(CategoryMapper::maptoCategoryDto);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.maptoCategory(categoryDto);
        return CategoryMapper.maptoCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        return CategoryMapper.maptoCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(Integer id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.maptoCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found" + id));
        List<Product> products = productRepository.findByCategoryId(id);
        for (Product product : products) {
            product.setCategory(null);
            productRepository.save(product);
        }
        categoryRepository.delete(category);
    }
}

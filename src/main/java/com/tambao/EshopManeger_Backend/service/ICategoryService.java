package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.CategoryDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    List<CategoryDto> getAllCategories();
    List<CategoryDto> getCategoriesWithSorting(String field);
    Page<CategoryDto> getCategoriesWithPage(int page, int size);
    Page<CategoryDto> getCategoriesWithPageAndSearch(int page, int size, String keyword);
    Page<CategoryDto> getCategoriesWithPageAndSorting(int page, int size, String field);
    Page<CategoryDto> getCategoriesWithPageAndSortingAndSearch(int page, int size, String field, String keyword);
    CategoryDto addCategory(CategoryDto categoryDto);
    CategoryDto getCategoryById(int id);
    CategoryDto updateCategory(Integer id, CategoryDto categoryDto);
    void deleteCategory(Integer id);
}

package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.CategoryDto;
import com.tambao.EshopManeger_Backend.entity.Category;

public class CategoryMapper {
    public static CategoryDto maptoCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getImage(),
                category.getSlug()
        );
    }

    public static Category maptoCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setImage(categoryDto.getImage());
        category.setSlug(categoryDto.getSlug());
        return category;
    }
}

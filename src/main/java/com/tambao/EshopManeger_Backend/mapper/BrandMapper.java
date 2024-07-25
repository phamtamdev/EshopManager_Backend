package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.BrandDto;
import com.tambao.EshopManeger_Backend.entity.Brand;
import com.tambao.EshopManeger_Backend.entity.Category;

public class BrandMapper {
    public static BrandDto mapToBrandDto(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getName(),
                brand.getImage(),
                brand.getCategory().getId()
        );
    };

    public static Brand mapToBrand(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setId(brandDto.getId());
        brand.setName(brandDto.getName());
        brand.setImage(brandDto.getImage());
        if(brandDto.getCategoryId() != null){
            Category category = new Category();
            category.setId(brandDto.getCategoryId());
            brand.setCategory(category);
        }
        return brand;
    }
}

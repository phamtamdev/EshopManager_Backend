package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.VariantDto;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Variant;

public class VariantMapper {
    public static VariantDto mapToVariantDto(Variant variant) {
        return new VariantDto(
                variant.getId(),
                variant.getName(),
                variant.getCategory().getId()
        );
    }

    public static Variant mapToVariant(VariantDto variantDto) {
        Variant variant = new Variant();
        variant.setId(variantDto.getId());
        variant.setName(variantDto.getName());
        if(variantDto.getCategoryId() != null){
            Category category = new Category();
            category.setId(variantDto.getCategoryId());
            variant.setCategory(category);
        }
        return variant;
    }
}

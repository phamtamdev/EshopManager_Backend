package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.VariantDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IVariantService {
    List<VariantDto> getVariantsByCategoryId(Integer categoryId);
    Page<VariantDto> getVariantsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder);
    VariantDto getVariantById(int id);
    List<VariantDto> getAllVariants();
    VariantDto saveVariant(VariantDto variantDto);
    VariantDto updateVariant(Integer variantId, VariantDto variantDto);
    void deleteVariant(int id);

}

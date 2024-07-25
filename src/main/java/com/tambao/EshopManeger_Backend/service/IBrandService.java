package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.BrandDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IBrandService {
    List<BrandDto> findBrandByCategoryId(Integer categoryId);
    List<BrandDto> getAllBrands();
    Page<BrandDto> getBrandsWithPage(int page, int size);
    Page<BrandDto> getBrandsWithPageAndSearch(int page, int size, String keyword);
    Page<BrandDto> getBrandsWithPageAndSorting(int page, int size, String field, String sortOrder);
    Page<BrandDto> getBrandsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder);
    BrandDto save(BrandDto brandDto);
    BrandDto findById(Integer id);
    BrandDto update(Integer id, BrandDto brandDto);
    void delete(Integer id);
}

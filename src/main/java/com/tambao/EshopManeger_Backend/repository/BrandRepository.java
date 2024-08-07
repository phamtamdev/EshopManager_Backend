package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Page<Brand> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    List<Brand> findBrandByCategoryId(Integer categoryId);
    Brand findBrandByCategoryIdAndNameContainingIgnoreCase(Integer categoryId, String brandName);
}

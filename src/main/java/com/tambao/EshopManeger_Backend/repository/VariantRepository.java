package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Variant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant, Integer> {
    List<Variant> findByCategoryId(Integer categoryId);
    Page<Variant> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

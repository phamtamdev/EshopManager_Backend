package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByNameContainingIgnoreCase(String key, Pageable pageable);
}

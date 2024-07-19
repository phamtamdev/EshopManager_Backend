package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByProductId(Integer productId);
}

package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    @Query("SELECT pv FROM ProductVariant pv JOIN pv.variant v WHERE pv.product.id = :productId")
    List<ProductVariant> findByProductIdWithVariantName(@Param("productId") Integer productId);
}

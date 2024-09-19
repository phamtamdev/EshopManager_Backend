package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategoryIdAndNameContaining(Integer id, String keyword, Pageable pageable);
    Page<Product> findByCategoryIdAndBrandIdAndNameContaining(Integer id, Integer brandId, String keyword, Pageable pageable);
    List<Product> findByCategoryId(Integer id);
    List<Product> findByBrandId(Integer id);
    Product findBySlug(String slug);
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findAllByIdIn(@Param("productIds") List<Integer> productIds);
}

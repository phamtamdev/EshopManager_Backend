package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.Wishlists;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistsRepository extends JpaRepository<Wishlists, Integer> {
    List<Wishlists> findByUserId(Integer id);
}

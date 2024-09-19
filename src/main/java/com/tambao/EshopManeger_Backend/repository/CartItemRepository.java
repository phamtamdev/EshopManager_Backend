package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartIdAndProductId(Integer cartId, Integer productId);
    List<CartItem> findByCartId(Integer cartId);
    CartItem findByIdAndProductId(Integer id, Integer productId);
}

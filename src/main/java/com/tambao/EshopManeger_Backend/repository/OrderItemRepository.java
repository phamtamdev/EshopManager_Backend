package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrdersId(Integer orderId);
}

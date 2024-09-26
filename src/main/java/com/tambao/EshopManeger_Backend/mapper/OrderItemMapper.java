package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.OrderItemDto;
import com.tambao.EshopManeger_Backend.entity.OrderItem;
import com.tambao.EshopManeger_Backend.entity.Orders;
import com.tambao.EshopManeger_Backend.entity.Product;

public class OrderItemMapper {
    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getProduct().getId(),
                orderItem.getOrders().getId()
        );
    }

    public static OrderItem mapToOrderItem(OrderItemDto dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.getId());
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setPrice(dto.getPrice());
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setId(dto.getProductId());
            orderItem.setProduct(product);
        }

        if (dto.getOrderId() != null) {
            Orders orders = new Orders();
            orders.setId(dto.getOrderId());
            orderItem.setOrders(orders);
        }

        return orderItem;
    }
}

package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.OrderItemDto;

import java.util.List;

public interface IOrderItemService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);
    List<OrderItemDto> getByOrderId(Integer orderId);
}

package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.OrderItemDto;
import com.tambao.EshopManeger_Backend.entity.OrderItem;
import com.tambao.EshopManeger_Backend.entity.Orders;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.OrderItemMapper;
import com.tambao.EshopManeger_Backend.repository.OrderItemRepository;
import com.tambao.EshopManeger_Backend.repository.OrderRepository;
import com.tambao.EshopManeger_Backend.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemRepository.save(OrderItemMapper.mapToOrderItem(orderItemDto));
        return OrderItemMapper.mapToOrderItemDto(orderItem);
    }

    @Override
    public List<OrderItemDto> getByOrderId(Integer orderId) {
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Not Found"));
        List<OrderItem> orderItems = orderItemRepository.findByOrdersId(orders.getId());
        return orderItems.stream().map(OrderItemMapper::mapToOrderItemDto).collect(Collectors.toList());
    }
}

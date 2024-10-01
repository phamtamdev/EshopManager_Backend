package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.OrderConfirmationDto;
import com.tambao.EshopManeger_Backend.dto.OrderDto;
import com.tambao.EshopManeger_Backend.entity.OrderStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderCode(String orderCode);
    OrderDto getByOrderId(Integer orderId);
    List<OrderDto> getAllOrders();
    Page<OrderDto> getOrdersWithPage(int page, int size);
    Page<OrderDto> getOrdersWithPageAndSearch(int page, int size, String keyword);
    Page<OrderDto> getOrdersWithPageAndOrderStatus(int page, int size, OrderStatus orderStatus);
    Page<OrderDto> getOrdersWithPageAndSorting(int page, int size, String field, String sortOrder);
    Page<OrderDto> getOrdersWithPageAndSortingAndSearch(int page, int size, String field, String sortOrder, String keyword);
    OrderDto getByOrderCodeAndPhone(String orderCode, String phone);
    List<OrderDto> getByUserId(Integer userId);
    OrderDto updateStatusOrder(Integer orderId, OrderStatus orderStatus);
    void sendEmail(OrderConfirmationDto orderConfirmationDto);
}

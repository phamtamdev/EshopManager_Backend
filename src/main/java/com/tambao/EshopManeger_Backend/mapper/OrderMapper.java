package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.OrderDto;
import com.tambao.EshopManeger_Backend.entity.Orders;
import com.tambao.EshopManeger_Backend.entity.Payment;
import com.tambao.EshopManeger_Backend.entity.Users;

public class OrderMapper {
    public static OrderDto mapToOrderDto(Orders order) {
        return new OrderDto(
                order.getId(),
                order.getCreateAt(),
                order.getTotalAmount(),
                order.getOrderCode(),
                order.getOrderStatus(),
                order.getTotalQuantity(),
                order.getTotalPrice(),
                order.getRecipientName(),
                order.getRecipientPhone(),
                order.getRecipientEmail(),
                order.getRecipientAddress(),
                order.getUser().getId(),
                order.getPayment().getId()
        );
    }

    public static Orders mapToOrders(OrderDto dto) {
        Orders order = new Orders();
        order.setId(dto.getId());
        order.setCreateAt(dto.getCreatedAt());
        order.setTotalAmount(dto.getTotalAmount());
        order.setOrderCode(dto.getOrderCode());
        order.setOrderStatus(dto.getOrderStatus());
        order.setTotalQuantity(dto.getTotalQuantity());
        order.setTotalPrice(dto.getTotalPrice());
        order.setRecipientName(dto.getRecipientName());
        order.setRecipientPhone(dto.getRecipientPhone());
        order.setRecipientEmail(dto.getRecipientEmail());
        order.setRecipientAddress(dto.getRecipientAddress());
        if (dto.getUserId() != null) {
            Users user = new Users();
            user.setId(dto.getUserId());
            order.setUser(user);
        }

        if (dto.getPaymentId() != null) {
            Payment payment = new Payment();
            payment.setId(dto.getPaymentId());
            order.setPayment(payment);
        }
        return order;
    }
}

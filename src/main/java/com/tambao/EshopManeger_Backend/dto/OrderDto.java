package com.tambao.EshopManeger_Backend.dto;

import com.tambao.EshopManeger_Backend.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private Integer id;
    private Timestamp createdAt;
    private Double totalAmount;
    private String orderCode;
    private OrderStatus orderStatus;
    private Integer totalQuantity;
    private Double totalPrice;
    private String recipientName;
    private String recipientPhone;
    private String recipientEmail;
    private Integer userId;
    private Integer paymentId;
    private Integer shippingAddressId;
}

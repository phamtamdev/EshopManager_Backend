package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDto {
    private Integer id;
    private int quantity;
    private double price;
    private String description;
    private Integer productId;
    private Integer orderId;
}

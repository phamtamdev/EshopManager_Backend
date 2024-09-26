package com.tambao.EshopManeger_Backend.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PROCESSING,
    CONFIRMED,
    DELIVERY,
    SHIPPED,
    COMPLETED,
    CANCELLED
}

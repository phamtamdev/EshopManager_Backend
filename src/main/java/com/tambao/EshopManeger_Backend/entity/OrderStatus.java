package com.tambao.EshopManeger_Backend.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_CONFIRMATION("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    IN_TRANSIT("Đang vận chuyển"),
    DELIVERED("Đã giao"),
    CANCELLED("Đã hủy");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

}

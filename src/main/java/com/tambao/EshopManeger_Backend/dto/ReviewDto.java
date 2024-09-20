package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ReviewDto {
    private int id;
    private int rating;
    private String comment;
    private Timestamp createdAt;
    private Boolean hasPurchased;
    private Integer userId;
    private Integer productId;
}

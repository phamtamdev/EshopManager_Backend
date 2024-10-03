package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class NotificationDto {
    private Integer id;
    private String message;
    private String link;
    private String logo;
    private boolean seen;
    private Timestamp createAt;
    private Integer userId;
}

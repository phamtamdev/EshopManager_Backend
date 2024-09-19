package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WishlistsDto {
    private Integer id;
    private Integer userId;
    private Integer productId;
}

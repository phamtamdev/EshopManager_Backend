package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShippingAddressDto {
    private int id;
    private String name;
    private String province;
    private String district;
    private String ward;
    private String streetNumber;
    private Boolean defaultAddress;
    private String fullAddress;
    private Integer userId;
}

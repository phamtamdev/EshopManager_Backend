package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.ShippingAddressDto;
import com.tambao.EshopManeger_Backend.entity.ShippingAddress;
import com.tambao.EshopManeger_Backend.entity.Users;

public class ShippingAddressMapper {
    public static ShippingAddressDto mapToShippingAddressDto(ShippingAddress shippingAddress) {
        return new ShippingAddressDto(
                shippingAddress.getId(),
                shippingAddress.getName(),
                shippingAddress.getProvince(),
                shippingAddress.getDistrict(),
                shippingAddress.getWard(),
                shippingAddress.getStreetNumber(),
                shippingAddress.getDefaultAddress(),
                shippingAddress.getFullAddress(),
                shippingAddress.getUser().getId()
        );
    }

    public static ShippingAddress mapToShippingAddress(ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(shippingAddressDto.getId());
        shippingAddress.setName(shippingAddressDto.getName());
        shippingAddress.setProvince(shippingAddressDto.getProvince());
        shippingAddress.setDistrict(shippingAddressDto.getDistrict());
        shippingAddress.setWard(shippingAddressDto.getWard());
        shippingAddress.setStreetNumber(shippingAddressDto.getStreetNumber());
        shippingAddress.setDefaultAddress(shippingAddressDto.getDefaultAddress());
        shippingAddress.setFullAddress(shippingAddressDto.getFullAddress());
        if(shippingAddressDto.getUserId() != null){
            Users user = new Users();
            user.setId(shippingAddressDto.getUserId());
            shippingAddress.setUser(user);
        }
        return shippingAddress;
    }
}

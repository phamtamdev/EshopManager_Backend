package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ShippingAddressDto;

import java.util.List;

public interface IShippingAddress {
    ShippingAddressDto addShippingAddress(ShippingAddressDto shippingAddressDto);
    List<ShippingAddressDto> getAllShippingAddressByUserId(Integer userId);
    ShippingAddressDto getShippingAddressByUserIdAndDefaultAddress(Integer userId, Boolean defaultAddress);
    ShippingAddressDto updateDefaultAddress(Integer addressId, Boolean defaultAddress);
    ShippingAddressDto updateShippingAddress(Integer addressId, ShippingAddressDto shippingAddressDto);
    ShippingAddressDto getShippingAddressById(Integer addressId);
    void deleteShippingAddress(Integer addressId);
}

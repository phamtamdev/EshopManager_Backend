package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.CartItemDto;

import java.util.List;

public interface ICartItemService {
    CartItemDto getById(Integer id);
    CartItemDto addCartItem(CartItemDto cartItemDto);
    List<CartItemDto> getByCartId(Integer cartId);
    CartItemDto updateQuantity(Integer cartItemId, CartItemDto cartItemDto);
    void deleteCartItem(Integer cartItemId);
}

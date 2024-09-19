package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.CartDto;

public interface ICartService {
    CartDto getCartByUserId(Integer userId);
    CartDto getOrCreateCartForUser(Integer userId);
    void deleteCart(Integer cartId);
}

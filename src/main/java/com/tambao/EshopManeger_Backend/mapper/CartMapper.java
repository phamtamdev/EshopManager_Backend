package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.CartDto;
import com.tambao.EshopManeger_Backend.entity.Cart;
import com.tambao.EshopManeger_Backend.entity.Users;

public class CartMapper {
    public static CartDto mapToCartDto(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUser().getId()
        );
    }

    public static Cart mapToCart(CartDto cartDto) {
        Cart cart = new Cart();
        cart.setId(cartDto.getId());
        if(cartDto.getUserId() != null){
            Users user = new Users();
            user.setId(cartDto.getUserId());
            cart.setUser(user);
        }
        return cart;
    }
}

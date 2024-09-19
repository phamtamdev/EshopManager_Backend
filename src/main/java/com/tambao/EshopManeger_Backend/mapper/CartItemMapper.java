package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.CartItemDto;
import com.tambao.EshopManeger_Backend.entity.Cart;
import com.tambao.EshopManeger_Backend.entity.CartItem;
import com.tambao.EshopManeger_Backend.entity.Product;

public class CartItemMapper {
    public static CartItemDto mapToCartItemDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getDescription(),
                cartItem.getCategorySlug(),
                cartItem.getProduct().getId(),
                cartItem.getCart().getId()
        );
    }

    public static CartItem mapToCartItem(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemDto.getId());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setPrice(cartItemDto.getPrice());
        cartItem.setDescription(cartItemDto.getDescription());
        cartItem.setCategorySlug(cartItemDto.getCategorySlug());
        if (cartItemDto.getProductId() != null) {
            Product product = new Product();
            product.setId(cartItemDto.getProductId());
            cartItem.setProduct(product);
        }

        if (cartItemDto.getCartId() != null) {
            Cart cart = new Cart();
            cart.setId(cartItemDto.getCartId());
            cartItem.setCart(cart);
        }

        return cartItem;
    }
}

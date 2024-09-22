package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.CartItemDto;
import com.tambao.EshopManeger_Backend.entity.Cart;
import com.tambao.EshopManeger_Backend.entity.CartItem;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.CartItemMapper;
import com.tambao.EshopManeger_Backend.repository.CartItemRepository;
import com.tambao.EshopManeger_Backend.repository.CartRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItemDto getById(Integer id) {
        CartItem cartItem = cartItemRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("CartItem not found"));
        return CartItemMapper.mapToCartItemDto(cartItem);
    }

    @Override
    public CartItemDto addCartItem(CartItemDto cartItemDto) {
        Cart cart = cartRepository.findById(cartItemDto.getCartId()).orElseThrow(() -> new RuntimeException("CartItem not found"));
        Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        List<CartItem> cartItems = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());
        for (CartItem existingCartItem : cartItems) {
            if (existingCartItem.getPrice().equals(cartItemDto.getPrice())) {
                existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemDto.getQuantity());
                cartItemRepository.save(existingCartItem);
                return CartItemMapper.mapToCartItemDto(existingCartItem);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setQuantity(cartItemDto.getQuantity());
        newCartItem.setDescription(cartItemDto.getDescription());
        newCartItem.setPrice(cartItemDto.getPrice());
        newCartItem.setCategorySlug(cartItemDto.getCategorySlug());
        cartItemRepository.save(newCartItem);
        return CartItemMapper.mapToCartItemDto(newCartItem);
    }

    @Override
    public List<CartItemDto> getByCartId(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        return cartItems.stream().map(CartItemMapper::mapToCartItemDto).collect(Collectors.toList());
    }

    @Override
    public CartItemDto updateQuantity(Integer cartItemId, CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findByIdAndProductId(cartItemId, cartItemDto.getProductId());
        if (cartItem == null) {
            throw new ResourceNotFoundException("CartItem not found");
        }
        cartItem.setQuantity(cartItemDto.getQuantity());
        CartItem cartItemUpdated = cartItemRepository.save(cartItem);
        return CartItemMapper.mapToCartItemDto(cartItemUpdated);
    }

    @Override
    public void deleteCartItem(Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepository.delete(cartItem);
    }
}

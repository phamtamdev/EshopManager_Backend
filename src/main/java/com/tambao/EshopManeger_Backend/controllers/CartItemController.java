package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.CartItemDto;
import com.tambao.EshopManeger_Backend.service.Impl.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<?> getAllByCartId(@RequestParam Integer cartId) {
        return ResponseEntity.ok(cartItemService.getByCartId(cartId));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<?> getByCartItemId(@PathVariable Integer cartItemId) {
        return ResponseEntity.ok(cartItemService.getById(cartItemId));
    }

    @PostMapping
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto) {
        cartItemDto.setId(0);
        cartItemDto = cartItemService.addCartItem(cartItemDto);
        return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<?> updateQuantity(@PathVariable Integer cartItemId, @RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok(cartItemService.updateQuantity(cartItemId, cartItemDto));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Integer cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}

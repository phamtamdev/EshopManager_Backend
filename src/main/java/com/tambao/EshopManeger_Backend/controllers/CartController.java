package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.service.Impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<?> getCartByUserId(@RequestParam Integer userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> addCart(@RequestParam Integer userId) {
        return ResponseEntity.ok(cartService.getOrCreateCartForUser(userId));
    }
}

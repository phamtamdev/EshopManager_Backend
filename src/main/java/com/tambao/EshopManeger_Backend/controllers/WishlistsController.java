package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.WishlistsDto;
import com.tambao.EshopManeger_Backend.service.Impl.WishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistsController {

    @Autowired
    private WishlistsService wishlistsService;

    @GetMapping("/{userId}/wishlists")
    public ResponseEntity<?> getWishlists(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(wishlistsService.getAllWishlistsByUserId(userId));
    }

    @GetMapping("/{userId}/wishlists-products")
    public ResponseEntity<?> getWishlistsProducts(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(wishlistsService.getWishlistProducts(userId));
    }

    @PostMapping
    public ResponseEntity<?> addWishlist(@RequestBody WishlistsDto wishlistsDto) {
        wishlistsDto.setId(0);
        wishlistsDto = wishlistsService.addWishlist(wishlistsDto);
        return new ResponseEntity<>(wishlistsDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{wishlistsId}")
    public ResponseEntity<?> deleteWishlist(@PathVariable("wishlistsId") Integer wishlistsId) {
        wishlistsService.deleteWishlistById(wishlistsId);
        return ResponseEntity.ok("Wishlist deleted successfully");
    }
}

package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.dto.WishlistsDto;

import java.util.List;

public interface IWishlistsService {
    WishlistsDto addWishlist(WishlistsDto wishlistsDto);
    List<WishlistsDto> getAllWishlistsByUserId(Integer userId);
    List<ProductDto> getWishlistProducts(Integer userId);
    void deleteWishlistById(Integer id);
}

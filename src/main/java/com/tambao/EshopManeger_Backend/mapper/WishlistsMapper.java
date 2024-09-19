package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.WishlistsDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.entity.Wishlists;

public class WishlistsMapper {
    public static WishlistsDto mapToWishlistsDto(Wishlists wishlists) {
        return new WishlistsDto(
                wishlists.getId(),
                wishlists.getUser().getId(),
                wishlists.getProduct().getId()
        );
    }

    public static Wishlists mapToWishlists(WishlistsDto dto) {
        Wishlists wishlists = new Wishlists();
        wishlists.setId(dto.getId());
        if(dto.getUserId() != null){
            Users user = new Users();
            user.setId(dto.getUserId());
            wishlists.setUser(user);
        }
        if(dto.getProductId() != null){
            Product product = new Product();
            product.setId(dto.getProductId());
            wishlists.setProduct(product);
        }
        return wishlists;
    }
}

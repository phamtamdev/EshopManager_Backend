package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.dto.WishlistsDto;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.entity.Wishlists;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ProductMapper;
import com.tambao.EshopManeger_Backend.mapper.WishlistsMapper;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.repository.WishlistsRepository;
import com.tambao.EshopManeger_Backend.service.IWishlistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistsService implements IWishlistsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistsRepository wishlistsRepository;

    @Autowired
    private ProductService productService;

    @Override
    public WishlistsDto addWishlist(WishlistsDto wishlistsDto) {
        Wishlists wishlists = WishlistsMapper.mapToWishlists(wishlistsDto);
        return WishlistsMapper.mapToWishlistsDto(wishlistsRepository.save(wishlists));
    }

    @Override
    public List<WishlistsDto> getAllWishlistsByUserId(Integer userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<Wishlists> wishlists = wishlistsRepository.findByUserId(user.getId());
        return wishlists.stream().map(WishlistsMapper::mapToWishlistsDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getWishlistProducts(Integer userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        List<Wishlists> wishlists = wishlistsRepository.findByUserId(user.getId());
        List<Integer> productIds = wishlists.stream()
                .map(Wishlists::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(productIds);
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteWishlistById(Integer id) {
        Wishlists wishlists = wishlistsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        wishlistsRepository.delete(wishlists);
    }
}

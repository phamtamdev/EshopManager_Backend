package com.tambao.EshopManeger_Backend.repository;

import com.tambao.EshopManeger_Backend.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Integer> {
    List<ShippingAddress> findByUserId(Integer userId);
    ShippingAddress findByUserIdAndDefaultAddress(Integer userId, Boolean defaultAddress);
}

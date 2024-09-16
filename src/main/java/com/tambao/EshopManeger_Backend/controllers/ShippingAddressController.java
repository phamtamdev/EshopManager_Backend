package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.ShippingAddressDto;
import com.tambao.EshopManeger_Backend.service.Impl.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping-address")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping
    public ResponseEntity<?> getShippingAddress(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(shippingAddressService.getAllShippingAddressByUserId(userId));
    }

    @GetMapping("/{addressId}/addressId")
    public ResponseEntity<?> getShippingAddressById(@PathVariable("addressId") Integer addressId) {
        return ResponseEntity.ok(shippingAddressService.getShippingAddressById(addressId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getShippingAddressByUserIdAndDefaultAddress(@PathVariable("userId") Integer userId, @RequestParam("defaultAddress") Boolean defaultAddress) {
        ShippingAddressDto shippingAddressDto = shippingAddressService.getShippingAddressByUserIdAndDefaultAddress(userId, defaultAddress);
        return ResponseEntity.ok(shippingAddressDto);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShippingAddressDto shippingAddressDto) {
        shippingAddressDto.setId(0);
        shippingAddressDto = shippingAddressService.addShippingAddress(shippingAddressDto);
        return new ResponseEntity<>(shippingAddressDto, HttpStatus.CREATED);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<?> update(@PathVariable("addressId") Integer addressId, @RequestBody ShippingAddressDto shippingAddressDto) {
        return ResponseEntity.ok(shippingAddressService.updateShippingAddress(addressId, shippingAddressDto));
    }

    @PutMapping("/{addressId}/defaultAddress")
    public ResponseEntity<?> updateDefaultAddress(@PathVariable Integer addressId, @RequestParam("defaultAddress") Boolean defaultAddress) {
        return ResponseEntity.ok(shippingAddressService.updateDefaultAddress(addressId, defaultAddress));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        shippingAddressService.deleteShippingAddress(id);
        return ResponseEntity.ok("Deleted Shipping Address Successfully");
    }
}

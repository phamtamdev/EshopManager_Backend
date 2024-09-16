package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.ShippingAddressDto;
import com.tambao.EshopManeger_Backend.entity.ShippingAddress;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ShippingAddressMapper;
import com.tambao.EshopManeger_Backend.repository.ShippingAddressRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.service.IShippingAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingAddressService implements IShippingAddress {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Override
    public ShippingAddressDto addShippingAddress(ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = shippingAddressRepository.save(ShippingAddressMapper.mapToShippingAddress(shippingAddressDto));
        return ShippingAddressMapper.mapToShippingAddressDto(shippingAddress);
    }

    @Override
    public List<ShippingAddressDto> getAllShippingAddressByUserId(Integer userId) {
        Users users = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User not found"));
        List<ShippingAddress> shippingAddressList = shippingAddressRepository.findByUserId(users.getId());
        return shippingAddressList.stream().map(ShippingAddressMapper::mapToShippingAddressDto).collect(Collectors.toList());
    }

    @Override
    public ShippingAddressDto getShippingAddressByUserIdAndDefaultAddress(Integer userId, Boolean defaultAddress) {
        if(!defaultAddress) {
            throw new ResourceNotFoundException("Cannot find address with defaultAddress set to false.");
        }
        Users users = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("User not found"));
        ShippingAddress shippingAddress = shippingAddressRepository.findByUserIdAndDefaultAddress(users.getId(), true);
        if (shippingAddress == null) {
            return null;
        }
        return ShippingAddressMapper.mapToShippingAddressDto(shippingAddress);
    }

    @Override
    public ShippingAddressDto updateDefaultAddress(Integer addressId, Boolean defaultAddress) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(addressId).orElseThrow(() ->new ResourceNotFoundException("Address not found"));
        shippingAddress.setDefaultAddress(defaultAddress);
        ShippingAddress shippingAddressUpdate = shippingAddressRepository.save(shippingAddress);
        return ShippingAddressMapper.mapToShippingAddressDto(shippingAddressUpdate);
    }

    @Override
    public ShippingAddressDto updateShippingAddress(Integer addressId, ShippingAddressDto shippingAddressDto) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(addressId).orElseThrow(() ->new ResourceNotFoundException("Address not found"));
        Users users = userRepository.findById(shippingAddressDto.getUserId()).orElseThrow(() ->new ResourceNotFoundException("User not found"));
        shippingAddress.setName(shippingAddressDto.getName());
        shippingAddress.setProvince(shippingAddressDto.getProvince());
        shippingAddress.setDistrict(shippingAddressDto.getDistrict());
        shippingAddress.setWard(shippingAddressDto.getWard());
        shippingAddress.setDefaultAddress(shippingAddressDto.getDefaultAddress());
        shippingAddress.setStreetNumber(shippingAddressDto.getStreetNumber());
        shippingAddress.setFullAddress(shippingAddressDto.getFullAddress());
        shippingAddress.setUser(users);
        ShippingAddress shippingAddressUpdate = shippingAddressRepository.save(shippingAddress);
        return ShippingAddressMapper.mapToShippingAddressDto(shippingAddressUpdate);
    }

    @Override
    public ShippingAddressDto getShippingAddressById(Integer addressId) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(addressId).orElseThrow(() ->new ResourceNotFoundException("Address not found"));
        return ShippingAddressMapper.mapToShippingAddressDto(shippingAddress);
    }

    @Override
    public void deleteShippingAddress(Integer addressId) {
        ShippingAddress shippingAddress = shippingAddressRepository.findById(addressId).orElseThrow(() ->new ResourceNotFoundException("Address not found"));
        shippingAddressRepository.delete(shippingAddress);
    }
}

package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Users;

public class UserMapper {

    public static UserDto mapToUsersDto(Users user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
        );
    }

    public static Users mapToUsers(UserDto dto) {
        Users user = new Users();
        user.setId(dto.getId());
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        return user;
    }
}

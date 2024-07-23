package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Users;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto mapToUsersDto(Users user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getBirthdate(),
                user.isEnabled(),
                user.getSource(),
                user.getAvatar(),
                user.getRoles().stream().map(RoleMapper::mapToRoleDto).collect(Collectors.toList())
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
        user.setBirthdate(dto.getBirthdate());
        user.setEnabled(dto.getEnabled());
        user.setSource(dto.getSource());
        user.setAvatar(dto.getAvatar());
        return user;
    }
}

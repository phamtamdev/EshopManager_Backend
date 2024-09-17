package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUsersService extends UserDetailsService {
    Users findByEmail(String email);

    Users findByUserName(String username);

    UserDto save(UserDto userDto);

    UserDto updateRole(Integer id, RoleDto roleDto);

    UserDto toggleUserStatus(Integer id, Boolean status);

    List<UserDto> getAll();

    Page<UserDto> findAllWithPageable(int page, int size);

    Page<UserDto> findAllWithPageableAndSearch(int page, int size, String keyword);
}

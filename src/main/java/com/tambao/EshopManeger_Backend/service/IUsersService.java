package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsersService extends UserDetailsService {
    Users findByEmail(String email);
    Users findByUserName(String username);
    Users save(Users user);
}

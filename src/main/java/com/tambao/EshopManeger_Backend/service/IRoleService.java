package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.entity.Role;

import java.util.List;

public interface IRoleService {
    List<RoleDto> findAll();
    Role getByName(String name);
    Role addRole(Role role);
}

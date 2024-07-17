package com.tambao.EshopManeger_Backend.service;

import com.tambao.EshopManeger_Backend.entity.Role;

public interface IRoleService {
    Role getByName(String name);
    Role addRole(Role role);
}

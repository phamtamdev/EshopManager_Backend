package com.tambao.EshopManeger_Backend.mapper;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.entity.Role;

public class RoleMapper {
    public static RoleDto mapToRoleDto(Role role) {
        return new RoleDto(
                role.getId(),
                role.getName()
        );
    }

    public static Role mapToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }
}

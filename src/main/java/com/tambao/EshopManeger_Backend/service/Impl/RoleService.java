package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.entity.Role;
import com.tambao.EshopManeger_Backend.mapper.RoleMapper;
import com.tambao.EshopManeger_Backend.repository.RoleRepository;
import com.tambao.EshopManeger_Backend.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleMapper::mapToRoleDto)
                .collect(Collectors.toList());
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }
}

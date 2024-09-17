package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Role;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.UserMapper;
import com.tambao.EshopManeger_Backend.repository.RoleRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService implements IUsersService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(@Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (userRepository.existsByUserName(userDto.getUserName())) {
            throw new ResourceNotFoundException("Username already exists");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }
        Users user = new Users();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setBirthdate(userDto.getBirthdate());
        user.setPhone(userDto.getPhone());
        user.setSex(0);
        user.setCreationDate(LocalDate.now());
        user.setEnabled(true);
        user.setSource("LOCAL");
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        List<Role> roles = new ArrayList<>();
        if (!userDto.getRoles().isEmpty()) {
            RoleDto roleDto = userDto.getRoles().get(0);
            Role role = roleRepository.findById(roleDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            roles.add(role);
            user.setRoles(roles);
        } else {
            throw new IllegalArgumentException("Role is required");
        }
        userRepository.save(user);
        return UserMapper.mapToUsersDto(user);
    }

    @Override
    public UserDto updateRole(Integer id, RoleDto roleDto) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Role role = roleRepository.findById(roleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        List<Role> updatedRoles = new ArrayList<>();
        updatedRoles.add(role);
        user.setRoles(updatedRoles);
        Users updateUser = userRepository.save(user);
        return UserMapper.mapToUsersDto(updateUser);
    }

    @Override
    public UserDto toggleUserStatus(Integer id, Boolean status) {
        Users user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> "ADMIN".equals(role.getName()));
        if (!isAdmin) {
            user.setEnabled(status);
            userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Cannot change status of an ADMIN user");
        }
        return UserMapper.mapToUsersDto(user);
    }


    @Override
    public List<UserDto> getAll() {
        List<Users> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUsersDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> findAllWithPageable(int page, int size) {
        Page<Users> users = userRepository.findAll(PageRequest.of(page, size));
        return users.map(UserMapper::mapToUsersDto);
    }

    @Override
    public Page<UserDto> findAllWithPageableAndSearch(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> users = userRepository.findByFullNameContainingIgnoreCase(keyword, pageable);
        return users.map(UserMapper::mapToUsersDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByUserName(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        User userDetails = new User(user.getUserName(), user.getPassword(), user.isEnabled(), true, true, true, rolesAuthorities(user.getRoles()));
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> rolesAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.JwtResponse;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.entity.Role;
import com.tambao.EshopManeger_Backend.entity.Users;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.UserMapper;
import com.tambao.EshopManeger_Backend.repository.RoleRepository;
import com.tambao.EshopManeger_Backend.repository.UserRepository;
import com.tambao.EshopManeger_Backend.security.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public UserDto registerUser(UserDto userDto) {
        if(userRepository.existsByUserName(userDto.getUserName())) {
            throw new ResourceNotFoundException("Username already exists");
        }
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }

        Users user = UserMapper.mapToUsers(userDto);
        user.setEnabled(false);
        Role role = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<>();
        if(role == null) {
            Role newRole = new Role();
            newRole.setName("USER");
            roleRepository.save(newRole);
            user.setRoles(List.of(newRole));
        }else{
            roles.add(role);
            user.setRoles(roles);
        }
        userRepository.save(user);
        return UserMapper.mapToUsersDto(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        JwtResponse response = new JwtResponse();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            Users user = userRepository.findByUserName(loginRequest.getUsername());
            String jwt = jwtService.generateToken(loginRequest.getUsername());
            response.setToken(jwt);
            response.setStatusCode(200);
            List<String> roles = new ArrayList<>();
            user.getRoles().forEach(role -> roles.add(role.getName()));
            response.setRoles(roles);
            response.setExpirationTime("24 Hrs");
            response.setMessage("Successfully logged in");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public JwtResponse getMyInfo(String userName){
        JwtResponse response = new JwtResponse();
        try{
            Users user = userRepository.findByUserName(userName);
            if(user != null){
                response.setStatusCode(200);
                response.setMessage("Successfully");
                response.setUserDto(UserMapper.mapToUsersDto(user));
            }else {
                response.setStatusCode(404);
                response.setMessage("User not found");
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}

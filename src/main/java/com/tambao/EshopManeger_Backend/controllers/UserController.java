package com.tambao.EshopManeger_Backend.controllers;

import com.tambao.EshopManeger_Backend.dto.RoleDto;
import com.tambao.EshopManeger_Backend.dto.UserDto;
import com.tambao.EshopManeger_Backend.service.Impl.RoleService;
import com.tambao.EshopManeger_Backend.service.Impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/users"})
public class UserController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "size",required = false) Integer size,
            @RequestParam(value = "keyword",required = false) String keyword
    ) {
        if(page == null || size == null) {
            List<UserDto> customers = usersService.getAll();
            return ResponseEntity.ok(customers);
        }else if(keyword != null) {
            Page<UserDto> customers = usersService.findAllWithPageableAndSearch(page, size, keyword);
            return ResponseEntity.ok(customers);
        }else {
            Page<UserDto> customers = usersService.findAllWithPageable(page, size);
            return ResponseEntity.ok(customers);
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{userId}/toggle")
    public ResponseEntity<?> toggleUser(@PathVariable Integer userId, @RequestParam("enabled") Boolean enabled) {
        return ResponseEntity.ok(usersService.toggleUserStatus(userId, enabled));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserDto userDto) {
        userDto.setId(0);
        userDto = usersService.save(userDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(usersService.updateRole(userId, roleDto));
    }
}
